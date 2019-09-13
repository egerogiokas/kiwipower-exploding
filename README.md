
### Assumption of one player
I made the assumption that, for now, I'll only deal with 1 player.
Obviously in a real game, more than one would be needed but to keep it simple,
I decided to not implement.

### Implementation of losing mechanics
What happens when a player "loses" wasn't specified so I introduced "restart" and
"exit" mechanics.
The way it works at the moment, the user can call either of these whenever. If this
isn't desireable however it can be changed. I just did the simplest thing for now,
rather than assume something more complex that was unnecessary.

### Allowing testability
I provide inputstream, printstream, and a list of cards in the ExplodingGame.
This is purely to make it easier to test. I don't like this, because if I make an 
instance of this class I shouldn't have to care about these things.
But providing testability was worth this downside in my opinion.
I would have preferred a different solution, but I couldn't think of one.

### Gradle
I'm not too familiar with scala and haven't used SBT, so I opted to gradle
I had also originally been implementing in kotlin, so had gradle already there.

### JUnit4 and Hamcrest
Tried and tested. Didn't think it was necessary to go too crazy with my testing framework. Could have done
JUnit 5 I suppose, but don't gain that much.

### Cards
I'm not sure the way I implemented Cards was the best way in Scala. Downsides of being unfamiliar with the language.
Originally thought I could do it as a Trait but had some issue... Didn't want to spend too much time nitpicking so
got it working with what I have and left it.

### ExplodingGame class
Currently this class does several things technically. It does the REPL as well as the actual "actions".
By that I mean it reads "draw" and has all the logic for drawing a card. This probably should be abstracted,
however I didn't feel that the code was in need of the abstraction quite yet.
If we had other more involved actions (e.g. skip and reverse) then it may make sense to start moving "actions" into
separate classes.

