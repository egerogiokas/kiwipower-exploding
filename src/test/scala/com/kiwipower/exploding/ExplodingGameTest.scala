package com.kiwipower.exploding

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, PrintStream}

import org.hamcrest.core.IsEqual.equalTo
import org.hamcrest.core.IsNot.not
import org.hamcrest.core.StringContains._
import org.junit.Assert.assertThat
import org.junit.Test

import scala.collection.mutable

class ExplodingGameTest {

  @Test
  def `when starting the game, creates cards with 1 exploding card`() {
    val player = Player("Player 1")
    val explodingGame = new ExplodingGame(player, new ByteArrayInputStream("exit".getBytes()))

    explodingGame.start()

    assertThat(player.defuseCards, equalTo(1))
    assertThat(explodingGame.cards.size, equalTo(49))

    val explodingCards = explodingGame.cards.filter(card => card.isInstanceOf[ExplodingCard])
    assertThat(explodingCards.size, equalTo(1))

    val defuseCards = explodingGame.cards.filter(card => card.isInstanceOf[DefuseCard])
    assertThat(defuseCards.size, equalTo(2))
  }

  @Test
  def `start the game and player draws a card`() {
    val inputStream = new ByteArrayInputStream("draw\n".getBytes())
    val outputStream = new ByteArrayOutputStream()

    val explodingGame = new ExplodingGame(new Player("Player 1"), inputStream, new PrintStream(outputStream))


    explodingGame.start()

    val outputs = new String(outputStream.toByteArray)

    assertThat(explodingGame.cards.size, equalTo(48))
    assertThat(outputs,
      containsString("Player 1 is playing exploding\r\n" +
        "to draw a card, enter \"draw\"\r\n" +
        "Player 1 please draw a card:\r\n")
    )
  }

  @Test
  def `can restart and exit the game`(): Unit = {
    val inputStream = new ByteArrayInputStream("draw\nrestart\ndraw\nexit\n".getBytes())
    val outputStream = new ByteArrayOutputStream()

    val explodingGame = new ExplodingGame(new Player("Player 1"), inputStream, new PrintStream(outputStream))

    explodingGame.start()

    val outputs = new String(outputStream.toByteArray)

    assertThat(explodingGame.cards.size, equalTo(48))
    assertThat(outputs, containsString("Cards have been shuffled, please draw a card Player 1\r\n"))
    assertThat(outputs, containsString("Quitting game!\r\n"))
  }

  @Test
  def `don't allow to keep drawing after gameover and is gameover with no defuses`(): Unit = {
    val inputStream = new ByteArrayInputStream((0 to 2).map(_ => "draw").mkString("\n").getBytes())
    val outputStream = new ByteArrayOutputStream()

    val explodingGame = new ExplodingGame(
      Player("Player 1", 0),
      inputStream,
      new PrintStream(outputStream),
      mutable.Queue(new ExplodingCard(), new BlankCard())
    )

    explodingGame.start()

    val outputs = new String(outputStream.toByteArray)

    assertThat(outputs, containsString("You drew the exploding card! Game Over! Play again? (restart/exit)\r\n"))
    assertThat(outputs, containsString("You need restart the game!\r\n"))
  }

  @Test
  def `if player has a defuse, and pulls exploding, removes defuse card from player and shuffles deck`(): Unit = {
    val cards = mutable.Queue(
      new ExplodingCard(),
      new BlankCard(),
      new BlankCard(),
      new BlankCard(),
      new BlankCard(),
      new BlankCard(),
      new BlankCard(),
      new BlankCard(),
      new BlankCard(),
      new BlankCard(),
      new BlankCard()
    )
    val inputStream = new ByteArrayInputStream("draw\n".getBytes())
    val outputStream = new ByteArrayOutputStream()

    val explodingGame = new ExplodingGame(
      Player("Player 1"),
      inputStream,
      new PrintStream(outputStream),
      cards
    )
    explodingGame.start()

    val outputs = new String(outputStream.toByteArray)

    assertThat(outputs, containsString("You drew the exploding card, but defused it. Draw again!\r\n"))
    assertThat(explodingGame.cards, not(equalTo(cards)))
  }

  @Test
  def `if draw defuse card, increment defuse`(): Unit = {
    val inputStream = new ByteArrayInputStream("draw\n".getBytes())
    val outputStream = new ByteArrayOutputStream()

    val player = Player("Player 1")
    val explodingGame = new ExplodingGame(
      player,
      inputStream,
      new PrintStream(outputStream),
      mutable.Queue(
        new DefuseCard(),
        new BlankCard()
      )
    )

    explodingGame.start()

    val outputs = new String(outputStream.toByteArray)

    assertThat(outputs, containsString("You drew a defuse card, no you have 2 defuse cards. Draw again!\r\n"))
    assertThat(player.defuseCards, equalTo(2))
    assertThat(explodingGame.cards.size, equalTo(1))
  }


}