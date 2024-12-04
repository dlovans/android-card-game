package dev.dlovan.androidblackjack

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.dlovan.androidblackjack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val gridViewModel: GridViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        gridViewModel.playerCards.observe(this) { items ->
            updateGrid(items)
        }


        binding.btnHitMe.setOnClickListener {
            gridViewModel.addItem(PlayerType.PLAYER)
            this.evaluateRound()
        }

        binding.btnStand.setOnClickListener {
            this.evaluateRound(true)
        }


        binding.btnRestart.setOnClickListener {
            gridViewModel.resetGame()
            binding.cvResult.visibility = View.GONE
            binding.btnHitMe.isEnabled = true
            binding.btnStand.isEnabled = true
        }
    }

    /**
     * Updates the grid with new card.
     */
    private fun updateGrid(items: MutableList<String>) {
        binding.cardGrid.removeAllViews()

        items.forEach { content ->
            val textView = TextView(this).apply {
                text = content
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 200
                    height = 300
                    rowSpec = GridLayout.spec(GridLayout.UNDEFINED)
                    marginEnd = 8
                    bottomMargin = 8
                    textSize = 30f
                    gravity = Gravity.CENTER
                }
                setBackgroundColor(Color.WHITE)
            }
            binding.cardGrid.addView(textView)
        }
    }

    /**
     * Evaluates the round of the game.
     */
    private fun evaluateRound(playerStands: Boolean = false) {
        var roundOver = false
        var playerValue = 0
        var handHasAce = false
        var resultText = ""
        for (card in gridViewModel.playerCards.value ?: emptyList()) {
            when (card) {
                "A" -> {
                    playerValue += 1
                    handHasAce = true
                }
                "J", "Q", "K" -> playerValue += 10
                else -> playerValue += card.toInt()
            }
        }

        if (playerValue <= 11 && handHasAce) {
            playerValue += 10
        }

        if (playerValue > 21) {
            resultText = "Dealer wins, you lose. You: $playerValue"
            roundOver = true
        } else if (playerValue == 21 && gridViewModel.playerCards.value?.size == 2) {
            resultText = "Blackjack! You win! You: $playerValue"
            roundOver = true
        }

        if (playerStands) {
            roundOver = true
            var dealerValue = 0
            var dealerHasAce = false
            while (dealerValue < 17) {
                when (val cardValue = gridViewModel.addItem(PlayerType.DEALER)) {
                    "A" -> {
                        dealerValue += 11
                        dealerHasAce = true
                    }
                    "J", "Q", "K" -> dealerValue += 10
                    else -> dealerValue += cardValue.toInt()
                }

                if (dealerValue > 21 && dealerHasAce) {
                    dealerValue -= 10
                }
            }

            resultText = if (dealerValue == 21 && gridViewModel.dealerCards.value?.size == 2) {
                "Blackjack! Dealer wins!"
            } else if (dealerValue > 21) {
                "You win!"
            } else if (dealerValue == playerValue) {
                "It's a tie!"
            } else if (dealerValue > playerValue) {
                "Dealer wins!"
            } else {
                "You win!"
            }

            resultText += " You: $playerValue, Dealer: $dealerValue"
        }

        if (roundOver) {
            binding.tvWinner.text = resultText
            binding.cvResult.visibility = View.VISIBLE
            binding.btnHitMe.isEnabled = false
            binding.btnStand.isEnabled = false
        }

    }
}