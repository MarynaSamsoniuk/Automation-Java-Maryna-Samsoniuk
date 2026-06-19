package ua.edu.ukma.samsoniuk;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTests {
    @Mock
    private SeatRepository seatRepository;

    @Mock
    private PaymentService paymentService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void bookingSuccessWhenSeatAvailableAndPaymentSuccess() {
        String seatNumber = "Balcony1";
        String customerName = "Oksana";
        String email = "oksana@gmail.com";
        String card = "card_123";

        when(seatRepository.isSeatAvailable(seatNumber)).thenReturn(true);
        when(paymentService.processPayment(150.0, card)).thenReturn("001");

        Booking booking = bookingService.bookTicket(seatNumber, customerName, email, card);

        assertThat(booking).isNotNull();
    }

    @Test
    void throwExceptionWhenSeatNotAvailable() {
        String seatNumber = "Balcony1";

        when(seatRepository.isSeatAvailable(seatNumber)).thenReturn(false);

        assertThatThrownBy(() -> bookingService.bookTicket(seatNumber, "Oksana", "oksana@gmail.com", "card_123"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Seat " + seatNumber + " is already booked");
    }

    @Test
    void throwExceptionWhenPaymentFails() {
        String seatNumber = "Balcony1";

        when(seatRepository.isSeatAvailable(seatNumber)).thenReturn(true);
        when(paymentService.processPayment(150.0, "card_123")).thenReturn(null);

        assertThatThrownBy(() ->
                bookingService.bookTicket(seatNumber, "Oksana", "oksana@gmail.com", "card_123"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Payment failed. Try with a different card");
    }

    @Test
    void callAllVoidMethodsWhenCancelBooking() {
        Long id = 1L;
        String seatNumber = "Balcony1";
        String customerName = "Oksana";
        String email = "oksana@gmail.com";

        Booking booking = new Booking(id, customerName, email, seatNumber, 150, "CONFIRMED");

        when(seatRepository.findById(id)).thenReturn(booking);

        bookingService.cancelBooking(id);

        verify(seatRepository).findById(id);
        verify(paymentService).refundPayment("T1");
        verify(seatRepository).cancelBooking(seatNumber);
        verify(notificationService).sendCancellationConfirmation(email, customerName, seatNumber);
    }

    @Test
    void callAllVoidMethodsOnlyOnceWhenCancelBooking() {
        Long id = 1L;
        String seatNumber = "Balcony1";
        String customerName = "Oksana";
        String email = "oksana@gmail.com";

        Booking booking = new Booking(id, customerName, email, seatNumber, 150, "CONFIRMED");

        when(seatRepository.findById(id)).thenReturn(booking);

        bookingService.cancelBooking(id);

        verify(seatRepository, times(1)).findById(id);
        verify(paymentService, times(1)).refundPayment("T1");
        verify(seatRepository, times(1)).cancelBooking(seatNumber);
        verify(notificationService, times(1)).sendCancellationConfirmation(email, customerName, seatNumber);
        verifyNoMoreInteractions(paymentService, seatRepository, notificationService);
    }

    @Test
    void neverCallVoidMethodsWhenBookingNotFound() {
        Long id = 1L;

        when(seatRepository.findById(id)).thenReturn(null);

        assertThatThrownBy(() ->
                bookingService.cancelBooking(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Booking with ID " + id + " not found");

        verify(paymentService, never()).refundPayment(anyString());
        verify(seatRepository, never()).cancelBooking(anyString());
        verify(notificationService, never()).sendCancellationConfirmation(anyString(), anyString(), anyString());
    }

    @Test
    void allBookingFieldsCorrectWhenBookingSuccess() {
        String seatNumber = "Balcony1";
        String customerName = "Oksana";
        String email = "oksana@gmail.com";
        String card = "card_123";

        when(seatRepository.isSeatAvailable(seatNumber)).thenReturn(true);
        when(paymentService.processPayment(150.0, card)).thenReturn("T1");

        Booking booking = bookingService.bookTicket(seatNumber, customerName, email, card);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(booking).isNotNull();
        softly.assertThat(booking.getId()).isNotNull();
        softly.assertThat(booking.getCustomerName()).isEqualTo(customerName);
        softly.assertThat(booking.getCustomerEmail()).isEqualTo(email);
        softly.assertThat(booking.getSeatNumber()).isEqualTo(seatNumber);
        softly.assertThat(booking.getPrice()).isEqualTo(150.0);
        softly.assertThat(booking.getStatus()).isEqualTo("CONFIRMED");
        softly.assertAll();
    }

    @Test
    void checkFilterAvailableSeats() {
        List<String> seats = List.of("Balcony1", "Stalls2", "Amphitheatre3", "Balcony4", "Balcony5");
        when(seatRepository.getAvailableSeats()).thenReturn(seats);
        List<String> availableSeats = bookingService.getAvailableSeats();
        assertThat(availableSeats)
                .filteredOn(seat -> seat.startsWith("Balcony"))
                .containsExactly("Balcony1", "Balcony4", "Balcony5");
    }

    @Test
    void checkBookingsForCustomer() {
        String email = "oksana@gmail.com";
        List<Booking> bookings = List.of(
                new Booking(1L, "Oksana", email, "Balcony1", 150.0, "CONFIRMED"),
                new Booking(2L, "Oksana", email, "Stalls2", 150.0, "CANCELLED"),
                new Booking(3L, "Oksana", email, "Amphitheatre3", 150.0, "CONFIRMED")
        );

        when(seatRepository.findByCustomerEmail(email)).thenReturn(bookings);

        List<Booking> customerBookings = bookingService.getBookingsByCustomer(email);

        assertThat(customerBookings)
                .hasSize(3)
                .extracting(Booking::getStatus)
                .containsExactlyInAnyOrder("CONFIRMED", "CONFIRMED", "CANCELLED");
    }

    @Test
    void badTestForPitMutation() {
        double discount = bookingService.getDiscount(300);
        assertThat(discount).isEqualTo(0.1);
    }

    @Test
    void goodTestForPitMutation() {
        assertThat(bookingService.getDiscount(100)).isEqualTo(0.05);
        assertThat(bookingService.getDiscount(199)).isEqualTo(0.05);

        assertThat(bookingService.getDiscount(200)).isEqualTo(0.1);
        assertThat(bookingService.getDiscount(300)).isEqualTo(0.1);
        assertThat(bookingService.getDiscount(499)).isEqualTo(0.1);

        assertThat(bookingService.getDiscount(500)).isEqualTo(0.2);
        assertThat(bookingService.getDiscount(700)).isEqualTo(0.2);
        assertThat(bookingService.getDiscount(999)).isEqualTo(0.2);
    }
}
