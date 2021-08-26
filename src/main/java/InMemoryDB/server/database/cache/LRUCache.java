package InMemoryDB.server.database.cache;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private final int size;

    LRUCache(int size) {
        super(size, 0.75f, true);
        this.size = size;

    }


    @SuppressWarnings("rawtypes")
    public static LRUCache newInstance(int size) {

        return new LRUCacheBuilder().setSize(size).createLRUCache();
    }


    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > getSize();
    }


}
