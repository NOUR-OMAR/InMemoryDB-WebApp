package InMemoryDB.server.database.cache;

public interface Cache<K, V> {


    V get(K key);

    V put(K key, V value);

    V remove(K key);


    void clear();


    int getMaxMemorySize();


    int getMemorySize();
}
