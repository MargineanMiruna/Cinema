package Domain;

import java.time.LocalDate;
import java.util.List;

public class Booking {
    private int id;
    private int customerId;
    private int showtimeId;
    private LocalDate date;
    private int nrOfCustomers;
    private List<Seat> chosenSeats;

    public Booking(int id, int customerId, int bookingId, LocalDate date, int nrOfCustomers, List<Seat> chosenSeats) {
        this.id = id;
        this.customerId = customerId;
        this.showtimeId = showtimeId;
        this.date = LocalDate.now();
        this.nrOfCustomers = nrOfCustomers;
        this.chosenSeats = chosenSeats;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }



    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setNrOfCustomers(int nrOfCustomers) {
        this.nrOfCustomers = nrOfCustomers;
    }

    public void setChosenSeats(List<Seat> chosenSeats) {
        this.chosenSeats = chosenSeats;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getNrOfCustomers() {
        return nrOfCustomers;
    }

    public List<Seat> getChosenSeats() {
        return chosenSeats;
    }
}
