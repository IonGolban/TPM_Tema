public interface MyLock {
    void lock(int threadId);
    void unlock(int threadId);
}
