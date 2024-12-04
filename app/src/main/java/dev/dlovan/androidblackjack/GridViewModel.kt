package dev.dlovan.androidblackjack

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GridViewModel : ViewModel() {
    var dealerCards = MutableLiveData<MutableList<String>>(mutableListOf())
    var playerCards = MutableLiveData<MutableList<String>>(mutableListOf())
    private var cards = mutableListOf(
        "A", "A", "A", "A",
        "2", "2", "2", "2",
        "3", "3", "3", "3",
        "4", "4", "4", "4",
        "5", "5", "5", "5",
        "6", "6", "6", "6",
        "7", "7", "7", "7",
        "8", "8", "8", "8",
        "9", "9", "9", "9",
        "10", "10", "10", "10",
        "J", "J", "J", "J",
        "Q", "Q", "Q", "Q",
        "K", "K", "K", "K"
    )


    fun addItem(playerType: PlayerType): String {
        val index = (1 until cards.size).random()
        val cardValue = cards.removeAt(index)
        val currentList: MutableList<String>

        if (playerType == PlayerType.PLAYER) {
            currentList = playerCards.value ?: mutableListOf()
            currentList.add(cardValue)
            playerCards.value = currentList
        } else {
            currentList = dealerCards.value ?: mutableListOf()
            currentList.add(cardValue)
            dealerCards.value = currentList
        }

        return cardValue
    }

    fun resetGame() {
        cards = mutableListOf(
            "A", "A", "A", "A",
            "2", "2", "2", "2",
            "3", "3", "3", "3",
            "4", "4", "4", "4",
            "5", "5", "5", "5",
            "6", "6", "6", "6",
            "7", "7", "7", "7",
            "8", "8", "8", "8",
            "9", "9", "9", "9",
            "10", "10", "10", "10",
            "J", "J", "J", "J",
            "Q", "Q", "Q", "Q",
            "K", "K", "K", "K"
        )
        playerCards.value = mutableListOf()
        dealerCards.value = mutableListOf()
    }
}
