package ua.edu.ukma.samsoniuk;

import java.util.List;

public class BookingService {

    private final SeatRepository seatRepository;
    private final PaymentService paymentService;
    private final NotificationService notificationService;

    private static final double TICKET_PRICE = 150.0;
    private static Long bookingIdCounter = 1L;

    public BookingService(SeatRepository seatRepository, PaymentService paymentService, NotificationService notificationService) {
        this.seatRepository = seatRepository;
        this.paymentService = paymentService;
        this.notificationService = notificationService;
    }

    public Booking bookTicket(String seatNumber, String customerName, String customerEmail, String cardToken) {
        if (!seatRepository.isSeatAvailable(seatNumber)) {
            throw new IllegalStateException("Seat " + seatNumber + " is already booked");
        }

        String transactionId = paymentService.processPayment(TICKET_PRICE, cardToken);
        if (transactionId == null) {
            throw new IllegalStateException("Payment failed. Try with a different card");
        }

        Booking booking = new Booking(
                bookingIdCounter++,
                customerName,
                customerEmail,
                seatNumber,
                TICKET_PRICE,
                "CONFIRMED"
        );

        seatRepository.saveBooking(booking);
        notificationService.sendBookingConfirmation(customerEmail, customerName, seatNumber);
        return booking;
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = seatRepository.findById(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking with ID " + bookingId + " not found");
        }

        paymentService.refundPayment("T" + bookingId);
        seatRepository.cancelBooking(booking.getSeatNumber());
        notificationService.sendCancellationConfirmation(
                booking.getCustomerEmail(),
                booking.getCustomerName(),
                booking.getSeatNumber()
        );
    }


    public List<Booking> getBookingsByCustomer(String email) {
        return seatRepository.findByCustomerEmail(email);
    }

    public List<String> getAvailableSeats() {
        return seatRepository.getAvailableSeats();
    }

    public double getDiscount(double price) {
        if (price < 200) {
            return 0.05;
        } else if (price < 500) {
            return 0.1;
        } else {
            return 0.2;
        }
    }
}