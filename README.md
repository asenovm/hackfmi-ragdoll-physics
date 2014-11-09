Bubble Combat
=======================

##Overview
A multiplayer Android combat game where 2+ bubbles (players) can fight with each other using touch gestures. Depending on the force and direction applied in the gesture the bubbles will accelerate and inflict damage on each other thus lowering their health. The one who has higher speed will cause more damage on its enemy in case of collision, but will also lower its own health if it misses the oponent and hits some of the special objects (obstacles) on the level.

##Game Lobby
The first screen the user sees upon opening **BubbleCombat** is the game lobby where all the currently active games are listed. The user can join each one of them upon selecting the respective item. In case no games are listed, the user can either refresh the game room (make a new search for active games), or conversely, create a game of his own that others can join by clicking the HOST button. Each hosted game can be joined for a time window of 120 seconds.
![GameLobby]("https://raw.githubusercontent.com/asenovm/hackfmi-ragdoll-physics/master/Screeshots/game_lobby.png", "game_lobby")

##Establishing connection
After one user hosts a game and another joins it, a *Bluetooth* connection is established between the 2 devices, so that they can negotiate the terms of the connection. Typically, the joining player will send a JOIN_GAME message to the host, making him aware that he is ready to join. When the host is ready to start the game will send a GO message letting the joined player know that he can start the game. Along with the GO message the host will send a timestamp, so that the joining side can sync his time with the host. Once the connection is established both players start the game.

##Game (connection logic)
Once the game is started each player starts listening for events coming from the other player. Each event contains the X and Y coordinates of the player, his current health and velocity along with the force he has applied when making the touch event. With the new events arriving the position of the remote player is updated respectively. Our player is also constantly sending his position so that it can be reflected as well.

##Game (gameplay details)
After the game is started, the level is loaded along with the bubbles of the 2+ players. In the middle of the level there is a spinning obstacle which lowers the health of the bubbles when hit, but also changes the direction of their movement along with their speed. When the bubbles of the players collide, both players lose health with the one who had bigger acceleration losing more. When the health of one of the players reaches 0 the game ends with the player with 0 health losing it and the others being winners.

![GamePlay1]("https://raw.githubusercontent.com/asenovm/hackfmi-ragdoll-physics/master/Screeshots/game_play_1.png", "game_play_1")

![GamePlay2]("https://raw.githubusercontent.com/asenovm/hackfmi-ragdoll-physics/master/Screeshots/game_play_2.png", "game_play_2")

##Technical details

###Physics
BubbleCombat uses a Java port of the popular Box2D physics engine. The engine handles force application and collision detection. Each gesture input is converted to a force vector which is then applied to the player's bubble on the next time step. Since players are very fast-moving objects, the so-called tunneling effect can sometimes be observed (for instance, a very fast moving player could "tunnel" through a wall). To handle this, all objects' dimensions are up-scaled, imposing a lower cap on their apparent speed, but also making collision detection easier for the engine and therefore more robust.

### Client synchronization
After converting a user gesture to a force vector, the player's state (i.e. position, linear velocity, force due to the input, health) is saved and sent to other peers. Each receiving player then locally restores the incoming state for the corresponding player's bubble and continues the physics simulation locally. Due to the network latency, however, this alone is not enough to ensure synchronization between the peers' local simulations. That is why the player's state is also sent several times per second, which ensures eventual consistency between simulations.
