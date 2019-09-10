package com.kiwipower.exploding

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, PrintStream}

import org.hamcrest.core.IsEqual.equalTo
import org.hamcrest.core.StringContains._
import org.junit.Assert.assertThat
import org.junit.Test

class ExplodingGameTest {


  @Test
  def `when initialising, creates cards with 1 exploding card`() {
    val explodingGame = new ExplodingGame(new Player("Player 1"))

    assertThat(explodingGame.cards.size, equalTo(47))
    val explodingCards = explodingGame.cards.filter(card => card.isInstanceOf[ExplodingCard])
    assertThat(explodingCards.size, equalTo(1))
  }

  @Test
  def `start the game and player draws a card`() {
    val explodingGame = new ExplodingGame(new Player("Player 1"))

    val inputStream = new ByteArrayInputStream("draw\n".getBytes())
    val outputStream = new ByteArrayOutputStream()

    explodingGame.start(inputStream, new PrintStream(outputStream))

    val outputs = new String(outputStream.toByteArray)

    assertThat(explodingGame.cards.size, equalTo(46))
    assertThat(outputs,
      containsString("Player 1 is playing exploding\r\n" +
        "to draw a card, enter \"draw\"\r\n" +
        "Player 1 please draw a card:\r\n")
    )
  }

  @Test
  def `can restart and exit the game`(): Unit = {
    val explodingGame = new ExplodingGame(new Player("Player 1"))

    val inputStream = new ByteArrayInputStream("draw\nrestart\ndraw\nexit\n".getBytes())
    val outputStream = new ByteArrayOutputStream()

    explodingGame.start(inputStream, new PrintStream(outputStream))

    val outputs = new String(outputStream.toByteArray)

    assertThat(explodingGame.cards.size, equalTo(46))
    assertThat(outputs, containsString("Cards have been shuffled, please draw a card Player 1\r\n"))
    assertThat(outputs, containsString("Quitting game!\r\n"))

  }

}