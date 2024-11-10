package Repo;

import java.util.List;
import java.util.Map;

public interface IRepository<T> {
    int add(T obj);
    T read(int id);
    void delete(int id);
    void update(int id, T obj);
    Map<Integer, T> getAll();
}
