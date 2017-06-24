import java.util.concurrent.TimeUnit;

/**
 * Created by Lo__oY on 2017/6/11.
 */
public class TestStop {

    private static boolean stop = false;
    public static void main(String args[]) throws InterruptedException {

     Thread workThread =  new Thread(()->{
            int i = 0;
            while(!stop){

                i++;
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        workThread.start();
            TimeUnit.SECONDS.sleep(3);
            stop = true;

    }
}
