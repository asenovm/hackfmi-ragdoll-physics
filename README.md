Bubble Combat
=======================

##Overview
A multiplayer Android combat game where 2+ bubbles (players) can fight with each other using touch gestures. Depending on the force and direction applied in the gesture the bubbles will accelerate and inflict damage on each other thus lowering their health. The one who has higher speed will cause more damage on its enemy in case of collision, but will also lower its own health if it misses the oponent and hits some of the special objects (obstacles) on the level.

##Game Lobby
The first screen the user sees upon opening **BubbleCombat** is the game lobby where all the currently active games are listed. The user can join each one of them upon selecting the respective item. In case no games are listed, the user can either refresh the game room (make a new search for active games), or conversely, create a game of his own that others can join by clicking the HOST button. Each hosted game can be joined for a time window of 120 seconds.

##Establishing connection
After one user hosts a game and another joins it, a *Bluetooth* connection is established between the 2 devices, so that they can negotiate the terms of the connection. Typically, the joining player will send a JOIN_GAME message to the host, making him aware that he is ready to join. When the host is ready to start the game will send a GO message letting the joined player know that he can start the game. Along with the GO message the host will send a timestamp, so that the joining side can sync his time with the host. Once the connection is established both players start the game.

##Game (connection logic)
Once the game is started each player starts listening for events coming from the other player. Each event contains the X and Y coordinates of the player, his current health and velocity along with the force he has applied when making the touch event. With the new events arriving the position of the remote player is updated respectively. Our player is also constantly sending his position so that it can be reflected as well.

##Game (gameplay details)
After the game is started, the level is loaded along with the bubbles of the 2+ players. In the middle of the level there is a spinning obstacle which lowers the health of the bubbles when hit, but also changes the direction of their movement along with their speed. When the bubbles of the players collide, both players lose health with the one who had bigger acceleration losing more. When the health of one of the players reaches 0 the game ends with the player with 0 health losing it and the others being winners.

##Technical details
