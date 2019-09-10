package com.kiwipower.exploding

import java.io.InputStream
import java.util.*
import kotlin.math.roundToInt

class ExplodingGame(
        val player: Player
) {
    val cards: MutableList<Card>

    init {
        val blankCards: MutableList<Card> = (0..46).map { BlankCard() }.toMutableList()
        val explodingCard = ExplodingCard()
        val randomNumber = (Math.random() * 46).roundToInt()

        blankCards[randomNumber] = explodingCard
        cards = blankCards
    }

    fun start(inputStream: InputStream = System.`in`) {
        println("Player: ${player.name} is playing exploding")
        println("to draw a card, enter \"draw\"")

        val scanner = Scanner(inputStream)

        while (scanner.hasNext()) {
            println("Player: ${player.name} please draw a card")
            when (scanner.next()) {
                "draw" -> {
                    val cardDrawn = cards.removeAt(0)
                    if (cardDrawn is ExplodingCard) {
                        println("You drew the exploding card! Game Over! Play again? (Y/N)")
                    }
                }
            }
        }

    }

}
