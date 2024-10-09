package com.example.actionproeve.services;

import com.example.actionproeve.models.DutySchedule;
import com.example.actionproeve.models.DutyScheduleDTO;
import com.example.actionproeve.repositories.DutyScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DutyScheduleService {

    @Autowired
    DutyScheduleRepository dutyScheduleRepository;

    public List<DutyScheduleDTO> getAllDutySchedules() {
        return dutyScheduleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DutyScheduleDTO> getDutySchedulesByDate(LocalDate date) {
        return dutyScheduleRepository.findByDate(date).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    //So that we can handle the logic in service and Controller can focus only on HTTP
    private DutyScheduleDTO convertToDTO(DutySchedule dutySchedule) {
        return new DutyScheduleDTO(
                dutySchedule.getId(),
                dutySchedule.getStartTime(),
                dutySchedule.getEndTime(),
                dutySchedule.getDate(),
                dutySchedule.getEmployeeName(),
                dutySchedule.getEmployeeId()
        );
    }


}
