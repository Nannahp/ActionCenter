export interface Employee {
    id?: number;               // Employee ID
  name: string;             // Employee's name
  username: string;         // Employee's username
  password: string;         // Employee's password (consider security practices)
  admin: boolean;           // Indicates if the employee is an admin
}