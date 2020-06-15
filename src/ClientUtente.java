import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientUtente {
    Socket socket;
    private String address;
    private int port;

    public ClientUtente(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public static void main(String args[]) {

        if (args.length != 2) {
            System.out.println("Usage: java ClientTreno <address> <port>");
            return;
        }
        ClientUtente client = new ClientUtente(args[0], Integer.parseInt(args[1]));
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
                System.out.println("\n\nBENVENUTO SU SICILTRAIN\n\n");
                System.out.println("0 - Ricerca le informazioni su un treno attraverso il suo codice");
                System.out.println("1 - Ricerca un treno");
                System.out.println("2 - QUIT");
                System.out.print("Inserisci la tua scelta---->");
                choice = user_scanner.nextInt();
                switch (choice){
                    case 0:
                        boolean showing = true;
                        System.out.println("Ecco l'elenco dei codici di tutti i treni");
                        pw.println("SHOWCODES");
                        pw.flush();
                        msg_received = server_scanner.nextLine();
                        if (msg_received.equals("BEGIN")) {
                            System.out.println("Ricevo il treno...");
                            while (showing) {
                                msg_received = server_scanner.nextLine();
                                if (msg_received.equals("END")) {
                                    showing = false;

                                } else {
                                    // printing codes
                                    System.out.println(msg_received);
                                }
                            }
                        }

                        System.out.print("\n\nInserisci il codice: ");
                        String code = user_scanner.next();

                        pw.println("SRC1 "+ code);
                        pw.flush();
                        msg_received = server_scanner.nextLine();
                        while(msg_received.equals("CODE_NOTOK")){
                            System.out.println("Codice non trovato..");
                            System.out.println("1 -Inserisci un nuovo codice: ");
                            System.out.println("2 - Ritorna indietro");
                            System.out.print("Inserisci la tua scelta---->");
                            choice = user_scanner.nextInt();
                            switch(choice){
                                case 1:
                                    System.out.println("Inserisci il codice: ");
                                    code = user_scanner.next();
                                    pw.println(code);
                                    pw.flush();
                                    msg_received = server_scanner.nextLine();
                                    break;

                                case 2:
                                    msg_received= "BACK";
                                    break;
                            }


                        }
                        if(msg_received.equals("BACK")){
                            pw.println("BACK");
                            pw.flush();
                            break;
                        }

                        showing = true;
                        if (msg_received.equals("BEGIN")) {
                            System.out.println("Ricevo il treno...");
                            while (showing) {
                                msg_received = server_scanner.nextLine();
                                if (msg_received.equals("END")) {
                                    showing = false;

                                } else {
                                    // printing the train
                                    System.out.println(msg_received);
                                }
                            }
                        }

                        else {
                            System.out.println("Unknown Response:"+msg_received);
                        }






                        break;

                    case 1:
                        //ricerca tramite da.. a e giorno
                        System.out.print("Inserisci città di partenza: ");
                        String dep = user_scanner.next();
                        System.out.print("Inserisci città di arrivo: ");
                        String arr= user_scanner.next();
                        System.out.print("In quale giorno della settimana?");
                        String day = user_scanner.next();
                        System.out.println("Inserisci un orario(hh:mm): ");
                        String time = user_scanner.next();

                        msg_to_send = "SRC2 " + dep + " "+arr+ " "+day+ " "+time;
                        pw.println(msg_to_send);
                        pw.flush();
                        System.out.println("Ricerca in corso di treni che partono da "+dep+ " in arrivo a "+arr+" dalle "+time+ " in poi di "+day);
                        showing = true;
                        msg_received = server_scanner.nextLine();
                        if (msg_received.equals("BEGIN")) {
                            System.out.println("Ricevi i treni...");
                            while (showing) {
                                msg_received = server_scanner.nextLine();
                                if (msg_received.equals("ENDOFTRAINS")) {
                                    showing = false;

                                } else {
                                    // printing the train
                                    System.out.println(msg_received);
                                }
                            }
                        }

                        else if(msg_received.equals("ENDOFTRAINS")){
                            System.out.println("Nessun Treno trovato..");
                        }
                        else{
                            System.out.println("Unkown response: "+msg_received);
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
