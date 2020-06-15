import java.time.LocalTime;
import java.util.HashMap;
import java.util.Set;

public class TrainStartedMap{
    private HashMap<String,TrenoPartito> map = new HashMap<>();
    public synchronized void add(String s, TrenoPartito t){
        map.put(s, t);
    }
    public synchronized void removeAll(){
        map.clear();
    }
    public synchronized boolean update(String code,int i){
        map.get(code).setUltimaFermataPassata(map.get(code).getStops().get(i));
        map.get(code).setLastUpdate(LocalTime.now());
        System.out.println("Last stop done: "+map.get(code).getUltimaFermataPassata()+"--Last Update: "+map.get(code).getLastUpdate());

        if(i==map.get(code).getStops().size()-1){
            map.get(code).setArrived(true);
            map.get(code).setOrarioArrEff(LocalTime.now());
            return false;

        }
        else return true;
    }

    @Override
    public String toString() {
        return "TrainStartedMap{" +
                "map=" + map +
                '}';
    }
    public HashMap<String,TrenoPartito> getMapCopy(){
        HashMap<String,TrenoPartito> mapCopy = new HashMap<>();
        Set<String> keyset = map.keySet();
        for (String m:keyset){
            TrenoPartito copy = new TrenoPartito(map.get(m).getCodice(),map.get(m).getPostiDisponibili(),map.get(m).getStops(), map.get(m).getOrarioPartenza(),map.get(m).getOrarioArrivo(),map.get(m).getTicket_prize(), map.get(m).getOrarioPartEff());
            copy.setLastUpdate(map.get(m).getLastUpdate());
            copy.setArrived(map.get(m).isArrived());
            copy.setUltimaFermataPassata(map.get(m).getUltimaFermataPassata());
            copy.setOrarioArrEff(map.get(m).getOrarioArrEff());
            mapCopy.put(m,copy);


        }
        return mapCopy;
    }
}
