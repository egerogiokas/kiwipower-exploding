package com.kiwipower.exploding

import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import java.io.ByteArrayInputStream

class ExplodingGameTest {


    @Test
    fun `when initialising, creates cards with 1 exploding card`() {
        val explodingGame = ExplodingGame(Player("Player 1"))

        assertThat(explodingGame.cards.size, equalTo(47))
        val explodingCards = explodingGame.cards.filterIsInstance<ExplodingCard>()
        assertThat(explodingCards.size, equalTo(1))
    }

    @Test
    fun `start the game and player draws a card`() {
        val explodingGame = ExplodingGame(Player("Player 1"))

        val inputStream = ByteArrayInputStream("draw\n".toByteArray())

        explodingGame.start(inputStream)
        assertThat(explodingGame.cards.size, equalTo(46))
    }
}