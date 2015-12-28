package ua.telesens.ostapenko.systemimitation;

import java.io.IOException;
import java.util.Date;

/**
 * @author root
 * @since 12.12.15
 */
public class DemoBar {

    public static void main(String[] args) throws IOException, InterruptedException {

//        new DemoBar().delay(50000);
        new DemoBar().loading("Execute",2000);

    }
    private void delay(long milliseconds) {
        String bar = "[--------------------]";
        String icon = "%";

        long startTime = new Date().getTime();
        boolean bouncePositive = true;
        int barPosition = 0;

        while ((new Date().getTime() - startTime) < milliseconds) {
            if (barPosition < bar.length() && barPosition > 0) {
                String b1 = bar.substring(0, barPosition);
                String b2 = bar.substring(barPosition);
                System.out.print("\r Delaying: " + b1 + icon + b2);
                if (bouncePositive) barPosition++;
                else barPosition--;
            }
            if (barPosition == bar.length()) {
                barPosition--;
                bouncePositive = false;
            }
            if (barPosition == 0) {
                barPosition++;
                bouncePositive = true;
            }

            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
        System.out.print("\n");
    }
    private static boolean loading = true;
    private static synchronized void loading(String msg,long milliseconds) throws IOException, InterruptedException {
        System.out.println(msg);
        long startTime = new Date().getTime();
        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    System.out.write("\r|".getBytes());
                    while((new Date().getTime() - startTime) < milliseconds) {
                        System.out.write("#".getBytes());
                        Thread.sleep(500);
                    }
                    System.out.write("| Done \r\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        th.start();
    }
}
