package com.paulobayer.mathgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity que mostra o resultado final do jogo
 * Exibe a pontuação e permite reiniciar o jogo
 */
class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Recupera a pontuação da MainActivity
        val score = intent.getIntExtra("SCORE", 0)
        
        // Configura o texto da pontuação
        val scoreText = findViewById<TextView>(R.id.scoreText)
        scoreText.text = "Sua nota: $score de 100 pontos"
        
        // Define a mensagem baseada na pontuação
        val messageText = findViewById<TextView>(R.id.messageText)
        when (score) {
            100 -> messageText.text = "Parabéns! Você acertou todas as questões!"
            in 60..99 -> messageText.text = "Boa amigão! Continue praticando!"
            in 20..59 -> messageText.text = "Bom trabalho! Você está no caminho certo!"
            else -> messageText.text = "Continue praticando para melhorar! Não desista!"
        }
        
        // Configura botão para reiniciar o jogo
        val restartButton = findViewById<Button>(R.id.restartButton)
        restartButton.setOnClickListener {
            // Volta para a MainActivity com um novo jogo
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Evita acumular activities na pilha
        }
    }
}