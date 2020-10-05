/*
    Basic class to hold all the players related data like his name, his total scoring, if he has scored 1 consequitvely,
    if he is allowed to play next turn.
 */

class Player {

    private String name;
    private int totalScore;
    private int firstRollAsOne;
    private boolean canPlay = true;

    boolean isCanPlay() {
        return canPlay;
    }

    void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    Player(String name){
        this.name = name;
    }

    String getName(){
        return name;
    }

    void addToTotalScore(int scoreForCurrentRound){
        totalScore += scoreForCurrentRound;
    }

    int getFirstRollAsOne() {
        return firstRollAsOne;
    }

    void setFirstRollAsOne() {
        this.firstRollAsOne = 1;
    }

    int getTotalScore(){
        return totalScore;
    }

}