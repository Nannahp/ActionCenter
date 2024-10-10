package com.example.actionproeve.repositories;

import com.example.actionproeve.models.DutySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DutyScheduleRepository extends JpaRepository<DutySchedule, Long> {
    List<DutySchedule> findByDate(LocalDate date);

    //Can be used if we implement search after
    //List<DutySchedule> findByEmployeeId(Long employeeId);
    //List<DutySchedule> findByDate(String date);


}
