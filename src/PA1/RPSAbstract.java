import java.util.Random;

// MARK: RPSAbstract
public abstract class RPSAbstract implements RPSInterface {
    protected static final int SEED = 12;
    protected final Random rand = new Random(SEED);

    // The moves allowed in this version of RPS
    protected String[] possibleMoves;

    // MARK: Game Statistics
    // The number of games, player wins, CPU wins and ties
    protected int numGames;
    protected int numPlayerWins;
    protected int numCPUWins;
    protected int numTies;

    protected int numPossibleMoves;

    // The complete history of the moves
    protected String[] playerMoves;
    protected String[] cpuMoves;

    // The default moves. Use for the basic implementation of the game.
    protected static final String[] DEFAULT_MOVES = { "rock", "paper",
            "scissors" };

    /*
     * Important: Use the following constants to avoid output matching issues
     * and magic numbers!
     */

    // MARK: Game Messages / Outcomes
    // Messages for the game.
    protected static final String INVALID_INPUT = "That is not a valid move. Please try again.";
    protected static final String PLAYER_WIN = "You win.";
    protected static final String CPU_WIN = "I win.";
    protected static final String TIE = "It's a tie.";
    protected static final String CPU_MOVE = "I chose %s. ";
    protected static final String THANKS = "Thanks for playing!\nOur most recent games were:";
    protected static final String PROMPT_MOVE = "Let's play! What's your move? (Type the move or q to quit)";
    protected static final String Max_GAMES_MSG = "Maximum Number of Games Reached";

    protected static final String OVERALL_STATS = "Our overall stats are:\n" +
            "I won: %.2f%%\nYou won: %.2f%%\nWe tied: %.2f%%\n";
    protected static final String CPU_PLAYER_MOVES = "Me: %s, You: %s\n";

    // Game outcome constants.
    protected static final int CPU_WIN_OUTCOME = 2;
    protected static final int PLAYER_WIN_OUTCOME = 1;
    protected static final int TIE_OUTCOME = 0;
    protected static final int INVALID_INPUT_OUTCOME = -1;

    // Other game control constants.
    protected static final int MAX_GAMES = 100;
    protected static final int MIN_POSSIBLE_MOVES = 3;
    protected static final int NUM_ROUNDS_TO_DISPLAY = 10;
    protected static final int PERCENTAGE_RESIZE = 100;
    protected static final String QUIT = "q";

    // MARK: isValidMove
    @Override
    public boolean isValidMove(String move) {
        for (int i = 0; i < possibleMoves.length; i++) {
            if (possibleMoves[i].equals(move)) {
                return true;
            }
        }
        return false;
    }

    // MARK: playRPS
    @Override
    public void playRPS(String playerMove, String cpuMove) {
        if (!isValidMove(cpuMove) || !isValidMove(playerMove)) {
            System.out.println(INVALID_INPUT);
            return;
        }

        int result = this.determineWinner(playerMove, cpuMove);
        if (result == INVALID_INPUT_OUTCOME) {
            return;
        }

        this.playerMoves[numGames] = playerMove;
        this.cpuMoves[numGames] = cpuMove;
        this.numGames += 1;

        System.out.printf(CPU_MOVE, cpuMove);

        if (result == PLAYER_WIN_OUTCOME) {
            System.out.println(PLAYER_WIN);
            this.numPlayerWins += 1;
        }

        if (result == CPU_WIN_OUTCOME) {
            System.out.println(CPU_WIN);
            this.numCPUWins += 1;
        }

        if (result == TIE_OUTCOME) {
            System.out.println(TIE);
            this.numTies += 1;
        }
    }

    // MARK: genCPUMove
    /**
     * Generates next cpu move
     *
     * @return representing the move, depending on random int
     */
    @Override
    public String genCPUMove() {
        // Generate new random number
        int num = rand.nextInt(possibleMoves.length);
        // Get move based on random number
        return possibleMoves[num];
    }

    // MARK: displayStats
    /**
     * Print out the end game stats: moves played and win percentages
     */
    @Override
    public void displayStats() {
        float cpuWinPercent = (float) numCPUWins /
                (float) numGames * PERCENTAGE_RESIZE;
        float playerWinPercent = (float) numPlayerWins /
                (float) numGames * PERCENTAGE_RESIZE;
        float tiedPercent = (float) numTies /
                (float) numGames * PERCENTAGE_RESIZE;

        System.out.println(THANKS);

        // Get which index to print to
        int printTo = (numGames < NUM_ROUNDS_TO_DISPLAY)
                ? 0
                : numGames - NUM_ROUNDS_TO_DISPLAY;

        // Print system and user moves
        for (int i = numGames - 1; i >= printTo; i--) {
            System.out.printf(CPU_PLAYER_MOVES, this.cpuMoves[i],
                    this.playerMoves[i]);
        }

        System.out.printf(OVERALL_STATS, cpuWinPercent, playerWinPercent,
                tiedPercent);
    }
}
