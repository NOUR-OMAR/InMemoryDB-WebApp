package InMemoryDB.server.database.cache;

public class LRUCacheBuilder<K, V> {
    private int size;

    public LRUCacheBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    public LRUCache createLRUCache() {
        return new LRUCache(size);
    }
}