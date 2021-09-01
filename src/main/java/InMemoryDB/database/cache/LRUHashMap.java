package InMemoryDB.database.cache;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class LRUHashMap<K, V> extends LinkedHashMap<K, V> {

    private final int size;

    LRUHashMap(int size) {
        super(size, 0.75f, true);
        this.size = size;

    }


    @SuppressWarnings("rawtypes")
    public static LRUHashMap newInstance(int size) {

        return new LRUHashMap(size);
    }


    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > getSize();
    }


}
