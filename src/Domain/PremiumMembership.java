package Domain;

import java.time.LocalDate;
import java.util.List;

public class PremiumMembership extends Membership{
    int customerId;
    LocalDate startDate;
    LocalDate endDate;
    double price = 100;
    double discount = 20;

    /**
     *
     * @param customerId
     * @param startDate of the membership
     * @param endDate startDate +30 days
     */

    public PremiumMembership(int customerId, LocalDate startDate, LocalDate endDate) {
        super(customerId, startDate, endDate);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public double offerDiscount(double price) {
        return price - price * (discount / 100);
    }
}
