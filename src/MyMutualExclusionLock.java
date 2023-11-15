import java.util.concurrent.locks.ReentrantLock;

public class MyMutualExclusionLock implements MyLock{
    private boolean[] flag;
    private boolean[] access;
    private int[] label;
    private int n;
    private int[] access_counter;
    public MyMutualExclusionLock(int numThreads) {
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
        shiftValueLabels(i);
        printLabels();
        access[i] = false;
        flag[i] = false;
    }

    private void shiftValueLabels(int i) {
        int currentLabelValue = label[i];
        int maxLabelValue = label[i];

        for (int j = 0; j < n; j++) {
            if (label[j] > currentLabelValue) {
                maxLabelValue = Math.max(maxLabelValue, label[j]);
                label[j]--;
            }
        }

        label[i] = maxLabelValue;
    }

    private void printLabels() {
        for (int j = 0; j < n; j++) {
            System.out.print(label[j] + " ");
        }
        System.out.println();
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
