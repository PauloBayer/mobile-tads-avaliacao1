package com.paulobayer.mathgame

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var num1TextView: TextView
    private lateinit var opTextView: TextView
    private lateinit var num2TextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        num1TextView = findViewById(R.id.num1)
        opTextView = findViewById(R.id.operator)
        num2TextView = findViewById(R.id.num2)

        updateRandomValues()
    }

    fun updateRandomValues() {
        val num1 = Random.nextInt(100) + 1
        num1TextView.text = num1.toString()

        var operators = listOf("+", "-")
        var op = operators[Random.nextInt(operators.size)]
        opTextView.text = op

        do {
            var num2 = Random.nextInt(100) + 1
            num2TextView.text = num2.toString()
        } while (op == "-" && num2 > num1)
    }
}