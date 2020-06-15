import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Treno implements Serializable {
    private String codice;
    private int postiDisponibili;
    private boolean partito = false;

    public boolean isPartito() {
        return partito;
    }

    public void setPartito(boolean partito) {
        this.partito = partito;
    }

    private LinkedList<String> stops = new LinkedList<>();
    private LocalTime orarioPartenza;
    private LocalTime orarioArrivo;
    private ArrayList<String> giorni= new ArrayList<>();
    private float ticket_prize;
    private boolean every_day = false;

    public Treno(String codice, int postiDisponibili, LinkedList<String> stops, LocalTime orarioPartenza, LocalTime orarioArrivo, float ticket_prize) {
        this.codice = codice;
        this.postiDisponibili = postiDisponibili;
        this.stops = stops;
        this.orarioPartenza = orarioPartenza;
        this.orarioArrivo = orarioArrivo;
        this.ticket_prize = ticket_prize;
    }

    public LinkedList<String> getStops() {
        return stops;
    }
    public void setStops(LinkedList<String> stops) {
        this.stops = stops;
    }

    public float getTicket_prize() {
        return ticket_prize;
    }

    public void setTicket_prize(float ticket_prize) {
        this.ticket_prize = ticket_prize;
    }

    public boolean isEvery_day() {
        return every_day;
    }

    public void setEvery_day(boolean every_day) {
        this.every_day = every_day;

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
       this.giorni= giorni;
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

    @Override
    public String toString() {
        String s= "BEGIN\n\n";
        s=s+ "----CODICE: "+ codice+ "\n\n";
        s= s+ "---POSTI DISPONIBILI: "+ postiDisponibili + "\n\n";
        int i=1;
        for(String m: stops){

            s=s+ "---FERMATA "+i+ ": " +m;
            i++;
        }
        s=s+ "\n\n---ORARIO DI PARTENZA: "+ orarioPartenza+ "\n\n";
        s=s+ "---ORARIO DI ARRIVO: "+ orarioArrivo+"\n\n";
        s=s+ "---DURATA DEL VIAGGIO: "+ ChronoUnit.HOURS.between(orarioPartenza,orarioArrivo)+" ORE E "+ (ChronoUnit.MINUTES.between(orarioPartenza,orarioArrivo)-60*ChronoUnit.HOURS.between(orarioPartenza,orarioArrivo))+" MINUTI\n";
        s=s+ "\n---PREZZO DEL BIGLIETTO: " + ticket_prize+ "\n\n";
        if(isEvery_day()){
            s=s+ "---OGNI GIORNO\n\n";
        }
        else {
            int y=1;
            for (String n : giorni) {

                s = s + "---GIORNO "+y+ " "+ n+"\n\n";
                y++;
            }
        }
        s=s+ "END";

        return s;
    }
    public String toStringCode(){
        String s;

        s="\n\nCODICE: "+ codice +" DA " + stops.getFirst() + " A "+ stops.getLast();

        return s;


    }





}
