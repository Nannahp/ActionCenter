package com.example.actionproeve.services;

import com.example.actionproeve.models.DutySchedule;
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

    public void deleteDutySchedule(Long id) {
        dutyScheduleRepository.deleteById(id);
    }


    public List<DutySchedule> findByEmployeeUsername(String username) {
        return dutyScheduleRepository.findByEmployee_Username(username);
    }

    public List<DutySchedule> getAllDuties() {
        return dutyScheduleRepository.findAll();
    }

}
