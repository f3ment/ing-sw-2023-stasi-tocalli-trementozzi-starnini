import distributed.Server;
import distributed.rmi.ClientImpl;
import distributed.rmi.ServerImpl;
import view.TextualUI;
import utils.*;
import model.*;
import controller.*;

import java.rmi.RemoteException;


public class App {
    public static void main( String[] args ) {

        System.out.print(Color.BLACK_BACKGROUND);
        System.out.print(Color.RED_BOLD);
        System.out.println(".___  ___. ____    ____         _______. __    __   _______  __       _______  __   _______ "+"\033[0m");
        System.out.print(Color.RESET);
        System.out.print(Color.BLACK_BACKGROUND);
        System.out.print(Color.YELLOW_BOLD);
        System.out.println("|   \\/   | \\   \\  /   /        /       ||  |  |  | |   ____||  |     |   ____||  | |   ____|"+"\033[0m");
        System.out.print(Color.RESET);
        System.out.print(Color.BLACK_BACKGROUND);
        System.out.print(Color.GREEN_BOLD);
        System.out.println("|  \\  /  |  \\   \\/   /        |   (----`|  |__|  | |  |__   |  |     |  |__   |  | |  |__   "+"\033[0m");
        System.out.print(Color.RESET);
        System.out.print(Color.BLACK_BACKGROUND);
        System.out.print(Color.BLUE_BOLD);
        System.out.println("|  |\\/|  |   \\_    _/          \\   \\    |   __   | |   __|  |  |     |   __|  |  | |   __|  "+"\033[0m");
        System.out.print(Color.RESET);
        System.out.print(Color.BLACK_BACKGROUND);
        System.out.print(Color.MAGENTA_BOLD);
        System.out.println("|  |  |  |     |  |        .----)   |   |  |  |  | |  |____ |  `----.|  |     |  | |  |____ "+"\033[0m");
        System.out.print(Color.RESET);
        System.out.print(Color.BLACK_BACKGROUND);
        System.out.print(Color.CYAN_BOLD);
        System.out.println("|__|  |__|     |__|        |_______/    |__|  |__| |_______||_______||__|     |__| |_______|"+"\033[0m");
        System.out.print(Color.RESET);

        System.out.println("\033[40m" + "                                                                                            " + "\33[0m");
        System.out.println("\033[40m\033[1;31m\033[4;37m" + " Cr" + "\033[1;33m" + "ea" + "\033[1;32m" + "te" + "\033[1;34m" + "d B" + "\033[1;35m" + "y: " + "\033[40m" + "                                                                               " +"\033[0m");
        System.out.println("\033[40m"+ " " + "\033[0;30m\033[41m" + "- Michelangelo Stasi (michelangelo.stasi@mail.polimi.it)                                   " + "\033[40m"+ " " + "\33[0m");
        System.out.println("\033[40m"+ " " + "\033[0;30m\033[42m" + "- Nicolo' Tocalli (nicolo.tocalli@mail.polimi.it)                                          " + "\033[40m"+ " " + "\33[0m");
        System.out.println("\033[40m"+ " " + "\033[0;30m\033[43m" + "- Francesco Trementozzi (francesco.trementozzi@mail.polimi.it)                             " + "\033[40m"+ " " + "\33[0m");
        System.out.println("\033[40m"+ " " + "\033[0;30m\033[44m" + "- Giuseppe Starnini (giuseppe.starnini@mail.polimi.it)                                     " + "\033[40m"+ " " + "\33[0m");
        System.out.println("\033[40m" + "                                                                                             " + "\33[0m");
        System.out.println("\033[0;107m" + "                                                                                             " + "\33[0m");
        System.out.println("\033[40m" + "                                                                                             " + "\33[0m");

        System.out.print("Start in 3..");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.print("2..");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.print("1..");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for(int i=0; i<50; i++) System.out.println();
        /*
        System.out.println("Press \"ENTER\" to continue...");
        try {
            System.in.read();
            for(int i=0; i<50; i++) System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }*/




        Game model;
        ArrayList<String> names= new ArrayList<String>();
        names.add("Piero");
        names.add("Giovanni");
        names.add("Luca");
        //names.add("Giacomo");
        try {
            model = new Game(names);
        } catch (IOException e) {
            System.out.print(Color.RED_BOLD);
            System.out.print(Color.YELLOW_BACKGROUND);
            System.err.println("Error while creating new match!");
            System.out.print(Color.RESET);
            return;
        }

        Server server = new ServerImpl();
        ClientImpl client = new ClientImpl(server);
        client.run();
    }
}
