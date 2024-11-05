package Repo;

import java.util.List;

public interface IRepository<T> {
    void add(T obj);
    T read(int id);
    void delete(int id);
    void update(int id, T obj);
    List<T> getAll();
}
