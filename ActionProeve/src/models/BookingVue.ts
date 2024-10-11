export interface BookingVue {
    id?: number;                       // Booking ID
    activityName: string;             // Name of the booked activity
    customerName: string;             // Customer's name
    email: string;                    // Customer's email
    employee: number;         // Reference to Employee object with id
    date: string;                     // Booking date in string format
    startTime: string;                // Start time in string format
    endTime: string;                  // End time in string format
}
