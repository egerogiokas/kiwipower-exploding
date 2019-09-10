package com.kiwipower.exploding


object GameLauncher {

  def main(args: Array[String]) : Unit = {

    val player = new Player("Player 1")
    new ExplodingGame(player).start()

  }
}