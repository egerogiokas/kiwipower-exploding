package com.kiwipower.exploding


object GameLauncher {

  def main() {

    val player = new Player("Player 1")
    new ExplodingGame(player).start()

  }
}