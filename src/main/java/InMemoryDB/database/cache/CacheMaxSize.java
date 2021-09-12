package InMemoryDB.database.cache;

public enum CacheMaxSize {
    CACHE_MAX_SIZE(1000);
    private int size;

    CacheMaxSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
