import com.codingame.gameengine.runner.SoloGameRunner;

public class Main {
    public static void main(String[] args) {
        SoloGameRunner gameRunner = new SoloGameRunner();

        // Sets the player
        gameRunner.setAgent(Solution_cross.class);

        // Sets a test case
        gameRunner.setTestCase("test01.json");

        gameRunner.start();
    }
}
