package com.kiwipower.exploding

import java.io.{InputStream, PrintStream}
import java.util.Scanner

import scala.collection.mutable


class ExplodingGame(player: Player) {

  var cards: mutable.Queue[Card] = shuffleCards()
  var gameover = false


  def start(inputStream: InputStream = System.`in`, outputStream: PrintStream = System.out) {
    outputStream.println(s"${player.name} is playing exploding")
    outputStream.println("to draw a card, enter \"draw\"")
    outputStream.println(s"${player.name} please draw a card:")

    val scanner = new Scanner(inputStream)

    while (scanner.hasNext()) {
      scanner.next() match {
        case "draw" =>
          if (gameover) {
            outputStream.println("You need restart the game!")
          } else {
            val cardDrawn = cards.dequeue()
            if (cardDrawn.isInstanceOf[ExplodingCard]) {
              outputStream.println(s"You drew the exploding card! Game Over! Play again? (restart/exit)")
              gameover = true
            } else {
              outputStream.println(s"You haven't exploded yet, keep going! Draw another card: ${cards.size} cards left")
            }
          }
        case "restart" =>
          cards = shuffleCards()
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

  private def shuffleCards(): mutable.Queue[Card] = {
    val blankCards = (0 to 48).map(_ => new BlankCard().asInstanceOf[Card])
    val mutableBlankCards: mutable.Queue[Card] = mutable.Queue(blankCards: _*)

    val explodingCardLocation = Math.floor(Math.random() * 46).toInt

    mutableBlankCards.update(explodingCardLocation, new ExplodingCard())

    val firstDefuseCardLocation: Int = getRandomLocation(List(explodingCardLocation))
    mutableBlankCards.update(firstDefuseCardLocation, new DefuseCard())

    val secondDefuseCardLocation = getRandomLocation(List(explodingCardLocation, firstDefuseCardLocation))
    mutableBlankCards.update(secondDefuseCardLocation, new DefuseCard())
    gameover = false
    player.defuseCards += 1
    mutableBlankCards
  }

  def getRandomLocation(location: List[Int]): Int = {
    var newRandomLocation: Int = Math.floor(Math.random() * 46).toInt
    if (location.contains((i: Int) => i.equals(newRandomLocation))) {
      newRandomLocation = getRandomLocation(location)
    }
    newRandomLocation
  }


}
