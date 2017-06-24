import com.ly.Test;

/**
 * Created by Lo__oY on 2017/6/11.
 */
public class TestClassLoader {

    public static void main(String args[]){

        TestClassLoader tcl = new TestClassLoader();
        System.out.println("Main------- getclass   " + tcl.getClass().getClassLoader());
        System.out.println("Main------- thread   " + Thread.currentThread().getContextClassLoader());
        try {
            Class clz = Thread.currentThread().getContextClassLoader().loadClass("com.ly.Test");
            Test test = (Test)clz.newInstance();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for(int i = 0;i<40;i++){

            new Thread(()->{
                    TestClassLoader ttcl = new TestClassLoader();
            System.out.println("Main------- getclass   " + ttcl.getClass().getClassLoader());
            System.out.println("Main------- thread   " + Thread.currentThread().getContextClassLoader());
            }
            ).start();
        }
    }
}
