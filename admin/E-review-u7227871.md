## Code Review

Reviewed by: Haoting Chen, u7227871

Reviewing code written by: Anubhav Saxena u7227566

Component: [changeFaces(int[] initialFaces, Step step)](https://gitlab.cecs.anu.edu.au/u7313467/comp1140-ass2-tue09q/-/blob/master/src/comp1140/ass2/core/Dice.java#L132-185)

### Comments 

The function takes a list of numbers on each face of a dice and a step and a tip step. It returns a new list of numbers on each face after implementing the step. If the input is not a tip step, the function will output the same list.

The best feature of the code is that it is well-structured. The function considers that a tip may be forward, right, left, and backward (invalid step). It then deals with the cases separately. However, using “switch” statement here may be better than using “if then else” statement because there are more than two cases. The function can also deal with unexpected like a step that is not a tip. However, throwing an exception when an unexpected thing is input may be better because it can avoid the programmer's failure to realise the input is wrong.

The code is relatively well-documented. It contains a comment as well as the author. However, the comment can be more descriptive, although tipping implies changing faces. The comment can be something like, “Update the faces of a dice after a tip”.

The program decomposition is appropriate. The function correctly refers to the input object, step. And the function correctly uses the getter methods of the step to get the x and y coordinates. The syntax of the function is correct.

The code follows Java convention. The variable like “initialFaces”, “newFaces” and “step” are named correctly and appropriately.

There may be some errors in the code. The faces on a dice are saved in the order {TOP, FORWARD, RIGHT, BEHIND, LEFT, BOTTOM}. When the dice is tipped to the right, the new top face is the old left face, and the new bottom face is the old right face. The forward face and the behind face remain unchanged. Therefore, the first case (tip to the right) of the code may become:

newFaces[0] = initialFaces[4]; // The new top face is the old left face.

newFaces[1] = initialFaces[1]; // Unchanged.

newFaces[2] = initialFaces[0]; // The new right face is the old top face

newFaces[3] = initialFaces[3]; // Unchanged.
newFaces[4] = initialFaces[5]; // The new left faces is the old bottom face.

newFaces[5] = initialFaces[2]; // The new bottom faces is the old right face.


