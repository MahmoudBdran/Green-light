package com.erp.greenlight.greenlightWorker.service;

import com.erp.greenlight.greenlightWorker.models.Salary;
import com.erp.greenlight.greenlightWorker.respository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    public Salary saveSalary(Salary salary) {
        if(!salary.getIsPaid()){
            salary.setDeduction(BigDecimal.ZERO);
        }
        return salaryRepository.save(salary);
    }

    public Map<String,Object> saveListOfSalaries(List <Salary> salaries) {
        List<LocalDate> datesWhichIsNotSavedInSalary = new ArrayList<>();

        for(Salary salary : salaries){
//            if(!salary.getIsPaid()){
//                salary.setDeduction(BigDecimal.ZERO);
//            }
            if(checkSalaryDuplication(salary)){


                datesWhichIsNotSavedInSalary.add(salary.getSalaryDate());
                System.out.println(datesWhichIsNotSavedInSalary);
                System.out.println(datesWhichIsNotSavedInSalary.size());
            }else{
                System.out.println(salaryRepository.save(salary));

                System.out.println("Salary saved for date: " + salary.getSalaryDate());

            }
        }
        Map<String,Object> result = new HashMap<>();
        result.put("status", datesWhichIsNotSavedInSalary.isEmpty());
        result.put("DatesNotSaved",datesWhichIsNotSavedInSalary);
        return result;

    }
    public boolean checkSalaryDuplication(Salary salary){
     List<Salary> salariesFromDBForThisWorker = salaryRepository.findByWorkerIdOrderBySalaryDateDesc(salary.getWorker().getId());
     for(Salary salaryFromDb : salariesFromDBForThisWorker){
         if(salaryFromDb.getSalaryDate().equals(salary.getSalaryDate())){
             return true;
         }
     }

     return false;
    }

    public Optional<Salary> findSalaryById(Long id) {
        return salaryRepository.findById(id);
    }

    public List<Salary> findSalariesByWorkerId(Long workerId) {
        return salaryRepository.findByWorkerIdOrderBySalaryDateDesc(workerId);
    }

    public List<Salary> findSalariesByProjectId(Long projectId) {
        return salaryRepository.findByProjectId(projectId);
    }
}

