package Model;

/**
 *  The seat type enum represents different types of seats available, each with an associated price.
 *  The available seat types are:
 *      standard - Standard seat with a base price.
 *      VIP - IP seat with a higher price.
 *      premium - Premium seat with the highest price.
 */
public enum SeatType {
    standard(30),
    vip(40),
    premium(50);

    private int price;

    /**
     * Constructs a SeatType with the specified price.
     *
     * @param price the price associated with the seat type.
     */
    SeatType(int price) {
        this.price = price;
    }

    /**
     * Gets the price of the seat type.
     *
     * @return the price as an integer.
     */
    public int getPrice() {
        return price;
    }
}
