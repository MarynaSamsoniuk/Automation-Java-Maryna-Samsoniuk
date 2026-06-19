package ua.edu.ukma.samsoniuk;

public interface NotificationService {
    void sendBookingConfirmation(String email, String customerName, String seatNumber);

    void sendCancellationConfirmation(String email, String customerName, String seatNumber);
}