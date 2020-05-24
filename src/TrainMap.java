import java.util.HashMap;

public class TrainMap {
    private HashMap<String,Treno> map = new HashMap<>();
    public void add(String s, Treno t){
        map.put(s, t);
    }


}
