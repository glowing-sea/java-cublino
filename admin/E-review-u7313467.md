## Code Review

**Reviewed by:** Rajin Hossain (u7313467)

**Reviewing code written by:** Haoting Chen (u7227871)

**Component:** [isValidStepPur(String state, String step)](https://gitlab.cecs.anu.edu.au/u7313467/comp1140-ass2-tue09q/-/blob/master/src/comp1140/ass2/Cublino.java#L259-303)

### Comments 

**Best Features**
- Effective/efficient choice of primitive type byte over char or int. This means less data is allocated in memory since only 8 bits are allocated for a byte. Additionally, reduces further casting/conversion to int for arithmetic operations.
- First if statement is cleaverly placed which prevents redundant operations and calculations from occuring.

**Documentation**
- The code is written in such a way that the code documents itself (easy to follow along with the appropriate indentifiers chosen).
- Necessary comments are placed to describe which criteria is being checked from the method specifications provided.

**Program Decomposition**
- The method structure is appropriate in the context of the whole Cublino class as isValidStepPur would be crucial for use later on in development, especially for the AI.
- The method appropriately decomposes the input state and step into its components which allows for this method to be very readable and hence easier to debug or change later on.
- As mentioned, all the return false situations that can be covered immediately are placed at the top of the function to reduce unnecessary calculations and operations.
- Could have implemented much of the logic through object creation which would have improved overall readability (minor suggestion). 

**Java Code Conventions**
- Java code conventions are met as the method name is camel-cased as well as the variable names being selected appropriately.
- Some variable name/identifiers could be more description without commenting such as [String over;](https://gitlab.cecs.anu.edu.au/u7313467/comp1140-ass2-tue09q/-/blob/master/src/comp1140/ass2/Cublino.java#L269) and [byte forward;](https://gitlab.cecs.anu.edu.au/u7313467/comp1140-ass2-tue09q/-/blob/master/src/comp1140/ass2/Cublino.java#L268)
- Code style is maintained throughout the method

**Suspected Error in Code**
- Traced the method manually on paper with a table. No errors were found in the logic.
- Task 7 tests pass fully and the method's output meets the specifications listed on the Assignment.





