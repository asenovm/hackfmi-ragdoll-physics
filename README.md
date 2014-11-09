Bubble Combat
=======================

##Overview
A multiplayer Android combat game where 2+ bubbles (players) can fight with each other using touch gestures. Depending on the force and direction applied in the gesture the bubbles will accelerate and inflict damage on each other thus lowering their health. The one who has higher speed will cause more damage on its enemy in case of collision, but will also lower its own health if it misses the oponent and hits some of the special objects (hurdles) on the level.

##Game Lobby
The first screen the user sees upon opening **BubbleCombat** is the game lobby where all the currently active games are listed. The user can join each one of them upon selecting the respective item. In case no games are listed, the user can either refresh the game room (make a new search for active games), or conversely, create a game of his own that others can join by clicking the HOST button. Each hosted game can be joined for a time window of 120 seconds.

##Establishing connection
After one user hosts a game and another joins it, a *Bluetooth* connection is established between the 2 devices, so that they can negotiate the terms of the connection. Typically, the joining player will send a JOIN_GAME message to the host, making him aware that he is ready to join. When the host is ready to start the game will send a GO message letting the joined player know that he can start the game. Along with the GO message the host will send a timestamp, so that the joining side can sync his time with the host.

##Game

##Technical details
