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

###Physics
BubbleCombat uses a Java port of the popular Box2D physics engine. The engine handles force application and collision detection. Each gesture input is converted to a force vector which is then applied to the player's bubble on the next time step. Since players are very fast-moving objects, the so-called tunneling effect can sometimes be observed (for instance, a very fast moving player could "tunnel" through a wall). To handle this, all objects' dimensions are up-scaled, imposing a lower cap on their apparent speed, but also making collision detection easier for the engine and therefore more robust.

### Client synchronization
After converting a user gesture to a force vector, the player's state (i.e. position, linear velocity, force due to the input, health) is saved and sent to other peers. Each receiving player then locally restores the incoming state for the corresponding player's bubble and continues the physics simulation locally. Due to the network latency, however, this alone is not enough to ensure synchronization between the peers' local simulations. That is why the player's state is also sent several times per second, which ensures eventual consistency between simulations.
