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
    private lateinit var num1TextView: TextView
    private lateinit var num2TextView: TextView
    private lateinit var operatorTextView: TextView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa as views
        mainLayout = findViewById(R.id.main)
        questionNumberText = findViewById(R.id.questionNumberText)
        num1TextView = findViewById(R.id.num1)
        num2TextView = findViewById(R.id.num2)
        operatorTextView = findViewById(R.id.operator)
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
        generateQuestion()
    }

    /**
     * Gera uma nova expressão matemática aleatória
     */
    private fun generateQuestion() {
        // Reseta a interface
        mainLayout.setBackgroundColor(normalColor)
        answerInput.setText("")
        answerInput.isEnabled = true
        checkButton.isEnabled = true
        nextButton.visibility = View.INVISIBLE
        resultText.visibility = View.INVISIBLE
        correctAnswerText.visibility = View.INVISIBLE

        // Atualiza número da questão
        questionNumberText.text = getString(R.string.question_format, currentQuestionNumber)

        // Escolhe operação aleatória
        val isAddition = Random.nextBoolean()
        operatorTextView.text = if (isAddition) "+" else "-"

        if (isAddition) {
            // Adição: números aleatórios de 0 a 99
            val num1 = Random.nextInt(100)
            val num2 = Random.nextInt(100)
            num1TextView.text = num1.toString()
            num2TextView.text = num2.toString()
            currentAnswer = num1 + num2
        } else {
            // TODO: Implementar lógica para evitar resultados negativos na subtração
            // Dica: Garanta que o primeiro número seja sempre maior que o segundo
            val num1 = Random.nextInt(100)
            val num2 = Random.nextInt(100)
            num1TextView.text = num1.toString()
            num2TextView.text = num2.toString()
            currentAnswer = num1 - num2
        }
    }

    /**
     * Verifica a resposta fornecida pelo usuário
     */
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

    /**
     * Avança para próxima questão ou finaliza o jogo
     */
    private fun moveToNextQuestion() {
        if (currentQuestionNumber < totalQuestions) {
            // Próxima questão
            currentQuestionNumber++
            generateQuestion()
        } else {
            // Finaliza e mostra resultados
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("SCORE", score)
            startActivity(intent)
            finish()
        }
    }
}