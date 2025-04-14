package com.paulobayer.mathgame

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.random.Random

/**
 * Activity principal do jogo de matemática
 * Apresenta expressões matemáticas para crianças responderem
 */
class MainActivity : AppCompatActivity() {
    // Elementos da interface
    private lateinit var mainLayout: ConstraintLayout
    private lateinit var questionNumberText: TextView
    private lateinit var answerInput: EditText
    private lateinit var checkButton: Button
    private lateinit var nextButton: Button
    private lateinit var resultText: TextView
    private lateinit var correctAnswerText: TextView

    // Variáveis do jogo
    private var currentQuestionNumber = 1
    private var score = 0
    private var currentAnswer = 0
    private var totalQuestions = 5

    // Cores para feedback visual
    private val correctColor = Color.parseColor("#BBFFBB")
    private val wrongColor = Color.parseColor("#FFBBBB")
    private val normalColor = Color.parseColor("#E6F4FF")

    private lateinit var num1TextView: TextView
    private lateinit var opTextView: TextView
    private lateinit var num2TextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa as views
        mainLayout = findViewById(R.id.main)
        questionNumberText = findViewById(R.id.questionNumberText)
        num1TextView = findViewById(R.id.num1)
        num2TextView = findViewById(R.id.num2)
        opTextView = findViewById(R.id.operator)
        answerInput = findViewById(R.id.answerInput)
        checkButton = findViewById(R.id.checkButton)
        nextButton = findViewById(R.id.nextButton)
        resultText = findViewById(R.id.resultText)
        correctAnswerText = findViewById(R.id.correctAnswerText)

        // Configura os botões
        checkButton.setOnClickListener {
            checkAnswer()
        }

        nextButton.setOnClickListener {
            moveToNextQuestion()
        }

        // Inicia o jogo
        updateRandomValues()
    }

    private fun checkAnswer() {
        val userAnswer = answerInput.text.toString().toIntOrNull() ?: 0
        val isCorrect = userAnswer == currentAnswer
        
        if (isCorrect) {
            // Resposta correta
            score += 20
            mainLayout.setBackgroundColor(correctColor)
            resultText.text = getString(R.string.correct_answer)
            resultText.visibility = View.VISIBLE
        } else {
            // Resposta incorreta
            mainLayout.setBackgroundColor(wrongColor)
            resultText.text = getString(R.string.wrong_answer)
            resultText.visibility = View.VISIBLE
            correctAnswerText.text = currentAnswer.toString()
            correctAnswerText.visibility = View.VISIBLE
        }
        
        // Desabilita entrada e mostra botão de próxima
        answerInput.isEnabled = false
        checkButton.isEnabled = false
        
        if (currentQuestionNumber == totalQuestions) {
            nextButton.text = getString(R.string.finish_button)
        } else {
            nextButton.text = getString(R.string.next_button)
        }
        
        nextButton.visibility = View.VISIBLE
    }

    private fun moveToNextQuestion() {
        if (currentQuestionNumber < totalQuestions) {
            // Próxima questão
            currentQuestionNumber++
            updateRandomValues()
        } else {
            // Finaliza e mostra resultados
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("SCORE", score)
            startActivity(intent)
            finish()
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