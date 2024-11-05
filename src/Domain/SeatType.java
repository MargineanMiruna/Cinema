package Domain;

public enum SeatType {
    standard(30),
    vip(40),
    premium(50);

    private int price;
    SeatType(int price) {
        this.price = price;
    }
    public int getPrice() {
        return price;
    }
}
