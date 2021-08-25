public class ControlsProcess {
    public boolean flag = true;

    public synchronized void waitAccess(){
        if(!flag){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else{
            notify();
        }
    }
}
