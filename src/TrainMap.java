import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TrainMap implements Serializable {
    private HashMap<String,Treno> map = new HashMap<>();
    public synchronized void add(String s, Treno t){
        map.put(s, t);
    }
    public synchronized void remove(String code){
        map.remove(code);
    }
    public void update(String code){

        Move m = new Move(map.get(code));
        Thread t = new Thread(m);
        t.start();
    }

    @Override
    public String toString() {
        String s= "BEGIN\n";
        Set<String> keyset = map.keySet();
        int i= 1;
        for (String m: keyset){
            Treno t = map.get(m);
            s= s +" -----------------------------------------------TRENO "+i+"\n\n";
            s= s+ "---CODICE :" +t.getCodice()+"\n\n";
            s= s+ " ---POSTI DISPONIBILI :" +t.getPostiDisponibili()+"\n\n";
            s= s+ " ---STAZIONE DI PARTENZA :" +t.getStops().get(0)+"\n\n";
            s = s+ " ---STAZIONE DI ARRIVO :" + t.getStops().get(t.getStops().size()-1)+"\n\n";
            if (t.getStops().size()>2){
                int x = 1;
                for( String stop : t.getStops()){
                    s= s + "---FERMATA "+x+ " "+stop;
                    x++;
                }

            }
            s = s+ "\n\n ---PREZZO DEL BIGLIETTO: " + t.getTicket_prize()+" Euro\n\n";
            s= s + "---ORARIO DI PARTENZA: " + t.getOrarioPartenza()+"\n\n";
            s= s + "---ORARIO DI ARRIVO: " +t.getOrarioArrivo()+"\n\n";
            if(t.isEvery_day()){
                s=s+ "OGNI GIORNOY\n\n";
            }
            else{
                int y= 1;
                for( String days: t.getGiorni()){

                    s= s+"---GIORNO "+y+ " " +days+ "\n\n";
                    y++;

                }
            }
            i++;

        }
        s = s + "END";
        return s;
    }
    public synchronized HashMap<String,Treno> getMapCopy(){
        HashMap<String,Treno> mapCopy = new HashMap<>();
        Set<String> keyset = map.keySet();
        for (String m:keyset){
            Treno copy = new Treno(map.get(m).getCodice(),map.get(m).getPostiDisponibili(),map.get(m).getStops(), map.get(m).getOrarioPartenza(),map.get(m).getOrarioArrivo(),map.get(m).getTicket_prize(),map.get(m).getTimeBetweenStops());
            copy.setGiorni(map.get(m).getGiorni());
            copy.setEvery_day(map.get(m).isEvery_day());
            copy.setOrarioPartEff(map.get(m).getOrarioPartEff());
            copy.setMoving(map.get(m).isMoving());

            copy.setHistory(map.get(m).getHistory());
            copy.setArrived(map.get(m).isArrived());
            copy.setOrarioArrEff(map.get(m).getOrarioArrEff());
            copy.setLastUpdate(map.get(m).getLastUpdate());
            copy.setLastStopDone(map.get(m).getLastStopDone());

            mapCopy.put(m,copy);

        }
        return mapCopy;
    }
}
