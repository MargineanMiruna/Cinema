package Domain;

import java.util.ArrayList;
import java.util.List;

public class Screen {
    private int id;
    private int nrStandardSeats;
    private int nrVipSeats;
    private int nrPremiumSeats;
    private List<Seat> seats;


    public Screen(int id, int nrStandardSeats, int nrVipSeats, int nrPremiumSeats) {
        this.id = id;
        this.nrStandardSeats = nrStandardSeats;
        this.nrVipSeats = nrVipSeats;
        this.nrPremiumSeats = nrPremiumSeats;

        this.seats = new ArrayList<Seat>();
        int seatid = 0;
        for (int i = 1; i <= nrStandardSeats; i++) {
            seats.add(new Seat(seatid,i,false, SeatType.standard));
            seatid ++;
        }
        for (int i = 1; i <= nrVipSeats ; i++) {
            seats.add(new Seat(seatid,i,false, SeatType.vip));
            seatid ++;
        }
        for( int i = 1; i <=nrPremiumSeats; i++) {
            seats.add(new Seat(seatid,i,false, SeatType.premium));
            seatid ++;
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getNrStandardSeats() {
        return nrStandardSeats;
    }

    public int getNrVipSeats() {
        return nrVipSeats;
    }

    public int getNrPremiumSeats() {
        return nrPremiumSeats;
    }

    public void setNrVipSeats(int nrVipSeats) {
        this.nrVipSeats = nrVipSeats;
    }

    public void setNrStandardSeats(int nrStandardSeats) {
        this.nrStandardSeats = nrStandardSeats;
    }

    public void setNrPremiumSeats(int nrPremiumSeats) {
        this.nrPremiumSeats = nrPremiumSeats;
    }
}
