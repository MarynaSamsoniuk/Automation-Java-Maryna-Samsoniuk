package ua.edu.ukma.samsoniuk;

public interface PaymentService {
    String processPayment(double amount, String card);

    void refundPayment(String transactionId);
}
