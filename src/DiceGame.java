import java.util.*;

/*
    main class that performs all the computations and prints the results.
 */

public class DiceGame {

    private List<Player> listOfPlayers; //variable to store all the players who will be playing the game.
    private List<Player> finalListOfWinningOrder; //variable to hold the final result of the game, which player has won in what order.
    private int totalNoOfPlayers; // N number of players in the game

    private Dice dice; // dice object
    private int scoreToWin; // M score to as offset to see who wins the game.


    /*
        This is the first method that gets executed after driving method, which asks for different action from the user.
        Valid inputs :
        1 - To start a new game
        2 - Play a round
        3 - Exit from the complete system
     */

    private void run() {

        while (true) {

            System.out.println("(1) Start a new game");
            System.out.println("(2) Play one round");
            System.out.println("(3) Exit game");
            System.out.println("Choose an option: ");

            try {

                Scanner sc = new Scanner(System.in);
                int optionSelected = sc.nextInt();

                while (optionSelected < 0 || 3 < optionSelected) {
                    System.out.println("Option entered invalid, please enter a number between 1 - 5");
                    optionSelected = sc.nextInt();
                }

                if (optionSelected == 3) {
                    System.out.println("Exiting...");
                    break;
                }

                this.selectOption(optionSelected);

            } catch (InputMismatchException e) {
                System.out.println("Invalid user input, please enter choose between option 1 - 5");
            }
        }
    }

    private void selectOption(int optionSelected) {
        switch (optionSelected) {
            case 1:
                this.startNewGame();
                break;
            case 2:
                if(listOfPlayers.size() > 1) {

                    this.playRound(); //round should be played between more than one player.

                } else {

                    System.out.println("Looks like not many players left to play on board " +
                            ":: Total players left :: " + listOfPlayers.size() + " Better start fresh game as this games " +
                            " looks to be ended");
                }
                break;
            default:
                break;
        }
    }

    private void startNewGame() {

        listOfPlayers = new ArrayList<>();
        finalListOfWinningOrder = new ArrayList<>();
        dice = new Dice();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of players");

        totalNoOfPlayers = sc.nextInt(); //N number of players



        for(int i = 1; i <= totalNoOfPlayers; i++) {
            Player newPlayer = new Player("P"+i);
            System.out.println("Creating new player :: " + newPlayer.getName());
            listOfPlayers.add(newPlayer);
        }

        Collections.shuffle(listOfPlayers);

        System.out.println("*** PLAYERS ORDER OF PLAYING ***");
        for(Player player : listOfPlayers)
            System.out.println(player.getName());

        System.out.println("Please enter the upper score limit for the game.");
        scoreToWin = sc.nextInt(); //M score required to win the game.
    }

