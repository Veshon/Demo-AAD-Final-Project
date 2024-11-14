package com.example.demo.controller;

import com.example.demo.customStatusCode.SelectedErrorStatus;
import com.example.demo.dto.CropStatus;
import com.example.demo.dto.StaffStatus;
import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.StaffDTO;
import com.example.demo.exception.DataPersistException;
import com.example.demo.exception.FieldNotFoundException;
import com.example.demo.service.FieldService;
import com.example.demo.service.StaffService;
import com.example.demo.util.AppUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/staff")

public class StaffController {
    static Logger logger = LoggerFactory.getLogger(StaffController.class);

    @Autowired
    private StaffService staffService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveStaff(@RequestBody StaffDTO staffDTO) {
        // Define the regex pattern for email validation
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(emailRegex);

        // Validate email format
        if (staffDTO.getEmail() == null || !pattern.matcher(staffDTO.getEmail()).matches()) {
            logger.info("Invalid email format.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            staffService.saveStaff(staffDTO);
            logger.info("Staff POST method executed.");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            logger.info("Staff POST method not executed.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Staff POST method not executed.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StaffDTO> getAllStaff(){
        logger.info("Staff GET_ALL method executed.");
        return staffService.getAllStaff();
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public StaffStatus getSelectedStaff(@PathVariable ("id") String id){
        String regexForUserID = "^STAFF-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserID);
        var regexMatcher = regexPattern.matcher(id);
        if (!regexMatcher.matches()) {
            logger.info("Staff GET method not executed.");
            return (StaffStatus) new SelectedErrorStatus(1,"Staff ID is not valid");
        }
        logger.info("Staff GET method executed.");
        return staffService.getStaff(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteId(@PathVariable("id") String id){

        String regexForUserId = "^STAFF-[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserId);
        var regexMatcher = regexPattern.matcher(id);

        try {

            if (!regexMatcher.matches()){
                logger.info("Staff DELETE method not executed.");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            staffService.deleteStaff(id);
            logger.info("Staff DELETE method executed.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (FieldNotFoundException e){
            e.printStackTrace();
            logger.info("Staff DELETE method not executed.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("Staff DELETE method not executed.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStaff(@PathVariable("id") String id, @RequestBody StaffDTO updatedStaffDTO) {
        // Define the regex pattern for email validation
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(emailRegex);

        // Validate the staff ID format (existing validation)
        String regexForUserID = "^STAFF-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern idPattern = Pattern.compile(regexForUserID);
        var idMatcher = idPattern.matcher(id);

        // Validate the email format in updatedStaffDTO
        if (!idMatcher.matches() || updatedStaffDTO == null ||
                updatedStaffDTO.getEmail() == null || !pattern.matcher(updatedStaffDTO.getEmail()).matches()) {
            logger.info("Invalid email format.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            staffService.updateStaff(id, updatedStaffDTO);
            logger.info("Staff UPDATE method executed.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (FieldNotFoundException e) {
            e.printStackTrace();
            logger.info("Staff UPDATE method not executed.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Staff UPDATE method not executed.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
