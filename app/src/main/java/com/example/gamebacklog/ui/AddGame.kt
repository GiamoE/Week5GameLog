package com.example.gamebacklog.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.gamebacklog.R
import com.example.gamebacklog.model.Game

import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*

const val EXTRA_GAME = "EXTRA_GAME"

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnSave.setOnClickListener { view ->
            onSaveClick()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> { // Used to identify when the user has clicked the back button
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onSaveClick(){
        //add game with view values as extra to resultIntents putExtra, set the result and finish
        val game = Game(
            etGameTitleText.text.toString(),
            etGamePlatformText.text.toString().toInt(),
            etGameDayText.text.toString(),
            etGameMonthText.text.toString(),
            etGameYearText.text.toString()
        )
        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_GAME, game)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

}
