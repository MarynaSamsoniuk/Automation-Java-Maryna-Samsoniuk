package ua.edu.ukma.samsoniuk;

public class Booking {
    private final Long id;
    private final String customerName;
    private final String customerEmail;
    private final String seatNumber;
    private final double price;
    private final String status;

    public Booking(Long id, String customerName, String customerEmail, String seatNumber, double price, String status) {
        this.id = id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.seatNumber = seatNumber;
        this.price = price;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }
}
