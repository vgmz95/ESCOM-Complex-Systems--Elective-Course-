# ESCOM Complex Systems (Elective Course)
This repository contains all the code made during the optative course of Complex systems at IPN - ESCOM, these programs were made entirely on Java and JavaFX.

## One dimensional automaton 
This program generates all 255 rules of the [elementary cellular automaton](https://en.wikipedia.org/wiki/Elementary_cellular_automaton#Random_initial_state), the initial array can be generated randomly, by typing it into the console or by reading a plain text archive. 

<p align="center">
<img src="/img/1D.PNG" alt="1D automaton" width="400"/>
</p>

## Game Of Life 
This is program is capable of displaying all the ["life-like"](https://en.wikipedia.org/wiki/Life-like_cellular_automaton) rules for a 2D cellular automaton, including Conway's [Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life).
Other characteristics include: 
* Pause or continue the evolution using keyboard and mouse.
* Increase or decrease the speed of the evolutions, with a maximum of 60 frames per second, using keyboard or mouse.
* Edit the evolutions space in execution time by using the mouse.
* It supports a space of evolutions of up to 1000x1000.
* Three initial configuration modes: Empty, random, random space with probability up to 0.001.
* Accepts various scale factors for the visualization of very large or very small spaces.
### Controls
* P: Pauses the evolution
* C: Continues the evolution
* +/-: Increases/Decreases the speed pf the evolution
* Click : Toggles a square in the evolutionary space (it is recommended to pause the game before editing)

<p align="center">
<img src="/img/GameOfLife.gif" alt="Game of life" width="500"/>
</p>

## Biham–Middleton–Levine traffic model (BML)
This is variaton of the [BML traffic model](https://en.wikipedia.org/wiki/Biham%E2%80%93Middleton%E2%80%93Levine_traffic_model), adding one degree of freedom to the model. In this implementation cars can turn right (if they move from left to right) or to the left (if they move from top to bottom) under a given probability, which in this case was 10%. It includes the same features and controls from the Game of Life implementation.

<p align="center">
<img src="/img/BML.png" alt="1D automaton" width="400"/>
</p>
