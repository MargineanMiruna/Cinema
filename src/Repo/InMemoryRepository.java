package Repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * InMemoryRepository is an in-memory implementation of the {@link IRepository} interface.
 * Stores objects in a HashMap, providing basic CRUD operations for objects of a generic type.
 * @param <T> the type of objects managed by this repository
 */

public class InMemoryRepository<T> implements IRepository<T> {
    Map<Integer, T> objects = new HashMap<>();

    /**
     * Generates a new unique ID for an object.
     * The ID is one greater than the last entry's ID, or 1 if the repository is empty.
     * @return a unique integer ID for a new object
     */
    private int createNewId() {
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
     * @return the unique ID assigned to the added object
     */
    @Override
    public int add(T obj) {
        int id = createNewId();
        objects.put(id, obj);
        return id;
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
     * @param id  the unique identifier of the object to update
     * @param obj the new object with which to update the existing object
     */
    @Override
    public void update(int id, T obj) {
        objects.put(id, obj);
    }

    /**
     * Retrieves all objects in the repository as a map.
     * @return a map containing all objects in the repository,
     *         with their IDs as keys and the objects as values
     */
    @Override
    public Map<Integer, T> getAll() {
        return objects;
    }
}
