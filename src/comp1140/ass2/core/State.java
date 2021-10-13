package comp1140.ass2.core;

import comp1140.ass2.Cublino;

import java.util.ArrayList;

// (By Group)
public class State {
    private static final int BOARD_SIZE = 7;
    private boolean player1Turn; // "true" means player1's (White) turn."false" means player2 (Black) turn.
    private boolean pur; // "true" means Pur."false" mean Contra.
    private ArrayList<Dice> dices = new ArrayList<>();

    //================================================CONSTRUCTOR=====================================================//

    // (Created by Rajin, edited by Haoting)
    public State(String state) {
        assert Cublino.isStateWellFormed(state) : "The input state string is not well-formed.";

        this.player1Turn = state.charAt(0) == 'P' || state.charAt(0) == 'C';
        this.pur = state.charAt(0) == 'P' || state.charAt(0) == 'p';

        // add all the dice pieces
        for (int i = 1; i < state.length(); i += 3) {
            this.dices.add(new Dice(state.substring(i, i + 3)));
        }
    }

//    public State(String state) {
//        if (Cublino.isStateWellFormed(state)) {
//            String diceEncodings = state.substring(1); // all the dice encodings are assumed to be valid
//
//            // add all the dice pieces
//            for (int i = 0; i < diceEncodings.length()-2; i+=3) {
//                this.dices.add(new Dice(diceEncodings.substring(i,i+3)));
//            }
//
//            this.player1Turn = state.charAt(0) == 'P' || state.charAt(0) == 'C';
//        }
//    }
//

    //=============================================NON-STATIC METHODS=================================================//

    // (By Haoting)
    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        if(this.pur)
            output.append( player1Turn ? 'P' : 'p');
        else
            output.append( player1Turn ? 'C' : 'c');

        for (Dice dice : dices)
            output.append(dice);

        return output.toString();
    }

    // (By Rajin)
    // Gets the piece at an X and Y coordinate
    public Dice getPieceAt(int x, int y) {
        for (Dice dice:dices) {
            if (dice.getPosition().getX() == x && dice.getPosition().getY() == y) {
                return dice;
            }
        }
        return null;
    }

    // (By Rajin)
    // Gets all pieces that are either white or black based on the input boolean
    public ArrayList<Dice> getPieces(boolean isWhitePieces) {
        ArrayList<Dice> dices = new ArrayList<>();

        for (Dice dice: this.dices) {
            if (isWhitePieces) {
                if (Character.isUpperCase(dice.toString().charAt(0))) {
                    dices.add(dice);
                }
            } else {
                if (!Character.isUpperCase(dice.toString().charAt(0))) {
                    dices.add(dice);
                }
            }
        }

        return dices;
    }

    // (By Haoting Chen)
    // Given a position, check if there is a dice at that position.
    public boolean containDice (Position position){
        for(Dice dice : this.getDices())
            if (position.equals(dice.getPosition()))
                return true;
        return false;
    }

    // (By Haoting Chen)
    // Given a position and a player name, check if there is a player's dice at that position.
    public boolean containPlayerDice (Position position, Boolean isPlayer1){
        for(Dice dice : this.getDices())
            if (position.equals(dice.getPosition()) && dice.isPlayer1() == isPlayer1)
                return true;
        return false;
    }


    // Getter and setter methods (By Group)
    public ArrayList<Dice> getDices() {return dices;}
    public void setPlayer1Turn(boolean player1Turn) {this.player1Turn = player1Turn;}
    public void setDices(ArrayList<Dice> dices) {this.dices = dices;}
    public void setPur(boolean pur) {this.pur = pur;}
    public boolean getPlayerTurn() {return this.player1Turn;}


    //=================================================STATIC METHODS=================================================//
    //======================================================TESTS=====================================================//

    public static void main(String[] args) {
        State state1 = new State("Pwb1bc1sf1if2ca3ub3gc3Cb5Mb6Hf6Oa7Fb7Sc7We7");
        State state2 = new State("PMb6");
        Position pos = new Position("b6");
        System.out.println(state2.containDice(pos));
        System.out.println(state1.toString().equals("Pwb1bc1sf1if2ca3ub3gc3Cb5Mb6Hf6Oa7Fb7Sc7We7"));
    }
}
