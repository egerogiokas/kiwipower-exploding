package com.kiwipower.exploding

import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class ExplodingGameTest {

    @Test
    fun `when initialising, creates cards with 1 exploding card`() {
        val explodingGame = ExplodingGame(Player())

        assertThat(explodingGame.cards.size, equalTo(47))
        val explodingCards = explodingGame.cards.filterIsInstance<ExplodingCard>()
        assertThat(explodingCards.size, equalTo(1))
    }
}