package com.kiwipower.exploding

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

    fun start() {
    }

}
