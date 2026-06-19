package ua.edu.ukma.samsoniuk;

import java.util.List;

public interface SeatRepository {
    boolean isSeatAvailable(String seatNumber);

    void saveBooking(Booking booking);

    void cancelBooking(String seatNumber);

    Booking findById(Long bookingId);

    List<Booking> findByCustomerEmail(String customerEmail);

    List<String> getAvailableSeats();
}
