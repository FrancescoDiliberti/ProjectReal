import java.time.LocalTime;
import java.util.Date;

public class Move implements Runnable {

    private Treno t ;

    public Move(Treno t) {
        this.t = t;
    }

    @Override
    public void run() {
        t.setMoving(true);
        if(t.isArrived()){
            t.setArrived(false);
        }

        t.setOrarioPartEff(LocalTime.now());
        String story = new Date().toString();
        int i= 0;
        t.setLastStopDone(t.getStops().get(0));
        t.setLastUpdate(LocalTime.now());
        System.out.println("Last stop done: " + t.getLastStopDone() + "--Last Update: " + t.getLastUpdate());
        int y= 1;


        while(!t.getLastStopDone().equals(t.getStops().getLast())) {
            long time = t.getTimeBetweenStops().get(i);
            try {
                Thread.sleep(time * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t.setLastStopDone(t.getStops().get(y));
            t.setLastUpdate(LocalTime.now());
            System.out.println("Last stop done: " + t.getLastStopDone() + "--Last Update: " + t.getLastUpdate());
            y++;



            i++;
        }



        story = story + "--->" + new Date().toString();
        t.getHistory().add(story);
        System.out.println(t.getHistory().toString());
        t.setOrarioArrEff(LocalTime.now());
        t.setArrived(true);
        t.setMoving(false);


    }
}
