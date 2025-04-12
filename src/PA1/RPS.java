
/* 
  Name: Brian J. Masse
  Email: bmasse@ucsd.edu
  PID: A17991084
  Sources Used: Java Interface Documentation, PA1 Write-up
   
  This file is for CSE 12 PA1 in Spring 2025,
*/
import java.util.Scanner;

// MARK: RPS

/**
 * This class handles the primary logic of the RPS game
 * Including the main gameplay loop, handingle wins / losses
 * 
 * possibleMoves - The different moves that a player can play
 * numGames — Number of games played
 * numPlayerWins - Number of wins for the player
 * numCPUWins - Number of wins for the CPU
 * numTies - Number of ties
 * 
 * playerMoves - a history of all the player's moves
 * cpuMoves - a history of all the CPU's moves
 */
public class RPS extends RPSAbstract {

    /**
     * Construct a new instance of RPS with the given possible moves.
     * 
     * @param moves all possible moves in the game.
     */
    public RPS(String[] moves) {
        this.possibleMoves = moves;
        this.playerMoves = new String[MAX_GAMES];
        this.cpuMoves = new String[MAX_GAMES];
    }

    // MARK: Main
    /**
     * The programs main function
     * Contains the logic / gameplay loop for RPS
     */
    public static void main(String[] args) {
        // If command line args are provided use those as the possible moves
        String[] moves = new String[args.length];
        if (args.length >= MIN_POSSIBLE_MOVES) {
            System.arraycopy(args, 0, moves, 0, args.length);
        } else {
            moves = RPS.DEFAULT_MOVES;
        }

        // instantitate a new RPS game & scanner
        RPS game = new RPS(moves);
        Scanner in = new Scanner(System.in);

        String player_input = "";

        // begin the main gameplay loop
        while (!player_input.equals(ESCAPE_CHAR)) {

            System.out.println(PROMPT_MOVE);

            // take in the player's input & generate a CPU move
            player_input = in.nextLine();
            String cpu_input = game.genCPUMove();

            // determine if a game can / should be played
            if (game.numGames >= MAX_GAMES) {
                System.out.println(Max_GAMES_MSG);
                break;
            }

            if (player_input.equals(ESCAPE_CHAR)) {
                break;
            }

            game.playRPS(player_input, cpu_input);
        }

        game.displayStats();

        in.close();
    }

    // MARK: determineWinner
    /**
     * Determines which player won given their two inputs
     * 
     * @param playerMove the players move
     * @param cpuMove    the CPUs move
     * @return -1 for invalid inputs, 2 for a player win, 1 for a CPU win, 0 for a
     *         tie
     */
    @Override
    public int determineWinner(String playerMove, String cpuMove) {
        // check for input validitiy
        if (!isValidMove(playerMove) || !isValidMove(cpuMove)) {
            return INVALID_INPUT_OUTCOME;
        }

        // find the player's move in the possibleMove array
        int numPossibleMoves = this.possibleMoves.length;
        int indexOfPlayerMove = 0;

        for (int i = 0; i < numPossibleMoves; i++) {
            if (this.possibleMoves[i].equals(playerMove)) {
                indexOfPlayerMove = i;
                break;
            }
        }

        // get the index of the next / previous elements in the list
        int indexOfNextMove = (indexOfPlayerMove + 1) % numPossibleMoves;
        int indexOfPreviousMove = (indexOfPlayerMove - 1 + numPossibleMoves) % numPossibleMoves;

        // determine winners
        if (this.possibleMoves[indexOfNextMove].equals(cpuMove)) {
            return CPU_WIN_OUTCOME;
        }

        if (this.possibleMoves[indexOfPreviousMove].equals(cpuMove)) {
            return PLAYER_WIN_OUTCOME;
        }

        return TIE_OUTCOME;
    }
}
