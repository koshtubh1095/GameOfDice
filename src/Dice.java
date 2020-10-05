import java.util.Random;

/*
    Dice class which returns a random generated number between 1 and 6.
 */

class Dice {

    private static final int NUMBER_OF_SIDES = 6;

    private Random r = new Random();

    int roll(){
        return r.nextInt(NUMBER_OF_SIDES) + 1;
    }
}