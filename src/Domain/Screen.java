package Domain;

import java.util.ArrayList;
import java.util.List;

public class Screen {
    private int nrStandardSeats;
    private int nrVipSeats;
    private int nrPremiumSeats;
    private List<Seat> seats;


    public Screen(int nrStandardSeats, int nrVipSeats, int nrPremiumSeats) {
        this.nrStandardSeats = nrStandardSeats;
        this.nrVipSeats = nrVipSeats;
        this.nrPremiumSeats = nrPremiumSeats;

        this.seats = new ArrayList<Seat>();
        for (int i = 1; i <= nrStandardSeats; i++) {
            seats.add(new Seat(i, SeatType.standard));
        }
        for (int i = 1 + nrStandardSeats; i <= nrVipSeats + nrStandardSeats; i++) {
            seats.add(new Seat(i, SeatType.vip));
        }
        for( int i = 1 + nrStandardSeats + nrVipSeats; i <=nrPremiumSeats + nrVipSeats + nrStandardSeats; i++) {
            seats.add(new Seat(i, SeatType.premium));
        }
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

    public List<Seat> getSeats() {
        return seats;
    }
}
