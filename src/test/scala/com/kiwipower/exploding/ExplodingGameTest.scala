package com.kiwipower.exploding

import java.io.ByteArrayInputStream

import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class ExplodingGameTest {


    @Test
    def `when initialising, creates cards with 1 exploding card`() {
        val explodingGame = new ExplodingGame(new Player("Player 1"))

        assertThat(explodingGame.cards.size, equalTo(47))
        val explodingCards = explodingGame.cards.filter( card => card.isInstanceOf[ExplodingCard])
        assertThat(explodingCards.size, equalTo(1))
    }

    @Test
    def `start the game and player draws a card`() {
        val explodingGame = new ExplodingGame(new Player("Player 1"))

        val inputStream = new ByteArrayInputStream("draw\n".getBytes())

        explodingGame.start(inputStream)
        assertThat(explodingGame.cards.size, equalTo(46))
    }
}