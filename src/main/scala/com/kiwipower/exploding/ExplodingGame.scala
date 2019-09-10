package com.kiwipower.exploding

import java.io.{InputStream, PrintStream}
import java.util.Scanner

import scala.collection.{immutable, mutable}


class ExplodingGame(player: Player) {

  val cards: mutable.Queue[Card] = shuffleCards()


  def start(inputStream: InputStream = System.`in`, outputStream: PrintStream = System.out) {
    outputStream.println(s"${player.name} is playing exploding")
    outputStream.println("to draw a card, enter \"draw\"")
    outputStream.println(s"${player.name} please draw a card:")

    val scanner = new Scanner(inputStream)

    while (scanner.hasNext()) {
      scanner.next() match {
        case "draw" =>
          val cardDrawn = cards.dequeue()
          if (cardDrawn.isInstanceOf[ExplodingCard]) {
            outputStream.println(s"You drew the exploding card! Game Over! Play again? (restart/exit)")
          } else {
            outputStream.println(s"You haven't exploded yet, keep going! Draw another card: ${cards.size} cards left")
          }
        case unknownCommand =>
          outputStream.println(s"Unknown command $unknownCommand")
          outputStream.println("Please try a valid action.")
      }
    }

  }

  private def shuffleCards(): mutable.Queue[Card] = {
    val blankCards = (0 to 46).map(_ => new BlankCard().asInstanceOf[Card])
    val mutableBlankCards: mutable.Queue[Card] = mutable.Queue(blankCards: _*)
    val randomNumber = Math.floor(Math.random() * 46).toInt
    val explodingCard = new ExplodingCard()

    mutableBlankCards.update(randomNumber, explodingCard)
    mutableBlankCards
  }

}
