package com.kiwipower.exploding

import java.io.{InputStream, PrintStream}
import java.util.Scanner

import scala.collection.mutable
import scala.util.Random


class ExplodingGame(
                     player: Player,
                     inputStream: InputStream = System.`in`,
                     outputStream: PrintStream = System.out,
                     var cards: mutable.Queue[Card] = mutable.Queue()
                   ) {

  var gameover = false
  val MAX_NUMBER_OF_CARDS_IN_DECK = 49
  val scanner = new Scanner(inputStream)

  def start() {
    if (cards.isEmpty) {
      initialiseCards()
    }
    outputStream.println(s"${player.name} is playing exploding")
    outputStream.println("to draw a card, enter \"draw\"")
    outputStream.println(s"${player.name} please draw a card:")


    while (scanner.hasNext()) {
      scanner.next() match {
        case "draw" => drawACard()
        case "restart" =>
          initialiseCards()
          outputStream.println(s"Cards have been shuffled, please draw a card ${player.name}")
        case "exit" =>
          outputStream.println("Quitting game!")
          return
        case unknownCommand =>
          outputStream.println(s"Unknown command $unknownCommand")
          outputStream.println("Please try a valid action.")
      }
    }
  }

  private def drawACard(): Unit = {
    if (gameover) {
      outputStream.println("You need restart the game!")
    } else {
      val cardDrawn = cards.dequeue()
      cardDrawn match {
        case _: ExplodingCard =>
          if (player.defuseCards > 0) {
            outputStream.println("You drew the exploding card, but defused it. Draw again!")
            player.defuseCards -= 1
            shuffleCards()
          } else {
            outputStream.println(s"You drew the exploding card! Game Over! Play again? (restart/exit)")
          }
          gameover = true
        case _: DefuseCard =>
          player.defuseCards += 1
          outputStream.println(s"You drew a defuse card, no you have ${player.defuseCards} defuse cards. Draw again!")
        case _ =>
          outputStream.println(s"You haven't exploded yet, keep going! Draw another card: ${cards.size} cards left")
      }
    }
  }

  def shuffleCards(): Unit = {
    cards = Random.shuffle(cards)
  }

  def initialiseCards(): Unit = {
    val blankCards = (0 until MAX_NUMBER_OF_CARDS_IN_DECK).map(_ => new BlankCard().asInstanceOf[Card])
    val mutableBlankCards: mutable.Queue[Card] = mutable.Queue(blankCards: _*)

    val explodingCardLocation = Math.floor(Math.random() * MAX_NUMBER_OF_CARDS_IN_DECK).toInt

    mutableBlankCards.update(explodingCardLocation, new ExplodingCard())

    val firstDefuseCardLocation: Int = getRandomLocation(List(explodingCardLocation))
    mutableBlankCards.update(firstDefuseCardLocation, new DefuseCard())

    val secondDefuseCardLocation = getRandomLocation(List(explodingCardLocation, firstDefuseCardLocation))
    mutableBlankCards.update(secondDefuseCardLocation, new DefuseCard())
    gameover = false
    cards = mutableBlankCards
  }

  def getRandomLocation(location: List[Int]): Int = {
    var newRandomLocation: Int = Math.floor(Math.random() * (MAX_NUMBER_OF_CARDS_IN_DECK)).toInt
    if (location.contains((i: Int) => i.equals(newRandomLocation))) {
      newRandomLocation = getRandomLocation(location)
    }
    newRandomLocation
  }

}
