package javmarr.mazeGame;

public class AnimationThread extends Thread {

    TankFrame frame;

    AnimationThread(TankFrame p) {
        frame = p;
    }

    public void run() {

        while (true) {
            //update the panel
            frame.update();

            //wait a few milliseconds
            try {
                Thread.sleep(25);
            } catch (Exception e) {
            }
        }

    }

}
