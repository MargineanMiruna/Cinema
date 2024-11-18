package Repository;

import Model.BasicMembership;
import Model.HasId;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class FileRepository<T extends HasId> implements IRepository<T> {
    private String filePath;

    public FileRepository(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads the data from the file.
     *
     * @return The data stored in the file, or an empty map if the file is empty or does not exist.
     */
    private Map<Integer, T> readDataFromFile() {
        Map<Integer, T> objects = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                T obj = T.fromCSV(line);
                objects.put(obj.getId(), obj);
            }
            return objects;
        } catch (IOException e) {
            return new HashMap<>();
        }
    }

    /**
     * Writes the data to the file.
     *
     * @param objects The data to write to the file.
     */
    private void writeDataToFile(Map<Integer, T> objects) {
        if(objects.isEmpty()) return;

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(String.join(",", objects.get(1).getHeader()) + "\n");

            for(Map.Entry<Integer, T> entry : objects.entrySet()){
                writer.write(entry.getValue().toCSV() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error while writing to file: " + e.getMessage());
        }
    }

    /**
     * Performs an operation on the data stored in the file.
     *
     * @param function The function to apply to the data.
     */
    private void doInFile(Consumer<Map<Integer, T>> function) {
        Map<Integer, T> objects = readDataFromFile();
        function.accept(objects);
        writeDataToFile(objects);
    }

    /**
     * Generates a new unique ID for an object.
     * The ID is one greater than the last entry's ID, or 1 if the repository is empty.
     *
     * @return a unique integer ID for a new object
     */
    @Override
    public int generateNewId() {
        Map<Integer, T> objects = readDataFromFile();
        Map.Entry<Integer, T> lastEntry = null;

        for (Map.Entry<Integer, T> entry : objects.entrySet()) {
            lastEntry = entry;
        }

        if (lastEntry == null)
            return 1;
        return lastEntry.getKey() + 1;
    }

    /**
     * Adds an object to the repository.
     *
     * @param obj the object to be added to the repository
     */
    @Override
    public void add(T obj) {
        doInFile(objects -> objects.put(obj.getId(), obj));
    }

    /**
     * Reads or retrieves an object from the repository by its ID.
     *
     * @param id the unique identifier of the object to retrieve
     * @return the object associated with the specified ID, or {@code null} if no such object exists
     */
    @Override
    public T read(int id) {
        return readDataFromFile().get(id);
    }

    /**
     * Deletes an object from the repository by its ID.
     *
     * @param id the unique identifier of the object to be deleted
     */
    @Override
    public void delete(int id) {
        doInFile(objects -> objects.remove(id));
    }

    /**
     * Updates an object in the repository with a new object using the specified ID.
     *
     * @param obj the new object with which to update the existing object
     */
    @Override
    public void update(T obj) {
        doInFile(objects -> objects.put(obj.getId(), obj));
    }

    /**
     * Retrieves all objects in the repository as a map.
     *
     * @return a map containing all objects in the repository,
     * with their IDs as keys and the objects as values
     */
    @Override
    public Map<Integer, T> getAll() {
        return readDataFromFile();
    }
}
