package Repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepository<T> implements IRepository<T> {
    Map<Integer, T> objects = new HashMap<>();

    private int createNewId() {
        Map.Entry<Integer, T> lastEntry = null;

        for (Map.Entry<Integer, T> entry : objects.entrySet()) {
            lastEntry = entry;
        }

        if (lastEntry == null)
            return 1;
        return lastEntry.getKey() + 1;
    }

    @Override
    public int add(T obj) {
        int id = createNewId();
        objects.put(id, obj);
        return id;
    }

    @Override
    public T read(int id) {
        return objects.get(id);
    }

    @Override
    public void delete(int id) {
        objects.remove(id);
    }

    @Override
    public void update(int id, T obj) {
        objects.put(id, obj);
    }

    @Override
    public Map<Integer, T> getAll() {
        return objects;
    }
}
