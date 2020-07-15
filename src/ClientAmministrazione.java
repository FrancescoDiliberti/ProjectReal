import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Scanner;

public class ClientAmministrazione {
    Socket socket;
    private String address;
    private int port;

    public static void main(String args[]) {

        if (args.length != 2) {
            System.out.println("Usage: java ClientAmministrazione <address> <port>");
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
                System.out.println("0 - Aggiungi un treno");
                System.out.println("1 - Elimina un treno");
                System.out.println("2 - Mostra tutti i treni");
                System.out.println("3 - Salva i treni");
                System.out.println("4 - Esci");
                System.out.println("-------------------------------");
                System.out.print("Digita la tua scelta---->");
                choice = user_scanner.nextInt();
                long time;

                switch (choice) {
                    case 0:
                        // add train
                        System.out.print("Inserisci il codice del treno: ");
                        String code = user_scanner.next();
                        boolean error = false;



                        System.out.print("Inserisci i posti disponibili sul treno: ");
                        int seats = user_scanner.nextInt();
                        System.out.print("Inserisci il prezzo del biglietto: ");
                        String prize = user_scanner.next();
                        System.out.print("Inserisci la stazione di partenza: ");
                        String departure = user_scanner.next();
                        msg_to_send = "ADDTR " + code + " " + seats + " " + prize + " " + departure;
                        pw.println(msg_to_send);
                        pw.flush();
                        System.out.print("Inserisci la stazione di arrivo: ");
                        String arrive = user_scanner.next();
                        boolean error_2 = false;

                        do {
                            System.out.print("Il treno fa fermate intermedie?(Y/N): ");
                            String s = user_scanner.next();
                            if (s.equals("Y")) {
                                error = false;
                                boolean hasStops = true;
                                int i= 1;

                                while (hasStops) {
                                    System.out.print("Inserisci la fermata "+i+": ");
                                    String stop = user_scanner.next();
                                    System.out.print("Quanti minuti ci mette da "+departure.toUpperCase()+ " a "+stop.toUpperCase() +"?");
                                    time= user_scanner.nextLong();
                                    System.out.println("DEBUG: Sending " + stop);
                                    pw.println(stop+ " "+ time);
                                    i++;
                                    pw.flush();
                                    do {
                                        System.out.print("Il treno fa altre fermate intermedie?(Y/N):");
                                        String resp = user_scanner.next();

                                        if (resp.equals("N")) {
                                            hasStops = false;
                                            error_2=false;
                                            System.out.print("Quanti minuti ci mette da "+stop.toUpperCase()+" a "+arrive.toUpperCase() +"?");
                                            time = user_scanner.nextLong();
                                            System.out.println("DEBUG: Sending " + arrive + " "+time);
                                            pw.println(arrive+ " "+time);
                                            pw.flush();
                                            System.out.println("DEBUG: Sending ENDOFSTOPS");
                                            pw.println("ENDOFSTOPS");
                                            pw.flush();
                                        }
                                        else if(resp.equals("Y")){
                                            hasStops= true;
                                            error_2= false;
                                            departure = stop;
                                        }
                                        else{
                                            System.out.println("Inserisci una risposta valida (Y/N)");
                                            error_2=true;
                                        }
                                    }while(error_2);


                                }


                            } else if (s.equals("N")) {
                                error = false;

                                System.out.print("Quanti minuti ci mette per arrivare a "+arrive.toUpperCase() +"?");
                                time = user_scanner.nextLong();
                                System.out.println("DEBUG: Sending " + arrive);
                                pw.println(arrive+" "+time);
                                pw.flush();
                                System.out.println("DEBUG: Sending ENDOFSTOPS");
                                pw.println("ENDOFSTOPS");
                                pw.flush();


                            } else {
                                System.out.println("Inserisci una risposta valida: Y/N");
                                error = true;

                            }
                        } while (error);


                        System.out.print("Inserisci orario di partenza(hh:mm): ");
                        String dep_time = user_scanner.next();

                        System.out.println("DEBUG: Sending " + dep_time);
                        pw.println(dep_time);
                        pw.flush();
                        System.out.print("Inserisci orario di arrivo(hh:mm): ");
                        String arr_time = user_scanner.next();



                        while(LocalTime.parse(arr_time).compareTo(LocalTime.parse(dep_time))<0){
                            System.out.println("L'orario di arrivo è errato, inseriscine un'altro: ");
                            arr_time= user_scanner.next();
                        }

                        System.out.println("DEBUG: Sending " + arr_time);
                        pw.println(arr_time);
                        pw.flush();


                            System.out.print("Quanti giorni alla settimana il treno parte? ");
                            int days = user_scanner.nextInt();
                            while(days>7 || days== 0){
                                System.out.print("Inserisci un numero corretto di giorni :");
                                days = user_scanner.nextInt();
                            }
                            if(days==7) {
                                pw.println("EVERY");
                                pw.flush();
                                System.out.println("DEBUG: Sending EVERY");

                            }
                            else {
                                for (int i = 1; i <= days; i++) {
                                    System.out.println("Inserisci giorno "+i+": ");
                                    String day = user_scanner.next();
                                    System.out.println("DEBUG: Sending " + day+i);
                                    pw.println(day);
                                    pw.flush();

                            }
                                msg_to_send = "END_OFMSG";
                                System.out.println("DEBUG: Sending " + msg_to_send);
                                pw.println(msg_to_send);
                                pw.flush();




                            }














                        msg_received = server_scanner.nextLine();


                        if(msg_received.equals("CODE_NOTOK")) {
                            do {
                                System.out.print("Il codice del treno è già esistente, Inseriscine un altro :");
                                String new_code = user_scanner.next();
                                System.out.println("DEBUG: Sending " + new_code);
                                pw.println(new_code);
                                pw.flush();
                            } while (server_scanner.nextLine().equals("CODE_NOTOK"));
                        }
                        else if(msg_received.equals("CODE_OK")){
                                System.out.println("Code OK");
                            System.out.println("Treno aggiunto!");
                            }


                        else {
                            System.out.println("ERROR: unkown message->" + msg_received);

                        }
                        break;
                    case 1: // remove
                        System.out.println("Inserisci il codice del treno da eliminare: ");
                        String trainCode = user_scanner.next();
                        msg_to_send = "REM " + trainCode;
                        pw.println(msg_to_send);
                        pw.flush();
                        System.out.println("DEBUG: Sending "+msg_to_send);
                        msg_received= server_scanner.nextLine();
                        if(msg_received.equals("REM_OK")){
                            System.out.println("Treno eliminato correttamente!");
                        }
                        else if(msg_received.equals("REM_ERR")){
                            System.out.println("Treno non trovato, ripeti la procedura");
                        }


                        break;
                    case 2: // Show
                        msg_to_send = "SHOW";
                        pw.println(msg_to_send);
                        pw.flush();
                        msg_received = server_scanner.nextLine();
                        boolean showing = true;
                        if (msg_received.equals("BEGIN")) {
                            System.out.println("Ricevo i treni....");
                            while (showing) {
                                msg_received = server_scanner.nextLine();
                                if (msg_received.equals("END")) {
                                    showing = false;
                                    System.out.println("Lista dei treni finita");
                                } else {
                                    // printing the person
                                    System.out.println(msg_received);
                                }
                            }
                        }

                        else {
                            System.out.println("Unknown Response:"+msg_received);
                        }





                        break;
                    case 3: // save
                        pw.println("SAVE");
                        pw.flush();
                        msg_received = server_scanner.nextLine();
                        if (msg_received.equals("SAVE_OK")) {
                            System.out.println("File salvato correttamente");
                        } else if (msg_received.equals("SAVE_ERROR")) {
                            System.out.println("Errore nel salvare il file");
                        }

                    else {
                            System.out.println("Unknown message: " + msg_received);
                        }

                        break;
                    case 4: // quit
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

                        else {
                            System.out.println("Unknown message: " + msg_received);
                        }
                        break;




                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
