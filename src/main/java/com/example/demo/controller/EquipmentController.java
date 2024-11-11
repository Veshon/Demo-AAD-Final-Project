package com.example.demo.controller;

import com.example.demo.customStatusCode.SelectedErrorStatus;
import com.example.demo.dto.CropStatus;
import com.example.demo.dto.EquipmentStatus;
import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.EquipmentDTO;
import com.example.demo.dto.impl.StaffDTO;
import com.example.demo.exception.DataPersistException;
import com.example.demo.exception.FieldNotFoundException;
import com.example.demo.service.EquipmentService;
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
@RequestMapping("api/v1/equipments")

public class EquipmentController {
    static Logger logger = LoggerFactory.getLogger(EquipmentController.class);

    @Autowired
    public EquipmentService equipmentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveEquipment(@RequestBody EquipmentDTO equipmentDTO) {
        try {
            equipmentService.saveEquipment(equipmentDTO);
            logger.info("Equipment POST method executed.");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            logger.info("Equipment POST method not executed. BAD_REQUEST");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("Equipment POST method not executed. SERVER_ERROR");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EquipmentDTO> getAllEquipments(){
        logger.info("Equipment GET_ALL method executed.");
        return equipmentService.getAllEquipments();
    }

    @GetMapping(value = "/{equipmentId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public EquipmentStatus getSelectedCrop(@PathVariable ("equipmentId") String code){
        String regexEquipmentID = "^EQUIPMENT-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexEquipmentID);
        var regexMatcher = regexPattern.matcher(code);
        if (!regexMatcher.matches()) {
            logger.info("Equipment GET method not executed.");
            return (EquipmentStatus) new SelectedErrorStatus(1,"Equipment ID is not valid");
        }
        logger.info("Equipment GET method executed.");
        return equipmentService.getEquipment(code);
    }

    @DeleteMapping(value = "/{equipmentId}")
    public ResponseEntity<Void> deleteCode(@PathVariable("equipmentId") String code){

        String regexForUserId = "^EQUIPMENT-[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserId);
        var regexMatcher = regexPattern.matcher(code);

        try {

            if (!regexMatcher.matches()){
                logger.info("Equipment DELETE method not executed.");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            equipmentService.deleteEquipment(code);
            logger.info("Equipment DELETE method executed.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (FieldNotFoundException e){
            e.printStackTrace();
            logger.info("Equipment DELETE method not executed.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("Equipment DELETE method not executed.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping(value = "/{equipmentId}")
    public ResponseEntity<Void> updateEquipment(@PathVariable ("equipmentId") String id,
                                            @RequestBody EquipmentDTO updatedEquipmentDTO){
        String regexForEquipmentID = "^EQUIPMENT-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForEquipmentID);
        var regexMatcher = regexPattern.matcher(id);
        try {
            if(!regexMatcher.matches() || updatedEquipmentDTO == null){
                logger.info("Equipment UPDATE method not executed.");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            equipmentService.updateEquipment(id,updatedEquipmentDTO);
            logger.info("Equipment UPDATE method executed.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (FieldNotFoundException e){
            e.printStackTrace();
            logger.info("Equipment UPDATE method not executed.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("Equipment UPDATE method not executed.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
