package dev.dlovan.androidblackjack

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    lateinit var cardGrid: GridLayout
    lateinit var hitMeBtn: MaterialButton
    lateinit var standBtn: MaterialButton
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cardGrid = findViewById(R.id.card_grid)
        hitMeBtn = findViewById(R.id.btn_hit_me)
        standBtn = findViewById(R.id.btn_stand)

        hitMeBtn.setOnClickListener {
            addItemToGrid("$i")
            i++
        }
    }

    /**
     * Add card to grid.
     */
    fun addItemToGrid(content: String) {

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
        cardGrid.addView(textView)
    }
}