package comp1140.ass2.core;

public class Step {
    private Location startPosition;
    private Location endPosition;
    String encode;

    public Step(String step) {
        this.startPosition = new Location(step.substring(0,2));
        this.endPosition = new Location(step.substring(2,4));
        this.encode = step;
    }

    public boolean isTip(){
        return startPosition.isAdjacent(endPosition);
    }

    static public boolean isTipPlus(String stepEncode){
        Step step = new Step (stepEncode);
        return step.startPosition.isAdjacent(step.endPosition);
    }


    // Setter and getter method
    public void setStep(String step) {
        this.startPosition = new Location(step.substring(0,2));
        this.endPosition = new Location(step.substring(2,4));
        this.encode = step;
    }
    public Location getStartPosition() {return startPosition;}
    public Location getEndPosition() {return endPosition;}
    public String getEncode() {return encode;}
}
