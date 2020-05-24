import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

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
                long prize = msg_scanner.nextLong();
                ArrayList<String> stops= new ArrayList<>();
                String departure = msg_scanner.next();
                stops.add(departure);

                String next_stop = client_scanner.nextLine();
                System.out.println("Server Received: "+next_stop);
                stops.add(next_stop);
                next_stop = client_scanner.nextLine();
                System.out.println("Server Received: "+next_stop);
                while(!next_stop.equals("ENDOFSTOPS")){
                    stops.add(next_stop);
                    next_stop = client_scanner.nextLine();
                    System.out.println("Server Received: "+next_stop);

                }
                String message_2 = client_scanner.nextLine();
                System.out.println("Server Received: "+message_2);
                Scanner msg_scanner_2 = new Scanner(message_2);
                String dep_time= msg_scanner_2.next();
                String arr_time= msg_scanner_2.next();

                int days= client_scanner.nextInt();
                System.out.println("Server Received: "+days);
                if(days==7){
                    String next = client_scanner.nextLine();
                    System.out.println("Server Received: "+next);
                    boolean every_day = true;
                    Treno t = new Treno(code,seats,stops, LocalTime.parse(dep_time),LocalTime.parse(arr_time),prize);
                    t.setEvery_day(every_day);
                    trainmap.add(code,t);
                    System.out.println("SERVER LOG:Added "+t);
                    pw.println("ADDTR_OK");
                    pw.flush();




                }else {
                    ArrayList<String> dayList = new ArrayList<>();
                    String next = client_scanner.nextLine();
                    System.out.println("Server Received: "+next);
                    while (!next.equals("END_OFMSG")) {
                        dayList.add(next);
                        next = client_scanner.nextLine();
                        System.out.println("Server Received: "+next);
                    }

                    Treno t = new Treno(code, seats, stops, LocalTime.parse(dep_time), LocalTime.parse(arr_time), prize);
                    t.setGiorni(dayList);
                    trainmap.add(code, t);
                    System.out.println("SERVER LOG:Added "+t);
                    pw.println("ADDTR_OK");
                    pw.flush();
                }









            }
            else if (cmd.equals("ADDCP")){


            }


}
    }
}
