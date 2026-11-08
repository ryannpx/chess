package client;

import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        var port = 8080;
        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
        }

        var client = new ChessClient(port);
        System.out.println("yay chess client");
        System.out.print(client.eval("help"));

        try (var scanner = new Scanner(System.in)) {
            var result = "";
            while (!result.equals("quit")) {
                System.out.print("\n[" + client.getState() + "] >>> ");
                String line = scanner.nextLine();
                result = client.eval(line);
                System.out.print(result);
            }
        }
    }
}