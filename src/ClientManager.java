import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.util.*;

public class ClientManager implements Runnable{

    private Socket client_socket;
    private TrainMap trainmap;


    public ClientManager(Socket myclient, TrainMap map) {
        client_socket = myclient;
        this.trainmap = map;


    }

    @Override
    public void run() {
        String tid = Thread.currentThread().getName();

        System.out.println(tid+"-> Accepted connection from " + client_socket.getRemoteSocketAddress());

        PrintWriter pw = null;
        Scanner client_scanner = null;
        HashMap<String,Treno> mapCopy = new HashMap<>();
        HashMap<String,Treno> startCopy = new HashMap<>();

        try {
            client_scanner = new Scanner(client_socket.getInputStream());
            pw = new PrintWriter(client_socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean go = true;
        while (go) {

            String message = client_scanner.nextLine();
            System.out.println("Server Received: "+message);
            Scanner msg_scanner = new Scanner(message);
            String cmd = msg_scanner.next();
            if(cmd.equals("ADDTR")){
                String code = msg_scanner.next();
                int seats = msg_scanner.nextInt();
                String prize = msg_scanner.next();
                LinkedList<String> stops= new LinkedList<>();
                ArrayList<Long> times = new ArrayList<>();
                String departure = msg_scanner.next();
                stops.add(departure.toUpperCase());

                String next_s = client_scanner.nextLine();

                Scanner scan = new Scanner(next_s);
                System.out.println("Server Received: "+next_s);
                stops.add(scan.next().toUpperCase());
                times.add(scan.nextLong());
                message = client_scanner.nextLine();
                System.out.println("Server Received: "+message);
                while(!message.equals("ENDOFSTOPS")){
                    scan = new Scanner(message);

                    next_s = scan.next();
                    long time = scan.nextLong();

                    times.add(time);
                    stops.add(next_s.toUpperCase());
                    message = client_scanner.nextLine();

                    System.out.println("Server Received: "+next_s+" "+ time);

                }

                String dep_time= client_scanner.nextLine();
                System.out.println("Server Received: "+dep_time);
                String arr_time= client_scanner.nextLine();
                System.out.println("Server Received: "+arr_time);

                String days= client_scanner.nextLine();
                System.out.println("Server Received: "+days);
                if(days.equals("EVERY")){
                    //String next = client_scanner.next();
                    //System.out.println("Server Received: "+next);
                    boolean every_day = true;
                    //check if code already exists if not we can make a new treno
                    //if yes send a code_notok message to client
                    HashMap<String,Treno> copy = trainmap.getMapCopy();
                    while(copy.containsKey(code)) {
                        pw.println("CODE_NOTOK");
                        pw.flush();
                        code = client_scanner.nextLine();
                        System.out.println("Server Received: "+code);


                    }
                    pw.println("CODE_OK");
                    pw.flush();

                    Treno t = new Treno(code,seats,stops, LocalTime.parse(dep_time),LocalTime.parse(arr_time),Float.parseFloat(prize),times);
                    t.setEvery_day(every_day);

                    trainmap.add(code,t);
                    System.out.println("SERVER LOG:Added "+t.toString());




                }
                else {
                    ArrayList<String> dayList = new ArrayList<>();
                    while (!days.equals("END_OFMSG")) {


                            System.out.println("Server Received: " +days);
                            dayList.add(days.toUpperCase());
                            System.out.println(dayList);
                            days = client_scanner.nextLine();



                    }




                    mapCopy = trainmap.getMapCopy();
                    while(mapCopy.containsKey(code)) {
                        pw.println("CODE_NOTOK");
                        pw.flush();
                        code = client_scanner.nextLine();
                        System.out.println("Server Received: "+code);


                    }
                    pw.println("CODE_OK");
                    pw.flush();
                    Treno t = new Treno(code, seats, stops, LocalTime.parse(dep_time), LocalTime.parse(arr_time), Float.parseFloat(prize),times);
                    t.setGiorni(dayList);
                    trainmap.add(code, t);
                    System.out.println("SERVER LOG:Added "+t.toString());


                }









            }
            else if (cmd.equals("SHOW")){




                pw.println(trainmap.toString());
                pw.flush();



            }
            else if(cmd.equals("SHOWCODES")){
                mapCopy = trainmap.getMapCopy();
                Set<String> keyset = mapCopy.keySet();
                pw.println("BEGIN");

                for (String m: keyset){
                    pw.println(mapCopy.get(m).toStringCode());
                    pw.flush();
                }
                pw.println("END");
                pw.flush();

            }
            else if(cmd.equals("SRC2")){//secondo tipo di ricerca ClientUtente
                String dep= msg_scanner.next().toUpperCase();
                String arr= msg_scanner.next().toUpperCase();
                String day = msg_scanner.next().toUpperCase();
                String time = msg_scanner.next();
                mapCopy = trainmap.getMapCopy();


                Set<String> keySet = mapCopy.keySet();

                for (String m: keySet){
                    if(mapCopy.get(m).isEvery_day()) {
                        if (mapCopy.get(m).getOrarioPartenza().compareTo(LocalTime.parse(time)) >= 0 && mapCopy.get(m).getStops().contains(dep) && !dep.equals(mapCopy.get(m).getStops().getLast()) && mapCopy.get(m).getStops().contains(arr) && !arr.equals(mapCopy.get(m).getStops().getFirst())) {

                            //cerco la partenza nella lista eccetto l'ultima posizione
                            //cerco treni con orario di partenza dopo l'orario fornito dall'utente
                            //cerco l'arrivo nella lista delle fermate eccetto la prima posizione


                                pw.println(mapCopy.get(m).toString());
                                pw.flush();



                        }
                        System.out.println("DEBUG: Treno  non trovato");
                    }
                    else{
                        if (mapCopy.get(m).getOrarioPartenza().compareTo(LocalTime.parse(time)) >= 0 && mapCopy.get(m).getStops().contains(dep) && !dep.equals(mapCopy.get(m).getStops().getLast()) && mapCopy.get(m).getStops().contains(arr) && !arr.equals(mapCopy.get(m).getStops().getFirst()) && mapCopy.get(m).getGiorni().contains(day)) {


                                pw.println(mapCopy.get(m).toString());
                                pw.flush();

                        }

                        }



                }
                pw.println("ENDOFTRAINS");
                pw.flush();




            }

            else if (cmd.equals("SAVE")) {
                try {
                    var oos = new ObjectOutputStream(new FileOutputStream("TrainMap.ser"));
                    mapCopy = trainmap.getMapCopy();

                    oos.writeObject(trainmap.toString());
                    oos.close();
                    pw.println("SAVE_OK");
                    pw.flush();
                    System.out.println("SERVER LOG: list saved correctly");

                } catch (IOException e) {
                    pw.println("SAVE_ERROR");
                    pw.flush();
                    e.printStackTrace();
                }

            }
            else if (cmd.equals("QUIT")) {
                System.out.println("Server: Closing connection to "+client_socket.getRemoteSocketAddress());
                try {
                    pw.println("QUIT_OK");
                    pw.flush();
                    client_socket.close();

                } catch (IOException e) {
                    pw.println("QUIT_ERROR");
                    pw.flush();
                    e.printStackTrace();
                }
                go = false;
            }
            else if(cmd.equals("REM")){
                String trainCode = msg_scanner.next();
                mapCopy = trainmap.getMapCopy();
                System.out.println("Serve: received  "+trainCode);
                if (mapCopy.containsKey(trainCode)){
                    System.out.println("SERVER LOG: Removing train with code "+trainCode);
                    trainmap.remove(trainCode);
                    pw.println("REM_OK");
                    pw.flush();

                }
                else{
                    System.out.println("SERVER LOG: Train not found "+trainCode);
                    pw.println("REM_ERR");
                    pw.flush();
                }

            }
            else if(cmd.equals("SRC1")){//comando ricerca da clientUtente tramite codice
                String code = msg_scanner.next();
                mapCopy = trainmap.getMapCopy();
                boolean coding= true;


                while(coding){
                    if(!mapCopy.containsKey(code) && !code.equals("BACK")) {
                        pw.println("CODE_NOTOK");
                        pw.flush();
                        code = client_scanner.nextLine();
                    }
                    else if (code.equals("BACK")){
                        coding= false;
                        System.out.println("La ricerca è stata interrotta..");
                    }
                    else{//se siamo qui il treno è presente almeno nella mappa generica dei treni



                            pw.println(mapCopy.get(code).toString());
                            pw.flush();
                            coding = false;

                    }


                }




            }
            else if(cmd.equals("SRCH")){//comando ricerca da clientTreno
                mapCopy = trainmap.getMapCopy();
                String code = msg_scanner.next();
                while(!mapCopy.containsKey(code)){
                    pw.println("CODE_NOTOK");
                    pw.flush();
                    code= client_scanner.nextLine();
                }
                pw.println(mapCopy.get(code).toString());
                pw.flush();




                String cmd2 = client_scanner.nextLine();
                if (cmd2.equals("START")){
                    if(mapCopy.get(code).isMoving()){
                        pw.println("NOTSTART");
                        pw.flush();
                    }
                    else{

                        trainmap.update(code);
                        pw.println("START_OK");
                        pw.flush();


                    }

                }
                else if (cmd2.equals("RET")){
                    System.out.println("Nothing to do with train "+code);
                }
                else {
                    System.out.println("Error: unkown "+cmd2);
                }



            }
            else {
                System.out.println("Unknown command "+ message);
                pw.println("ERROR_CMD");
                pw.flush();
            }



}
    }
}
