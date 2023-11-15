import java.util.concurrent.locks.ReentrantLock;

public class MutualExclusionLock implements MyLock{
    private boolean[] flag;
    private boolean[] access;
    private int[] label;
    private int n;
    private int[] access_counter;
    public MutualExclusionLock(int numThreads) {
        n = numThreads;
        flag = new boolean[n];
        access = new boolean[n];
        label = new int[n];
        access_counter = new int[n];
        init();
    }

    private void init() {
        for (int k = 0; k < n; k++) {
            flag[k] = false;
            access[k] = false;
            label[k] = k + 1;
        }
    }

    public void lock(int i) {
        flag[i] = true;
        do {
            access[i] = false;
            while (!checkFlagsAndLabels(i)) ;
            access[i] = true;
        } while (existsAccess(i));
        access_counter[i]++;
        System.out.println("Thread " + i + " and access counter " + access_counter[i] + " is in critical section");
    }

    public void unlock(int i) {
        label[i] = getMaxLabelValue()+1;
        printLabels();
        access[i] = false;
        flag[i] = false;
    }

    private void printLabels() {
        for (int j = 0; j < n; j++) {
            System.out.print(label[j] + " ");
        }
        System.out.println();
    }
    private int getMaxLabelValue() {
        int maxLabelValue = 0;
        for (int j = 0; j < n; j++) {
            maxLabelValue = Math.max(maxLabelValue, label[j]);
        }
        return maxLabelValue;
    }

    private boolean checkFlagsAndLabels(int i) {
        for (int j = 0; j < n; j++) {
            if (j != i && flag[j] && (label[j] < label[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean existsAccess(int i) {
        for (int j = 0; j < n; j++) {
            if (j != i && access[j]) {
                return true;
            }
        }
        return false;
    }
}
