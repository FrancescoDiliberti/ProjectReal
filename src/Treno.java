import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Treno implements Serializable {
    private String codice;
    private int postiDisponibili;
    private boolean moving = false;
    private boolean arrived = false;
    private LocalTime orarioPartEff;
    private LocalTime orarioArrEff;
    private LocalTime lastUpdate;
    private LinkedList<String> stops = new LinkedList<>();
    private LocalTime orarioPartenza;
    private LocalTime orarioArrivo;
    private ArrayList<String> giorni= new ArrayList<>();
    private float ticket_prize;
    private boolean every_day = false;
    private ArrayList<String> history = new ArrayList<>();
    private String lastStopDone;
    private ArrayList<Long> timeBetweenStops = new ArrayList<>();

    public LocalTime getOrarioArrEff() {
        return orarioArrEff;
    }

    public void setOrarioArrEff(LocalTime orarioArrEff) {
        this.orarioArrEff = orarioArrEff;
    }

    public LocalTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public LocalTime getOrarioPartEff() {
        return orarioPartEff;
    }

    public void setOrarioPartEff(LocalTime orarioPartEff) {
        this.orarioPartEff = orarioPartEff;
    }

    public boolean isArrived() {
        return arrived;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }



    public ArrayList<Long> getTimeBetweenStops() {
        return timeBetweenStops;
    }

    public void setTimeBetweenStops(ArrayList<Long> timeBetweenStops) {
        this.timeBetweenStops = timeBetweenStops;
    }

    public String getLastStopDone() {
        return lastStopDone;
    }

    public void setLastStopDone(String lastStopDone) {
        this.lastStopDone = lastStopDone;
    }

    public Treno(String codice, int postiDisponibili, LinkedList<String> stops, LocalTime orarioPartenza, LocalTime orarioArrivo, float ticket_prize, ArrayList<Long> timeBetweenStops) {
        this.codice = codice;
        this.postiDisponibili = postiDisponibili;
        this.stops = stops;
        this.orarioPartenza = orarioPartenza;
        this.orarioArrivo = orarioArrivo;
        this.ticket_prize = ticket_prize;
        this.timeBetweenStops = timeBetweenStops;
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
        if(!isMoving()) {
            if(arrived){
                String s = "BEGIN\n\n";
                s=s+ "------CODICE: "+ codice+ "\n\n";
                s= s+ "-----POSTI A SEDERE: "+ postiDisponibili + "\n\n";
                int i=1;
                for(String m: stops){

                    s=s+ "---STOP "+i+ ": " +m;
                    i++;
                }

                s=s+"\n\n----FROM SCHEDULE---\n";
                s=s+ "\n-----ORARIO DI PARTENZA: "+ orarioPartenza+ "\n\n";
                s=s+ "-----ORARIO DI ARRIVO: "+ orarioArrivo+"\n\n";
                s=s+ "-----ORARIO DI PARTENZA EFFETTIVO: " +orarioPartEff+"\n\n";
                s=s +"---ARRIVATO A "+lastStopDone+" ALLE "+orarioArrEff+"\n\n";

                if(orarioArrivo.compareTo(orarioArrEff)<0) {

                    s = s + "---IN RITARDO DI " + ChronoUnit.HOURS.between(orarioArrivo, orarioArrEff) + " ORE E " + (ChronoUnit.MINUTES.between(orarioArrivo, orarioArrEff) - 60 * ChronoUnit.HOURS.between(orarioArrivo, orarioArrEff)) + " MINUTI\n\n";
                }
                else if(orarioArrivo.compareTo(orarioArrEff)==0){
                    s=s + "---IN ORARIO\n\n";
                }
                else{
                    s = s + "---IN ANITICIPO DI " + ChronoUnit.HOURS.between(orarioArrEff,orarioArrivo) + " ORE E " + (ChronoUnit.MINUTES.between(orarioArrEff,orarioArrivo) - 60 * ChronoUnit.HOURS.between(orarioArrEff,orarioArrivo)) + " MINUTI\n\n";

                }
                s=s+"----VIAGGI PRECEDENTI EFFETTUATI: ";
                if(history.isEmpty()){
                    s=s + "NESSUNO\n\n";
                }
                else {

                    for (String r : history) {
                        s=s+ "\n"+r;


                    }
                }
                s= s + "\n\n---PREZZO DEL BIGLIETTO :" +ticket_prize;
                s=s +"\n\nEND";


                return s;
            }
            else {


                String s = "BEGIN\n\n";
                s = s + "----CODICE: " + codice + "\n\n";
                s = s + "---POSTI DISPONIBILI: " + postiDisponibili + "\n\n";
                int i = 1;
                for (String m : stops) {

                    s = s + "---FERMATA " + i + ": " + m;
                    i++;
                }
                for( Long l: timeBetweenStops){
                    s=s+ "\n\n---time "+l;
                }
                s = s + "\n\n---ORARIO DI PARTENZA: " + orarioPartenza + "\n\n";
                s = s + "---ORARIO DI ARRIVO: " + orarioArrivo + "\n\n";
                s = s + "---DURATA DEL VIAGGIO: " + ChronoUnit.HOURS.between(orarioPartenza, orarioArrivo) + " ORE E " + (ChronoUnit.MINUTES.between(orarioPartenza, orarioArrivo) - 60 * ChronoUnit.HOURS.between(orarioPartenza, orarioArrivo)) + " MINUTI\n";
                s = s + "\n---PREZZO DEL BIGLIETTO: " + ticket_prize + "\n\n";
                if (isEvery_day()) {
                    s = s + "---OGNI GIORNO\n\n";
                } else {
                    int y = 1;
                    for (String n : giorni) {

                        s = s + "---GIORNO " + y + " " + n + "\n\n";
                        y++;
                    }
                }
                s=s+"----VIAGGI PRECEDENTI EFFETTUATI: ";
                if(history.isEmpty()){
                    s=s + "NESSUNO\n\n";
                }
                else {

                    for (String r : history) {
                        s=s+ "\n"+r;


                    }
                }

                s = s + "END";

                return s;
            }
        }
        else{
            
            
                String s = "BEGIN\n\n";
            s=s+ "------CODICE: "+ codice+ "\n\n";
            s= s+ "-----POSTI A SEDERE: "+ postiDisponibili + "\n\n";
            int i=1;
            for(String m: stops){

                s=s+ "---STOP "+i+ ": " +m;
                i++;
            }
            s=s+"\n\n----FROM SCHEDULE---\n";
            s=s+ "\n-----ORARIO DI PARTENZA: "+ orarioPartenza+ "\n\n";
            s=s+ "-----ORARIO DI ARRIVO: "+ orarioArrivo+"\n\n";
            s=s+ "-----ORARIO DI PARTENZA EFFETTIVO: " +orarioPartEff+"\n\n";

                if(orarioPartenza.compareTo(orarioPartEff)<0) {
                    s = s + "\nTRENO PER " + stops.getLast() + " PARTITO CON UN RITARDO DI " + ChronoUnit.HOURS.between(orarioPartenza, orarioPartEff) + " ORE E " + (ChronoUnit.MINUTES.between(orarioPartenza, orarioPartEff) - 60 * ChronoUnit.HOURS.between(orarioPartenza, orarioPartEff)) + " MINUTI\n\n";
                }
                else if(orarioPartenza.compareTo(orarioPartEff)==0){
                    s=s +"----TRENO PARTITO IN ORARIO\n\n";
                }
                else{
                    s=s+"-----TRENO PARTITO CON UN ANTICIPO DI "+ ChronoUnit.HOURS.between(orarioPartEff,orarioPartenza)+" ORE E "+ (ChronoUnit.MINUTES.between(orarioPartEff,orarioPartenza)-60*ChronoUnit.HOURS.between(orarioPartEff,orarioPartenza))+" MINUTI\n\n";
                }
                s=s+"----ULTIMA FERMATA EFFETTUATA: "+lastStopDone+" ALLE "+lastUpdate+"\n\n";
                s=s+"----VIAGGI PRECEDENTI EFFETTUATI: ";
                if(history.isEmpty()){
                    s=s + "NESSUNO\n\n";
                }
                else {

                    for (String r : history) {
                        s=s+ "\n"+r;


                    }
                }
                s= s + "\n\n---PREZZO DEL BIGLIETTO :" +ticket_prize;
                s=s +"\n\nEND";



                return s;
            }

        
    }
    public String toStringCode(){
        String s;

        s="\n\nCODICE: "+ codice +" DA " + stops.getFirst() + " A "+ stops.getLast();

        return s;


    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<String> history) {
        this.history = history;
    }
    public void start(){
        Move move = new Move(this);
        Thread t = new Thread(move);
        t.start();
    }


}
