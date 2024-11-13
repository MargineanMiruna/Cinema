package Repo;

import java.util.List;
import java.util.Map;

/**
 * IRepository provides a generic interface for managing repository operations.
 * Defines methods for adding, reading, deleting, updating, and retrieving all objects.
 * @param <T> the type of objects managed by the repository
 */
public interface IRepository<T> {
    /**
     * Adds an object to the repository.
     * @param obj the object to be added to the repository
     * @return the unique ID assigned to the added object
     */
    int add(T obj);

    /**
     * Reads or retrieves an object from the repository by its ID.
     * @param id the unique identifier of the object to retrieve
     * @return the object associated with the specified ID, or {@code null} if no such object exists
     */
    T read(int id);

    /**
     * Deletes an object from the repository by its ID.
     * @param id the unique identifier of the object to be deleted
     */
    void delete(int id);

    /**
     * Updates an object in the repository with a new object using the specified ID.
     * @param id  the unique identifier of the object to update
     * @param obj the new object with which to update the existing object
     */
    void update(int id, T obj);

    /**
     * Retrieves all objects in the repository as a map.
     * @return a map containing all objects in the repository,
     *          with their IDs as keys and the objects as values
     */
    Map<Integer, T> getAll();
}
