// src/services/BookingService.ts
import axios from 'axios';
import type { Booking } from '../models/Booking';

const API_URL = 'http://localhost:8080/api/bookings';

const BookingService = {
  // Fetch all bookings
  async getAllBookings(): Promise<Booking[]> {
    const response = await axios.get(`${API_URL}`);
    return response.data; // Expecting response.data to be an array of Booking objects
  },

  // Create a hardcoded booking
  async createHardcodedBooking(): Promise<Booking> {
    // Hardcoded booking data
    const hardcodedBooking: Booking = {
        activityName: 'Yoga Class',
        customerName: 'Jane Smith',
        email: 'jane.smith@example.com',
        date: new Date().toISOString().split('T')[0], // Current date in YYYY-MM-DD format
        startTime: '10:00:00', // Start time
        endTime: '11:00:00', // End time
        employee: { id: 1 } // Use a valid employee id as an object reference
        ,
        data: ''
    };

    const response = await axios.post(`${API_URL}/create-booking/hardcoded`, hardcodedBooking);

    return response.data; // Return the created Booking object from the server
  },
};

export default BookingService;
