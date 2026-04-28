package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.Booking;

@Repository
public class BookingMockRepository {

    private final List<Booking> bookings = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public BookingMockRepository() {
        bookings.add(new Booking(
                idGenerator.getAndIncrement(),
                3L,
                "Authentic Villa Romana",
                "Corina",
                "Popescu",
                "corina@email.com",
                "+40 700 000 000",
                "12 Sept 2026",
                "17 Sept 2026",
                4,
                2500,
                "Confirmed"
        ));
    }

    public List<Booking> findAll() {
        return new ArrayList<>(bookings);
    }

    public Booking save(Booking booking) {
        booking.setId(idGenerator.getAndIncrement());
        bookings.add(booking);
        return booking;
    }
}

