package com.example.savethedove

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class GameView(private val context: Context, private val gameTask: GameTask) : View(context) {
    private var myPaint: Paint? = null
    private var speed = 1
    private var time = 0
    private var score = 0
    private var highScore = 0 // Updated to track high score
    private var myDovePosition = 0
    private val otherArrows = ArrayList<HashMap<String, Any>>()

    private var viewWidth = 0
    private var viewHeight = 0

    init {
        myPaint = Paint()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = measuredWidth
        viewHeight = measuredHeight

        //generating falling abstacles
        if (time % 700 < 10 + speed) {
            val map = HashMap<String, Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            otherArrows.add(map)
        }
        time = time + 10 + speed  //inccrement time
        val arrowWidth = viewWidth / 5
        val arrowHeight = arrowWidth + 10
        myPaint!!.style = Paint.Style.FILL
        // Draw user's dove
        val d = context.resources.getDrawable(R.drawable.img1, null)
        d.setBounds(
            myDovePosition * viewWidth / 3 + viewWidth / 15 + 25,
            viewHeight - 2 - arrowHeight,
            myDovePosition * viewWidth / 3 + viewWidth / 15 + arrowWidth - 25,
            viewHeight - 2
        )
        d.draw(canvas!!)
        myPaint!!.color = Color.GREEN

        for (i in otherArrows.indices) {
            try {
                val dove = otherArrows[i]["lane"] as Int * viewWidth / 3 + viewWidth / 15
                var arrow = time - otherArrows[i]["startTime"] as Int

                val d2 = context.resources.getDrawable(R.drawable.img2, null)
                d2.setBounds(
                    dove + 25, arrow - arrowHeight, dove + arrowWidth - 25, arrow
                )
                d2.draw(canvas)
                if (otherArrows[i]["lane"] as Int == myDovePosition) {
                    // Check collision
                    if (arrow > viewHeight - 2 - arrowHeight && arrow < viewHeight - 2) {
                        gameTask.closeGame(score)
                    }
                }
                if (arrow > viewHeight + arrowHeight) {
                    otherArrows.removeAt(i)
                    score++
                    speed = 1 + score / 8  //increase sped gradually
                    if (score > highScore) {
                        highScore = score
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        myPaint!!.color = Color.WHITE
        myPaint!!.textSize = 40f
        // Draw score and speed
        canvas.drawText("Score: $score", 80f, 80f, myPaint!!)
        canvas.drawText("Speed: $speed", 380f, 80f, myPaint!!)
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val x1 = event.x
                if (x1 < viewWidth / 2) {
                    if (myDovePosition > 0) {
                        myDovePosition--
                    }
                }
                if (x1 > viewWidth / 2) {
                    if (myDovePosition < 2) {
                        myDovePosition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }
}