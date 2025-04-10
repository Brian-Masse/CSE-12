import java.util.Scanner;

// MARK: RPS
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
        this.numPossibleMoves = moves.length;
    }

    // MARK: Main
    public static void main(String[] args) {
        // If command line args are provided use those as the possible moves
        String[] moves = new String[args.length];
        if (args.length >= MIN_POSSIBLE_MOVES) {
            System.arraycopy(args, 0, moves, 0, args.length);
        } else {
            moves = RPS.DEFAULT_MOVES;
        }

        RPS game = new RPS(moves);
        Scanner in = new Scanner(System.in);

        String player_input = "";

        while (!player_input.equals("q")) {

            System.out.println(PROMPT_MOVE);

            player_input = in.nextLine();
            String cpu_input = game.genCPUMove();

            if (!game.checkCanPlayGame(player_input)) {
                break;
            }
            game.playRPS(player_input, cpu_input);
        }

        game.displayStats();

        in.close();
    }

    public boolean checkCanPlayGame(String player_input) {
        if (numGames >= MAX_GAMES) {
            System.out.println(Max_GAMES_MSG);
            return false;
        }

        return !player_input.equals("q");
    }

    // MARK: determineWinner
    @Override
    public int determineWinner(String playerMove, String cpuMove) {
        if (!isValidMove(playerMove) || !isValidMove(cpuMove)) {
            return INVALID_INPUT_OUTCOME;
        }

        int indexOfPlayerMove = 0;
        // find the player's move in the possibleMove array
        for (int i = 0; i < this.numPossibleMoves; i++) {
            if (this.possibleMoves[i].equals(playerMove)) {
                indexOfPlayerMove = i;
                break;
            }
        }

        int indexOfNextMove = (indexOfPlayerMove + 1) % this.numPossibleMoves;
        int indexOfPreviousMove = (indexOfPlayerMove - 1 + this.numPossibleMoves) % this.numPossibleMoves;

        if (this.possibleMoves[indexOfNextMove].equals(cpuMove)) {
            return CPU_WIN_OUTCOME;
        }

        if (this.possibleMoves[indexOfPreviousMove].equals(cpuMove)) {
            return PLAYER_WIN_OUTCOME;
        }

        return TIE_OUTCOME;
    }
}
