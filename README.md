# Cube Crusade

A simple game made to start my learning process for a larger scale software development project. 



In making this game, I somewhat followed the Java game development youtube tutorial made by RealTutsGML, who can be found here: https://www.youtube.com/@RealTutsGML

The first video in this tutorial series, titled "Java Programming: Let's Build a Game #1," can be found here: https://www.youtube.com/watch?v=1gir2R7G9ws



While the surface of the game remains the same, I went through several large refactors including switching to dependency injection, renaming variables to be more descriptive, various performance improvements, and extensive logic encapsulation where the original project had large, monolithic functions that served too many purposes. I wish I had picked a proper name though, the placeholder name ended up just sticking.



Some key takeaways from this project:

1. I will be using a game engine in the future should I decide to develop as a hobby. While I love Java, you shouldn't force a one language or tool over another because you like it, you should choose the best of for the job that will save you the most time and effort while delivering the necessary results. 
2. Project structure is important and should not be neglected; while I didn't pay close attention to this back then, it is a bit difficult to look at now seeing just how unorganized the project is. Every file is lumped under the main folder with nothing to group or logically separate the various files. A noteworthy item in this space are my enemies; each enemy is named a little oddly; EnemySlow, EnemyFast, etc. While not the worst, I think it would be far better to have an enemies folder which contains a SlowEnemy, FastEnemy, etc. This would be easier to both read and manage while avoiding the slightly odd names.
3. Git and GitHub are very useful; this project was one of the first where I realized the value git brings to a project. I was always aware of it but wasn't a stoic practitioner, but after a very large refactor where I was proud to show off my work, I quickly realized I had nothing to properly compare it to because I didn't have version tracking in place. In addition, there were various times I broke a feature or similar and being able to roll back, or at least consult the previous function would have saved me hours. These days the first thing I do is create a local git repository and set up a remote branch in GitHub. 
4. The potential for comments. I pieced together over time that comments should be the "why" where needed, not the "what" (except in especially complex cases) being plastered randomly around. I did, at least, stick to Javadoc comments for the most part, which were nice. If I had to re-do this, I'd be more thorough with my Javadoc comments (In fact, I'd likely start each function with a Javadoc comment explaining the purpose first), then I'd add various "why" comments to key functions and areas where I made large scale design decisions, such as in my mediator class to explain why I created it (Short version, there were some very confusing cases of passing data down through classes and through inheritance in a convoluted and messy way that were cleaned up nicely through the Mediator. It was not without its own drawbacks, however).
5. Logic encapsulation is especially and code should remain somewhat modular. In this project there are a few egregious instances of giant functions that should be broken up into smaller, more modular pieces. While I think that monolithic functions sometimes have their place, there is always a balance to be struck and I missed the mark in a few places. For a good example, my StateHandler class has a createMenuBoxItem class which does far too many things at once that I'd likely split off into subfunctions if I were working on this today. And to point #4, if you head to the mainMenuInteractions function beneath it, you can also see an example of useless comments such as
```
   else if (mouseOverItem(menuSettingsBox)) // If we press the settings button, switch to settings
         {mediator.getGame().gameState = Game.STATE.Settings; ...
```



Overall, I'm pretty proud of the project as I modified the code to improve it in numerous ways while learning more about programming as a whole. While it still had a long way to go to be truly good by the time I finished it, I think that can be said about nearly any project and at some point you have to decide on good enough.





See a basic description of the game below



There are a few features: A main menu, with music, menu particles, a starry background, and a settings menu with a mute button, volume control, and easy/hard difficulty settings. 

Once Play is pressed, you spawn as a square with 100 hp and 2 speed, and an enemy is spawned (the type is based on difficulty). As your score increases, so does the current level and the amount of currency ("Gold") you have. You can purchase health upgrades, speed upgrades, and refill your health. Each upgrade cost is equal to the next level of the ability \* 100. So purchasing the first upgrade costs 100, the 2nd 200, etc. Health increases by 25 per purchase, and speed by .1 per purchase. Refill health simply refills your health to whatever the current maximum is.

The controls are as follows:

W, Up Arrow = Move the player up  
A, Left Arrow = Move the player left  
S, Down Arrow = Move the player down  
D, Right Arrow = Move the player right  
M = Mute/Unmute the game  
P, Escape = Pause/Unpause the game  
Space = Open/Close the shop

The levels are not in a great state as they are based on a timer and not especially interesting. Levels are populated in a Spawn class, which has a SpawnMap, whose logic is based on the
current level, to spawn different types of enemies. A boss is spawned every 10 levels, and disappears after 8 seconds.
Once you lose all of your health, the game ends, displayed a game over screen with your level and score. You can then go back to the main menu to exit or play again.
There is no resizing available. There is no icon.



Currently, credits are in game and below:



Thanks to RealTutsGML for the tutorial

Thanks to Kevin MacLeod, who made the music "Half Mystery" used for the in-game audio  
"Half Mystery" Kevin MacLeod (incompetech.com) Licensed under Creative Commons: By Attribution 4.0 License "http://creativecommons.org/licenses/by/4.0/"

And again thanks to Kevin MacLeod, who made the music "Voxel Revolution" used for the menu audio  
"Voxel Revolution" Kevin MacLeod (incompetech.com) Licensed under Creative Commons: By Attribution 4.0 License "http://creativecommons.org/licenses/by/4.0/"