    private void playRound() {

        //round to be played among the list of players
        for (Player p : listOfPlayers) {

            if(p.isCanPlay()) { //if at all the player is eligible to role the dice

                System.out.println(p.getName() + " it's your turn. Please press r to roll dice");
                Scanner scanner = new Scanner(System.in);
                String s = scanner.nextLine();

                if(s.equalsIgnoreCase("r")) {

                    int currentRoundScore;
                    int firstResult = dice.roll();

                    //if at all the first number got is 6 then one more chance is given to the player.
                    if (firstResult == 6) {

                        System.out.println("It's a 6. Congrats you earned yourself one more chance. press r to roll again!!");
                        String input = scanner.nextLine();

                        if(input.equalsIgnoreCase("r")) {

                            int secondResult = dice.roll();
                            currentRoundScore = (firstResult + secondResult);
                            p.addToTotalScore(currentRoundScore);

                            if(p.getTotalScore() - scoreToWin > 0) {

                                System.out.println("Congrats " + p.getName() + " You have won by margin of " +
                                        (p.getTotalScore() - scoreToWin) + " points. Please wait for final result.");

                            } else {

                                System.out.println(p.getName() + " rolled" + firstResult + ", rolled again and scored"+ secondResult +
                                                " points(BONUS POINTS), for a total of"+ currentRoundScore
                                        +  " points. TOTAL SCORE : " + p.getTotalScore() + " points.");
                            }
                            p.setCanPlay(true);

                        } else {

                            System.out.println("Wrong input. Coming out");
                            break;

                        }

                    } else if(firstResult == 1) {

                        //To check if there are consecutive 1s then it player's next chance should be skipped.

                        if(p.getFirstRollAsOne() == 1) {

                            currentRoundScore = (firstResult);
                            p.addToTotalScore(currentRoundScore);
                            if(p.getTotalScore() - scoreToWin > 0) {

                                System.out.println("Congrats " + p.getName() + " You have won by margin of "
                                        + (p.getTotalScore() - scoreToWin) + " points. Please wait for final result.%n");
                                System.out.println(p.getName() + " scored two continue ones " +
                                        "hence wont play next round. But doesn't matter you won :) ");

                            } else {

                                System.out.println(p.getName() + " rolled " +  firstResult + " and scored "
                                        +  currentRoundScore +  " points, "
                                                + "for a total of " + p.getTotalScore() + " points.");
                                System.out.println(p.getName() + " scored two continue ones hence wont play next round.");
                            }

                            //Only place where flag of user is set as false to make him non eligible to play next round as he has hit two consecutive 1s in two turns.
                            p.setCanPlay(false);

                        } else {

                            p.setFirstRollAsOne();
                            currentRoundScore = (firstResult);
                            p.addToTotalScore(currentRoundScore);

                            if(p.getTotalScore() - scoreToWin > 0) {

                                System.out.println("Congrats " + p.getName() + " You have won by margin of "
                                        + (p.getTotalScore() - scoreToWin) + " points. Please wait for final result.");

                            } else {

                                System.out.println(p.getName() + " rolled " +  firstResult + " and scored "
                                        +  currentRoundScore +  " points, "
                                        + "for a total of " + p.getTotalScore() + " points.");

                            }

                            p.setCanPlay(true);

                        }

                    } else {

                        //For any other number on the dice.
                        currentRoundScore = (firstResult);
                        p.addToTotalScore(currentRoundScore);


                        if(p.getTotalScore() - scoreToWin > 0) {

                            System.out.println("Congrats " + p.getName() + " You have completed game by margin of "
                                    + (p.getTotalScore() - scoreToWin) + " points. Please wait for final result.");

                        } else {

                            System.out.println(p.getName() + " rolled " +  firstResult + " and scored "
                                    +  currentRoundScore +  " points, "
                                    + "for a total of " + p.getTotalScore() + " points.");

                        }

                        p.setCanPlay(true);

                    }

                } else {
                    System.out.println("Cannot determine input. Malware input coming out.");
                    break;
                }


            } else {
                p.setCanPlay(true);
            }
        }

        printWhoIsLeadingAfterEveryRound(); //prints result after every round that takes place.

        if (this.checkIfAnyoneHasWon()) { //after every round check the eligibility of the player to win situation or not.
            if(listOfPlayers.size() == 1 || listOfPlayers.size() == 0) {
                if (listOfPlayers.size() == 1) {
                    Player player = listOfPlayers.get(0);
                    System.out.println(player.getName() + " has lost the game");
                    System.out.println("This DiceGame ended");
                    finalListOfWinningOrder.add(player);

                } else {
                    System.out.println("There's probably a tie!!! as two players might have completed on same turn.");
                }

            }
        }

        //after all the computation if the criteria of the function satisfies then print the final score board.
        printFinalScore();


    }

    private void printFinalScore() {

        if(finalListOfWinningOrder.size() == totalNoOfPlayers) {
            System.out.println("******* PRINTING FINAL BOARD OF RESULT WITH FINAL PLAYERS STANDING *******");

            int rank = 1;
            for(Player player : finalListOfWinningOrder) {
                System.out.println("PLAYER NAME : " + player.getName() + " FINAL RANKING " + rank++);
            }
        }
    }

    private void printWhoIsLeadingAfterEveryRound() {

        System.out.println("****** RESULT BOARD ******");
        ArrayList<Player> players = new ArrayList<>(listOfPlayers);
        //comparing based on the number of total achieved after every round.
        players.sort(Comparator.comparingInt(Player::getTotalScore).reversed());

        int rank = 1;

        //print in form of descending order as per the total scored so far and assign ranks based on the above computation.
        for (Player p : players) {
            System.out.println("PLAYER NAME " + p.getName() + "  "
                    + "PLAYER'S SCORE " + p.getTotalScore() + "  " + "PLAYER'S RANK " + rank++);
        }

    }

    private boolean checkIfAnyoneHasWon() {

        List<Player> winners = new ArrayList<>();

        //filtering from the main list of players, those players who have scored more then threshold score.
        listOfPlayers.stream().filter((p) -> (p.getTotalScore() >= scoreToWin)).forEach(winners::add);

        winners.forEach((p) -> {

            System.out.println(p.getName() + " has reached the game limit of " + scoreToWin + " for a total "
                            + "of "+ p.getTotalScore() + " points, " + p.getName() + " has won the game!");
            //Removing the player from the list of players who are supposed to play next round and adding those players in another list which wull be used to get the final computation
            listOfPlayers.remove(p);
            finalListOfWinningOrder.add(p);

        });

        return !winners.isEmpty();

    }

    //Driving method
    public static void main(String[] args) {
        DiceGame newGame = new DiceGame();
        System.out.println("Welcome to the Dice and roll game!");
        newGame.run();

    }
}