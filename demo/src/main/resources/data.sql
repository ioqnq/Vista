DELETE FROM properties;
DELETE FROM bookings;

INSERT INTO properties (
    id, name, location, price_per_night, rating, image_url,
    max_guests, property_type, breakfast_included, allows_pets,
    parking_space, description, host_email
)
VALUES
(1, 'Cosy loft in city center', 'Rome', 200.00, 4.8, '/images/property-1.jpg', 4, 'Apartment', true, true, false, 'A cozy apartment in central Rome.', 'ana.host@example.com'),
(2, 'Classic double apartment', 'Rome', 200.00, 4.7, '/images/property-2.jpg', 4, 'Apartment', false, false, true, 'Comfortable apartment close to the main attractions.', 'ana.host@example.com'),
(3, 'Authentic Villa Romana', 'Rome', 500.00, 4.9, '/images/property-3.jpg', 8, 'Villa', true, true, true, 'Large villa ideal for families and groups.', 'ana.host@example.com'),
(4, '2 bedroom apartment', 'Rome', 217.00, 4.6, '/images/property-4.jpg', 5, 'Apartment', false, false, true, 'Two-bedroom apartment with modern amenities.', 'ana.host@example.com');