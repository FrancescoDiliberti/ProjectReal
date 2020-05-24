import java.lang.reflect.Array;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

public class Treno {
    private String codice;
    private int postiDisponibili;
    private ArrayList<String> stops;
    private LocalTime orarioPartenza;
    private LocalTime orarioArrivo;
    private ArrayList<String> giorni;
    private long ticket_prize;

    public void setStops(ArrayList<String> stops) {
        this.stops = stops;
    }

    public long getTicket_prize() {
        return ticket_prize;
    }

    public void setTicket_prize(long ticket_prize) {
        this.ticket_prize = ticket_prize;
    }

    public boolean isEvery_day() {
        return every_day;
    }

    public void setEvery_day(boolean every_day) {
        this.every_day = every_day;
    }

    private boolean every_day = false;


    public Treno(String codice, int postiDisponibili, ArrayList<String> stops, LocalTime orarioPartenza, LocalTime orarioArrivo, long ticket_prize) {
        this.codice = codice;
        this.postiDisponibili = postiDisponibili;
        this.stops = stops;
        this.orarioPartenza = orarioPartenza;
        this.orarioArrivo = orarioArrivo;
        this.ticket_prize = ticket_prize;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public void setPostiDisponibili(int postiDisponibili) {
        this.postiDisponibili = postiDisponibili;
    }


    public ArrayList<String>  getGiorni() {
        return giorni;
    }

    public void setGiorni(ArrayList<String> giorni) {
        this.giorni = giorni;
    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    public LocalTime getOrarioPartenza() {
        return orarioPartenza;
    }

    public LocalTime getOrarioArrivo() {
        return orarioArrivo;
    }

    public void setOrarioArrivo(LocalTime orarioArrivo) {
        this.orarioArrivo = orarioArrivo;
    }

    public void setOrarioPartenza(LocalTime orarioPartenza) {
        this.orarioPartenza = orarioPartenza;
    }
   //public static void main(String[] args){
        //TrainMap map= new TrainMap();

        /*Treno t = new Treno();
        t.setOrarioPartenza(LocalTime.parse("03:30"));
    t.setOrarioArrivo(LocalTime.parse("07:00"));
        System.out.println(t.getOrarioPartenza());
        System.out.println(t.getOrarioArrivo());
        LocalTime arrivo = t.getOrarioArrivo();
        LocalTime partenza = t.getOrarioPartenza();
        System.out.println("Duarata viaggio: "+ ChronoUnit.HOURS.between(partenza,arrivo)+":"+ (ChronoUnit.MINUTES.between(partenza,arrivo)-60*ChronoUnit.HOURS.between(partenza,arrivo)));
        */
        /*String[] x = new String[2];
        x[0]="Catania";
        x[1]="Palermo";
        System.out.println("Fermate :"+Arrays.toString(x));*/


   //}



}
