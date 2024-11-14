package Model;

import java.io.Serializable;

/**
 * A functional interface that represents an onject with a unique identifier
 */
@FunctionalInterface
public interface HasId extends Serializable {
    /**
     * Gets the unique identifier of the object.
     * @return The unique identifier.
     */
    public int getId();
}
