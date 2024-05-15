package com.example.savethedove

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), GameTask {
    private lateinit var rootLayout: LinearLayout
    private lateinit var startBtn: Button
    private lateinit var playAgainBtn: Button
    private lateinit var mGameView: GameView
    private lateinit var scoreText: TextView
    private lateinit var highScoreText: TextView
    private var score = 0
    private var highScore = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        scoreText = findViewById(R.id.score)
        highScoreText = findViewById(R.id.highScore)
        playAgainBtn = findViewById(R.id.playAgainBtn)

        startBtn.setOnClickListener {
            startGame()
        }

        playAgainBtn.setOnClickListener {
            playAgain()
        }

    }

    private fun startGame() {
        mGameView = GameView(this, this)
        mGameView.setBackgroundResource(R.drawable.bg)
        rootLayout.addView(mGameView)
        startBtn.visibility = View.GONE
        scoreText.visibility = View.GONE
        highScoreText.visibility = View.GONE
        playAgainBtn.visibility = View.GONE
        score = 0
    }

    override fun closeGame(mScore: Int) {
        score = mScore
        scoreText.text = "Score : $score"
        startBtn.visibility = View.VISIBLE
        scoreText.visibility = View.VISIBLE
        highScoreText.visibility = View.VISIBLE
        playAgainBtn.visibility = View.VISIBLE

        if (score > highScore) {
            highScore = score
            highScoreText.text = "High Score : $highScore"
        }
        rootLayout.removeView(mGameView)
    }

    private fun playAgain() {
        rootLayout.removeView(mGameView)
        startGame()
    }
}