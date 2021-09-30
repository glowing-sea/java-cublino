package comp1140.ass2.core;

public class Step {
    private Position start;
    private Position end;

    //================================================CONSTRUCTOR=====================================================//

    // A simple constructor of a step.
    public Step(String step) {
        this.start = new Position(step.substring(0,2));
        this.end = new Position(step.substring(2,4));
    }

    // A constructor of a step based on its encoding.
    public Step(Position startPosition, Position endPosition) {
        this.start = startPosition;
        this.end = endPosition;
    }

    //=============================================NON-STATIC METHODS=================================================//

    @Override
    public String toString() {
        return "" + start + end;
    }

    // Setter and getter method
    public void setStep(String step) {
        this.start = new Position(step.substring(0,2));
        this.end = new Position(step.substring(2,4));
    }

    public Position getStartPosition() {return start;}
    public Position getEndPosition() {return end;}

    public boolean isTip(){
        return start.isAdjacent(end);
    }


    //=================================================STATIC METHODS=================================================//

    static public boolean isTipPlus(String stepEncode){
        Step step = new Step (stepEncode);
        return step.start.isAdjacent(step.end);
    }

    //======================================================TESTS=====================================================//

    // Check Methods
    public static void main(String[] args) {
        Step s1 = new Step("a1a3");
        Step s2 = new Step("c5c7");
        Step s3 = new Step("g7g6");
        Step s4 = new Step("a1a3");
        System.out.println(s1 + "," + s2 + "," + s3 + "," + s4);
    }
}
