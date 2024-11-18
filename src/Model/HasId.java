package Model;

import java.io.Serializable;

/**
 * A functional interface that represents an onject with a unique identifier
 */
public interface HasId extends Serializable {
    /**
     * Gets the unique identifier of the object.
     * @return The unique identifier.
     */
    public int getId();
    public String[] getHeader();
    public String toCSV();
    //public static <T> T fromCSV(String csvLine);
}
