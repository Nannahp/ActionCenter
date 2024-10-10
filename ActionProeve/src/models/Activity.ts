export interface Activity {
    activityName: string;         // Assuming name is a string
    information: string;  // Assuming description is a string
    image: string;   // Assuming image_url can be a string and may be optional
    durations: string;        // Adjust the type of times as necessary
    minPeople: number;
    maxPeople: number;
    price: number;
  }
