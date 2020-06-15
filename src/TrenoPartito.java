import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;

public class TrenoPartito extends Treno {
    private String ultimaFermataPassata;
    private boolean arrived = false;
    private LocalTime orarioPartEff;
    private LocalTime orarioArrEff;
    private LocalTime lastUpdate;

    public LocalTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isArrived() {
        return arrived;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public LocalTime getOrarioPartEff() {
        return orarioPartEff;
    }

    public void setOrarioPartEff(LocalTime orarioPartEff) {
        this.orarioPartEff = orarioPartEff;
    }

    public LocalTime getOrarioArrEff() {
        return orarioArrEff;
    }

    public void setOrarioArrEff(LocalTime orarioArrEff) {
        this.orarioArrEff = orarioArrEff;
    }

    public String getUltimaFermataPassata() {
        return ultimaFermataPassata;
    }

    public void setUltimaFermataPassata(String ultimaFermataPassata) {
        this.ultimaFermataPassata = ultimaFermataPassata;
    }

    public TrenoPartito(String codice, int postiDisponibili, LinkedList<String> stops, LocalTime orarioPartenza, LocalTime orarioArrivo, float ticket_prize,LocalTime orarioPartEff) {
        super(codice, postiDisponibili, stops, orarioPartenza, orarioArrivo, ticket_prize);
        this.orarioPartEff= orarioPartEff;
    }
    public String toString() {
        String s= "BEGIN\n";
        s=s+ "------CODICE: "+ super.getCodice()+ "\n\n";
        s= s+ "-----POSTI A SEDERE: "+ super.getPostiDisponibili() + "\n\n";
        int i=1;
        for(String m: super.getStops()){

            s=s+ "---STOP "+i+ ": " +m;
            i++;
        }
        s=s+"\n\n----FROM SCHEDULE---\n";
        s=s+ "\n-----ORARIO DI PARTENZA: "+ super.getOrarioPartenza()+ "\n\n";
        s=s+ "-----ORARIO DI ARRIVO: "+ super.getOrarioArrivo()+"\n\n";
        s=s+ "-----ORARIO DI PARTENZA EFFETTIVO: " +getOrarioPartEff()+"\n\n";
        if(isArrived()){

            s=s +"---ARRIVATO A "+getUltimaFermataPassata()+" ALLE "+getOrarioArrEff()+"\n\n";

            if(super.getOrarioArrivo().compareTo(getOrarioArrEff())<0) {

                s = s + "---IN RITARDO DI " + ChronoUnit.HOURS.between(super.getOrarioArrivo(), getOrarioArrEff()) + " ORE E " + (ChronoUnit.MINUTES.between(super.getOrarioArrivo(), getOrarioArrEff()) - 60 * ChronoUnit.HOURS.between(super.getOrarioArrivo(), getOrarioArrEff())) + " MINUTI\n\n";
            }
            else if(super.getOrarioArrivo().compareTo(getOrarioArrEff())==0){
                s=s + "---IN ORARIO\n\n";
            }
            else{
                s = s + "---IN ANITICIPO DI " + ChronoUnit.HOURS.between(getOrarioArrEff(),super.getOrarioArrivo()) + " ORE E " + (ChronoUnit.MINUTES.between(getOrarioArrEff(),super.getOrarioArrivo()) - 60 * ChronoUnit.HOURS.between(getOrarioArrEff(),super.getOrarioArrivo())) + " MINUTI\n\n";

            }


        }
        else{

            if(super.getOrarioPartenza().compareTo(getOrarioPartEff())<0) {
                s = s + "\nTRENO PER " + super.getStops().getLast() + " PARTITO CON UN RITARDO DI " + ChronoUnit.HOURS.between(super.getOrarioPartenza(), getOrarioPartEff()) + " ORE E " + (ChronoUnit.MINUTES.between(super.getOrarioPartenza(), getOrarioPartEff()) - 60 * ChronoUnit.HOURS.between(super.getOrarioPartenza(), getOrarioPartEff())) + " MINUTI\n\n";
            }
            else if(super.getOrarioPartenza().compareTo(getOrarioPartEff())==0){
                s=s +"----TRENO PARTITO IN ORARIO\n\n";
            }
            else{
                s=s+"-----TRENO PARTITO CON UN ANTICIPO DI "+ ChronoUnit.HOURS.between(getOrarioPartEff(),super.getOrarioPartenza())+" ORE E "+ (ChronoUnit.MINUTES.between(getOrarioPartEff(),super.getOrarioPartenza())-60*ChronoUnit.HOURS.between(getOrarioPartEff(),super.getOrarioPartenza()))+" MINUTI\n\n";
            }
            s=s+"----ULTIMA FERMATA EFFETTUATA: "+getUltimaFermataPassata()+" ALLE "+getLastUpdate()+"\n\n";



        }

        s=s+ "-----PREZZO DEL BIGLIETTO: " + super.getTicket_prize()+ " EURO\n";


        s=s+ "END";

        return s;
    }
}
