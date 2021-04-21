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

        // general
        int dirCount = scanner.nextInt();
        scanner.nextLine();
        //System.err.println("dirCount = " + dirCount);
        String dirsInfos = scanner.nextLine();
        //System.err.println("dirsInfos = " + dirsInfos);

        // trafficLights
        int lightCount = scanner.nextInt();
        scanner.nextLine();
        //System.err.println("lightCount = " + lightCount);
        for (int i = 0; i < lightCount; i++) {
            String lightInfos = scanner.nextLine();
            //System.err.println("lightsInfos = " + lightInfos);
        }


        //fifos
        int fifoCount = scanner.nextInt();
        scanner.nextLine();
        //System.err.println("fifoCount = " + fifoCount);
        for (int i = 0; i < fifoCount; i++) {
            String fifoInfos = scanner.nextLine();
            //System.err.println("fifoInfos = " + fifoInfos);
        }

        int turn = 0;

        while (true) {

            // score in loop

            // trafficLights in loop

            // fifos in loop


            if (turn == 4){
                System.out.println("H_GREEN");
            }

            else if(turn == 12){
                System.out.println("V_GREEN");
            }

            else if(turn == 20){
                System.out.println("N_RED");
            }
            else if(turn == 28){
                System.out.println("H_GREEN");
            }
            else if(turn == 36){
                System.out.println("W_RED");
            }

            else if(turn == 42){
                System.out.println("V_GREEN");
            }
            else if(turn == 50){
                System.out.println("H_GREEN");
            }

            else {
                System.out.println("WAIT");
            }
            /*
            System.out.println("WAIT");
            */
            turn ++;
        }
    }

}
