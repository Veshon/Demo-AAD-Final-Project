package com.example.demo.controller;

import com.example.demo.customStatusCode.SelectedErrorStatus;
import com.example.demo.dto.CropStatus;
import com.example.demo.dto.StaffStatus;
import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.StaffDTO;
import com.example.demo.exception.DataPersistException;
import com.example.demo.service.FieldService;
import com.example.demo.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/staff")

public class StaffController {

    @Autowired
    public StaffService staffService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveStaff(@RequestBody StaffDTO staffDTO) {
        try {
            staffService.saveStaff(staffDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StaffDTO> getAllStaff(){
        return staffService.getAllStaff();
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public StaffStatus getSelectedStaff(@PathVariable ("id") String id){
        String regexForUserID = "^STAFF-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserID);
        var regexMatcher = regexPattern.matcher(id);
        if (!regexMatcher.matches()) {
            return (StaffStatus) new SelectedErrorStatus(1,"Staff ID is not valid");
        }
        return staffService.getStaff(id);
    }
}
