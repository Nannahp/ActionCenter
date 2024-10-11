export interface DutySchedule {
    id: number;           // Assuming id is a number
    startTime: string;                // Start time in string format
    endTime: string;                  // End time in string format
    date: string;                     // Booking date in string format
    employee: { id: number; username: String };         // Reference to Employee object with id
    employeeName: string; // Include employee's name here
    employeeUsername: string; // Include employee's name here

}