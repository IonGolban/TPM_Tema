public class ThreadTest extends Thread {
    int threadId;
    MyLock myLock;


    public ThreadTest(int threadId, MyLock myLock) {
        this.threadId = threadId;
        this.myLock = myLock;
    }

    public void run() {
        int nrOfRuns = 100;
        while (nrOfRuns > 0) {
            if (Math.random() < 0.5) {
                myLock.lock(threadId);
                myLock.unlock(threadId);
                nrOfRuns--;
            }
            try {
                sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//            myLock.lock(threadId);
//            myLock.unlock(threadId);
//            nrOfRuns--;
        }

    }
}