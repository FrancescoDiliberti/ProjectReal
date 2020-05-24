import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class ClientAmministrazione {
    Socket socket;
    private String address;
    private int port;

    public static void main(String args[]) {

        if (args.length != 2) {
            System.out.println("Usage: java MyClient <address> <port>");
            return;
        }

        ClientAmministrazione client = new ClientAmministrazione(args[0], Integer.parseInt(args[1]));
        client.start();
    }

    public ClientAmministrazione(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void start() {
        System.out.println("Starting Client connection to " + address + ":" + port);

        try {
            socket = new Socket(address, port);
            System.out.println("Started Client connection to " + address + ":" + port);

            // to server
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            // from server
            Scanner server_scanner = new Scanner(socket.getInputStream());
            // from user
            Scanner user_scanner = new Scanner(System.in);

            String msg_to_send;
            String msg_received;

            boolean go = true;
            int choice;

            while (go) {

                System.out.println("-------------------------------");
                System.out.println("0 - Add train");
                System.out.println("1 - Remove train");
                System.out.println("2 - List all trains");
                System.out.println("3 - Save trains");
                System.out.println("4 - Add a conductor");
                System.out.println("5 - Remove conductor");
                System.out.println("6 - Show all conductors");
                System.out.println("7 - Save conductors");
                System.out.println("8 - Quit");
                System.out.println("-------------------------------");
                System.out.print("enter choice---->");
                choice = user_scanner.nextInt();

                switch (choice) {
                    case 0:
                        // add train
                        System.out.print("Insert train code(must be 7 digits) :");
                        String code = user_scanner.next();
                        if (code.length() > 7) {
                            System.out.println("-----WRONG CODE TYPE-----");
                            break;
                        }
                        boolean error = false;
                        boolean error_2 = false;


                        System.out.print("Insert seating capacity:");
                        int seats = user_scanner.nextInt();
                        System.out.print("Insert ticket prize: ");
                        long prize = user_scanner.nextLong();
                        System.out.print("Insert departure station:");
                        String departure = user_scanner.next();
                        msg_to_send = "ADDTR " + code + " " + seats + " " + prize + " " + departure;
                        pw.println(msg_to_send);
                        pw.flush();

                        do {
                            System.out.println("Has this train indermediate stops?(Y/N)");
                            String s = user_scanner.next();
                            if (s.equals("Y")) {
                                error = false;
                                boolean hasStops = true;
                                while (hasStops) {
                                    System.out.println("Insert stop: ");
                                    String stop = user_scanner.next();
                                    System.out.println("DEBUG: Sending " + stop);
                                    pw.println(stop);
                                    pw.flush();
                                    System.out.println("Does the train have other intermediate stops(Y/N)? :");
                                    String resp = user_scanner.next();
                                    if (resp.equals("N")) {
                                        hasStops = false;
                                        System.out.println("Insert arrive :");
                                        String arrive = user_scanner.next();
                                        System.out.println("DEBUG: Sending " + arrive);
                                        pw.println(arrive);
                                        pw.flush();
                                        System.out.println("DEBUG: Sending ENDOFSTOPS");
                                        pw.println("ENDOFSTOPS");
                                        pw.flush();
                                    }

                                }


                            } else if (s.equals("N")) {
                                error = false;
                                //prendere l'arrivo
                                System.out.println("Insert arrive :");
                                String arrive = user_scanner.next();
                                System.out.println("DEBUG: Sending " + arrive);
                                pw.println(arrive);
                                pw.flush();
                                System.out.println("DEBUG: Sending ENDOFSTOPS");
                                pw.println("ENDOFSTOPS");
                                pw.flush();


                            } else {
                                System.out.println("Enter a valid response; Y/N");
                                error = true;

                            }
                        } while (error);


                        System.out.println("Insert departure time(hh:mm) : ");
                        String dep_time = user_scanner.next();
                        System.out.println("Insert arrive time(hh:mm) : ");
                        String arr_time = user_scanner.next();
                        msg_to_send = dep_time + " " + arr_time;
                        System.out.println("DEBUG: Sending " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        do {
                            System.out.println("Does this train leave every day? (Y/N) :");
                            String r = user_scanner.next();
                            if (r.equals("Y")) {
                                error_2 = false;
                                int days = 7;
                                System.out.println("DEBUG: Sending " + days);
                                pw.println(days);
                                pw.flush();
                                msg_to_send = "END_OFMSG";
                                System.out.println("DEBUG: Sending " + msg_to_send);
                                pw.println(msg_to_send);
                                pw.flush();



                            } else if (r.equals("N")) {
                                error = false;
                                //prendo i giorni in cui viaggia
                                System.out.println("How many days a week? ");
                                int days = user_scanner.nextInt();
                                System.out.println("DEBUG: Sending " + days);
                                pw.println(days);
                                pw.flush();
                                for (int i = 1; i <= days; i++) {

                                    System.out.println("Insert which day :");
                                    String day = user_scanner.next();
                                    System.out.println("DEBUG: Sending " + day);
                                    pw.println(day);
                                    pw.flush();

                                }
                                msg_to_send = "END_OFMSG";
                                System.out.println("DEBUG: Sending " + msg_to_send);
                                pw.println(msg_to_send);
                                pw.flush();


                            } else {
                                System.out.println("Enter a valid response; Y/N");
                                error_2 = true;

                            }
                        } while (error_2);


                        msg_received = server_scanner.nextLine();
                        if (msg_received.equals("ADDTR_OK")) {
                            System.out.println("Train added correctly!");
                        } else if (msg_received.equals("ADDTR_ERROR")) {
                            System.out.println("Error adding Train!");
                        } else {
                            System.out.println("ERROR: unkown message->" + msg_received);

                        }
                        break;
                    case 1: // remove
                        break;
                    case 2: // list
                        msg_to_send = "LIST";
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        boolean listing = true;
                        if (msg_received.equals("BEGIN")) {
                            System.out.println("Receiving list....");
                            while (listing) {
                                msg_received = server_scanner.nextLine();
                                if (msg_received.equals("END")) {
                                    listing = false;
                                    System.out.println("List ended");
                                } else {
                                    // printing the person
                                    System.out.println(msg_received);
                                }
                            }
                        } else {
                            System.out.println("Unknown Response:" + msg_received);
                        }
                        break;
                    case 3: // save
                        pw.println("SAVE");
                        pw.flush();

                        msg_received = server_scanner.nextLine();
                        if (msg_received.equals("SAVE_OK")) {
                            System.out.println("File save correctly");
                        } else if (msg_received.equals("SAVE_ERROR")) {
                            System.out.println("Error saving file");
                        } else {
                            System.out.println("Unknown message: " + msg_received);
                        }

                        break;
                    case 4: // quit
                        go = false;
                        System.out.println("Quitting client...");
                        msg_to_send = "QUIT";
                        pw.println(msg_to_send);
                        pw.flush();
                        break;

                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
