package com.example.gamebacklog.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gamebacklog.R
import com.example.gamebacklog.model.Game

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

const val DAYS = 365.0f
const val MONTHS = 12.0f
const val REQUEST_CODE = 99

class MainActivity : AppCompatActivity() {

    private val backlog = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(backlog)

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        btnAdd.setOnClickListener { view ->
            startAddActivity()
        }

        initViews()
        initViewModel()
    }

    private fun initViews(){
        // Initialize the recycler view with a linear layout manager, adapter
        rvBacklog.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvBacklog.adapter = gameAdapter
        rvBacklog.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        createItemTouchHelper().attachToRecyclerView(rvBacklog)
    }

    private fun initViewModel(){
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        // Observe games from the view model, update the list when the data is changed
        viewModel.backlog.observe(this, Observer{backlog ->
            this@MainActivity.backlog.clear()
            this@MainActivity.backlog.addAll(this@MainActivity.sortBacklog(backlog))
            gameAdapter.notifyDataSetChanged()
        })
    }

    private fun sortBacklog(backlog: List<Game>): List<Game>{
        return backlog.sortedByDescending { game: Game ->
            -(game.releaseYear.toFloat()
            + toYears(game.releaseMonth.toFloat(), true)
            + toYears(game.releaseDay.toFloat(), false))
        }
    }

    private fun toYears(measurable: Float, isMonth: Boolean): Float{
        return if(isMonth)measurable / MONTHS else measurable / DAYS
    }

    private fun startAddActivity(){
        val intent = Intent(this, AddActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_CODE -> {
                    val game = data!!.getParcelableExtra<Game>(EXTRA_GAME)

                    if(hasValidDate(game))
                        viewModel.insertGame(game)
                }
            }
        }
    }

    private fun hasValidDate(game: Game) : Boolean{
        return game.releaseDay.isDigitsOnly()
                && game.releaseMonth.isDigitsOnly()
                && game.releaseYear.isDigitsOnly()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_delete_backlog -> {
                viewModel.clearBacklog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Create a touch helper to recognize when a user swipes an item from a recycler view.
     * An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
     * and uses callbacks to signal when a user is performing these actions.
     */
    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedGame = backlog.removeAt(position)
                viewModel.deleteGame(deletedGame)
            }
        }
        return ItemTouchHelper(callback)
    }
}
