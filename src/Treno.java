import java.sql.SQLOutput;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

public class Treno {
    private int codice;
    private int postiDisponibili;
    private String[] fermate; //fermate[0]-->partenza
    private LocalTime orarioPartenza;
    private LocalTime orarioArrivo;
    private String[] giorni;

    public Treno(int codice, int postiDisponibili, String[] fermate, LocalTime orarioPartenza, LocalTime orarioArrivo, String[] giorni) {
        this.codice = codice;
        this.postiDisponibili = postiDisponibili;
        this.fermate = fermate;
        this.orarioPartenza = orarioPartenza;
        this.orarioArrivo = orarioArrivo;
        this.giorni = giorni;
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public void setPostiDisponibili(int postiDisponibili) {
        this.postiDisponibili = postiDisponibili;
    }

    public String[] getFermate() {
        return fermate;
    }

    public void setFermate(String[] fermate) {
        this.fermate = fermate;
    }

    public String[] getGiorni() {
        return giorni;
    }

    public void setGiorni(String[] giorni) {
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
        //Treno t = new Treno();
        //t.setOrarioPartenza(LocalTime.parse("03:30"));
    //t.setOrarioArrivo(LocalTime.parse("07:00"));
        //System.out.println(t.getOrarioPartenza());
        //System.out.println(t.getOrarioArrivo());
        //LocalTime arrivo = t.getOrarioArrivo();
        //LocalTime partenza = t.getOrarioPartenza();
        //System.out.println("Duarata viaggio: "+ ChronoUnit.HOURS.between(partenza,arrivo)+":"+ (ChronoUnit.MINUTES.between(partenza,arrivo)-60*ChronoUnit.HOURS.between(partenza,arrivo)));}


}
