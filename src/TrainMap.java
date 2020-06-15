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

    @Override
    public String toString() {
        String s= "BEGIN\n";
        Set<String> keyset = map.keySet();
        int i= 1;
        for (String m: keyset){
            Treno t = map.get(m);
            s= s +" -----------------------------------------------TRAIN "+i+"\n";
            s= s+ "---CODE :" +t.getCodice()+"\n";
            s= s+ " ---SEATS :" +t.getPostiDisponibili()+"\n";
            s= s+ " ---Departure STATION :" +t.getStops().get(0)+"\n";
            s = s+ " ---Arrive STATION :" + t.getStops().get(t.getStops().size()-1)+"\n";
            if (t.getStops().size()>2){
                int x = 1;
                for( String stop : t.getStops()){
                    s= s + "---Stop "+x+ " "+stop;
                    x++;
                }

            }
            s = s+ "\n ---Ticket Prize :" + t.getTicket_prize()+" Euros\n";
            s= s + "---Departure Time :" + t.getOrarioPartenza()+"\n";
            s= s + "---Arrival Time :" +t.getOrarioArrivo()+"\n";
            if(t.isEvery_day()){
                s=s+ "OGNI GIORNOY\n";
            }
            else{
                int y= 1;
                for( String days: t.getGiorni()){

                    s= s+"---DAY "+y+ " " +days+ "\n";
                    y++;

                }
            }
            i++;

        }
        s = s + "END";
        return s;
    }
    public HashMap<String,Treno> getMapCopy(){
        HashMap<String,Treno> mapCopy = new HashMap<>();
        Set<String> keyset = map.keySet();
        for (String m:keyset){
            Treno copy = new Treno(map.get(m).getCodice(),map.get(m).getPostiDisponibili(),map.get(m).getStops(), map.get(m).getOrarioPartenza(),map.get(m).getOrarioArrivo(),map.get(m).getTicket_prize());
            copy.setGiorni(map.get(m).getGiorni());
            copy.setEvery_day(map.get(m).isEvery_day());
            mapCopy.put(m,copy);

        }
        return mapCopy;
    }
}
