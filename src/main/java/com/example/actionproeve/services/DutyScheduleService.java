package com.example.actionproeve.services;

import com.example.actionproeve.models.DutySchedule;
import com.example.actionproeve.models.Employee;
import com.example.actionproeve.repositories.DutyScheduleRepository;
import com.example.actionproeve.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DutyScheduleService {

    @Autowired
    DutyScheduleRepository dutyScheduleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public List<DutySchedule> getDutySchedulesByDate(LocalDate date) {
        return dutyScheduleRepository.findByDate(date);
    }

    public DutySchedule saveDutySchedule(DutySchedule dutySchedule) {
        return dutyScheduleRepository.save(dutySchedule);
    }

    private String getEmployeeNameById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        return employee != null ? employee.getName() : null; // Return employee name or null if not found
    }


}
