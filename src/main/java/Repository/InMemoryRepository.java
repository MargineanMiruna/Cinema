package Repository;

import Model.HasId;

import java.util.HashMap;
import java.util.Map;

/**
 * The InMemoryRepository class is an in-memory implementation of the {@link IRepository} interface.
 * Stores objects in a HashMap, providing basic CRUD operations for objects of a generic type.
 * @param <T> the type of objects managed by this repository
 */
public class InMemoryRepository<T extends HasId> implements IRepository<T> {
    private final Map<Integer, T> objects = new HashMap<>();

    /**
     * Generates a new unique ID for an object.
     * The ID is one greater than the last entry's ID, or 1 if the repository is empty.
     * @return a unique integer ID for a new object
     */
    public int generateNewId() {
        Map.Entry<Integer, T> lastEntry = null;

        for (Map.Entry<Integer, T> entry : objects.entrySet()) {
            lastEntry = entry;
        }

        if (lastEntry == null)
            return 1;
        return lastEntry.getKey() + 1;
    }

    /**
     * Adds an object to the repository, assigning it a new unique ID.
     * @param obj the object to be added to the repository
     */
    @Override
    public void add(T obj) {
        objects.put(obj.getId(), obj);
    }

    /**
     * Reads or retrieves an object from the repository by its ID.
     * @param id the unique identifier of the object to retrieve
     * @return the object associated with the specified ID, or {@code null} if no such object exists
     */
    @Override
    public T read(int id) {
        return objects.get(id);
    }

    /**
     * Deletes an object from the repository by its ID.
     * @param id the unique identifier of the object to be deleted
     */
    @Override
    public void delete(int id) {
        objects.remove(id);
    }

    /**
     * Updates an object in the repository with a new object using the specified ID.
     * @param obj the new object with which to update the existing object
     */
    @Override
    public void update(T obj) {
        objects.replace(obj.getId(), obj);
    }

    /**
     * Retrieves all objects in the repository as a map.
     * @return a map containing all objects in the repository, with their IDs as keys and the objects as values
     */
    @Override
    public Map<Integer, T> getAll() {
        return objects;
    }
}
