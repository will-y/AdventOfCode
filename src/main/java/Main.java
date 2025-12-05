import util.GenAssetsKt;
import util.PuzzleRunner;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0) {
            GenAssetsKt.genAssets(args);
            return;
        }
        PuzzleRunner runner = new PuzzleRunner();

        runner.runPuzzle(25, 5, false, true);
    }
}
