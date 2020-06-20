import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Scanner;
public class ClientTreno {
    Socket socket;
    private String address;
    private int port;

    public ClientTreno(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public static void main(String args[]) {

        if (args.length != 2) {
            System.out.println("Usage: java ClientTreno <address> <port>");
            return;
        }
        ClientTreno client = new ClientTreno(args[0], Integer.parseInt(args[1]));
        client.start();


    }

    public void start(){
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
                System.out.println("0 - Cerca il tuo treno attraverso il codice");
                System.out.println("1 - Mostra tutti i treni");
                System.out.println("2 - QUIT");
                System.out.print("Inserisci la tua scelta---->");
                choice = user_scanner.nextInt();
                switch (choice){
                    case 0:
                        boolean show = true;




                        System.out.print("Inserisci il codice: ");
                        String code = user_scanner.next();

                        pw.println("SRCH "+ code);
                        pw.flush();
                        msg_received = server_scanner.next();
                        while(msg_received.equals("CODE_NOTOK")){
                            System.out.println("Code non trovato..");
                            System.out.print("Inserisci un nuovo codice: ");
                            code = user_scanner.next();
                            pw.println(code);
                            pw.flush();
                            msg_received = server_scanner.nextLine();
                        }

                        if (msg_received.equals("BEGIN")){





                                System.out.println("Ricevo il treno....");
                                while (show) {
                                    msg_received = server_scanner.nextLine();
                                    if (msg_received.equals("END")) {
                                        show = false;

                                    } else {
                                        // printing the trains
                                        System.out.println(msg_received);
                                    }
                                }
                            //qui devo far scegliere cosa fare col treno, se farlo partire, se indicare che è arrivato o tornare indietro
                            System.out.println("\n-------------------------------\n");
                            System.out.println("0 - Fai partire il treno");
                            System.out.println("1 - Ritorna indietro");
                            System.out.print("Inserisci la tua scelta---->");
                            choice = user_scanner.nextInt();
                            switch(choice){
                                case 0:
                                    pw.println("START");
                                    pw.flush();
                                    msg_received = server_scanner.next();
                                    int i=1;
                                    if(msg_received.equals("START_OK")){
                                        System.out.println("Il treno è partito...");





                                    }
                                    else if(msg_received.equals("NOTSTART")){
                                        System.out.println("Treno già in movimento.. Errore");
                                        break;
                                    }


                                    else System.out.println("Error: unknown response to start "+msg_received);
                                    break;
                                case 1:
                                    pw.println("RET");
                                    pw.flush();
                                    break;
                            }


                        }
                        else{
                            System.out.println("Error: unkown message");

                        }



                        break;

                    case 1:
                        pw.println("SHOW");
                        pw.flush();
                        msg_received = server_scanner.nextLine();
                        boolean showing = true;
                        if (msg_received.equals("BEGIN")) {
                            System.out.println("Receiving trains....");
                            while (showing) {
                                msg_received = server_scanner.nextLine();
                                if (msg_received.equals("END")) {
                                    showing = false;
                                    System.out.println("List of trains ended");
                                } else {
                                    // printing the trains
                                    System.out.println(msg_received);
                                }
                            }
                        }

                        else {
                            System.out.println("Unknown Response:"+msg_received);
                        }





                        break;
                    case 2:
                    msg_to_send = "QUIT";
                    pw.println(msg_to_send);
                    pw.flush();
                    msg_received = server_scanner.next();
                    if(msg_received.equals("QUIT_OK")){
                        go = false;
                        System.out.println("Quitting client...");
                        break;
                    }
                    else if(msg_received.equals("QUIT_ERROR")){
                        System.out.println("Unable to quit connection...");
                        break;

                    }


                }






            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
