public class Main {
    public static void main(String[] args) throws InterruptedException {
        int numThreads = 4;
        MyLock mutualExclusionLock = new MyMutualExclusionLock(numThreads);
        Thread[] threads = new Thread[numThreads];
        // Creating and starting threads
        for (int i = 0; i < numThreads; i++) {
            Thread thread = new ThreadTest(i, mutualExclusionLock);
            threads[i] = thread;
        }

        for (int i = 0; i < numThreads; i++) {
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
        }
    }
}