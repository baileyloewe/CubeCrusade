# Cube Crusade

A 2D platformer game built in Java, originally developed by following a YouTube tutorial in 2022, then extensively
refactored, rearchitected, and redesigned as a personal engineering challenge.
Recently picked back up in 2026 to continue refining the project.

https://github.com/user-attachments/assets/73b81076-fcca-4e2e-98b7-c8518334f63a

Currently, the surface of the game remains the same, but I have completed several significant refactors
(and still have plenty to go) including implementing dependency injection, composition, observer patterns,
and various performance improvements.

See a basic description of the game below

There are a few features: A main menu, with music, menu particles, a starry background, and a settings menu with a mute
button, volume control, and easy/hard difficulty settings.

Once Play is pressed, you spawn as a square, and an enemy is spawned (enemy type is based on difficulty). As your score
increases, so does the current level and the amount of currency ("Gold") you have. You can purchase health upgrades,
speed upgrades, and refill your health. Each upgrade cost is equal to the next level of the ability \* 100.
So purchasing the first upgrade costs 100, the 2nd 200, etc. Health increases by 25 per purchase, and speed by
.1 units per purchase. Refill health simply refills your health to whatever the current maximum is.

The controls are as follows:

W, Up Arrow = Move the player up  
A, Left Arrow = Move the player left  
S, Down Arrow = Move the player down  
D, Right Arrow = Move the player right  
M = Mute/Unmute the game  
P, Escape = Pause/Unpause the game  
Space = Open/Close the shop

The levels are not in a great state as they are based on a timer and are not especially interesting. Levels (linked to
a timer through the current score) spawn different types of enemies. A boss is spawned every 10 levels, and disappears
after 8 seconds. Once you lose all of your health, the game ends, displaying a game over screen with your level and
score. You can then go back to the main menu to exit or play again.

Currently, credits are in game and below:

Thanks to RealTutsGML for the base game architecture and layout through his series on developing a game in
Java: https://www.youtube.com/watch?v=1gir2R7G9ws

Thanks to Kevin MacLeod, who made the music "Half Mystery" used for the in-game audio and main menu audio, linked below

"Half Mystery" Kevin MacLeod (incompetech.com) Licensed under Creative Commons: By Attribution 4.0
License "http://creativecommons.org/licenses/by/4.0/"

"Voxel Revolution" Kevin MacLeod (incompetech.com) Licensed under Creative Commons: By Attribution 4.0
License "http://creativecommons.org/licenses/by/4.0/"
