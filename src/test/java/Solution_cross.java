import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * A basic example AI for the game.
 * Goes to the closest reachable eggs.
 */

public class Solution_cross {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int stackCount = scanner.nextInt();
        scanner.nextLine();
        System.err.println("stackCount = " + stackCount);
        for (int i = 0; i < stackCount; i++) {
            String  stackInfos = scanner.nextLine();
            System.err.println("stackInfos = " + stackInfos);
            int initialStackCount = Integer.parseInt(stackInfos.split(" ")[0]);
            System.err.println(initialStackCount);
            for (int j = 0; j < initialStackCount; j++) {
            }
        }

        int turn = 0;

        while (true) {

            if (turn == 5){
                System.out.println("H_GREEN");
            }

            else if(turn == 15){
                System.out.println("V_GREEN");
            }

            else if(turn == 22){
                System.out.println("H_GREEN");
            }

            else {
                System.out.println("WAIT");
            }

            turn ++;
        }
    }

}
