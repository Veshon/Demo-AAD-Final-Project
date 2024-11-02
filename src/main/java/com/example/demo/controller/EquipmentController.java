package com.example.demo.controller;

import com.example.demo.customStatusCode.SelectedErrorStatus;
import com.example.demo.dto.CropStatus;
import com.example.demo.dto.EquipmentStatus;
import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.EquipmentDTO;
import com.example.demo.dto.impl.StaffDTO;
import com.example.demo.exception.DataPersistException;
import com.example.demo.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/equipments")

public class EquipmentController {

    @Autowired
    public EquipmentService equipmentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveEquipment(@RequestBody EquipmentDTO equipmentDTO) {
        try {
            equipmentService.saveEquipment(equipmentDTO);
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
    public List<EquipmentDTO> getAllEquipments(){
        return equipmentService.getAllEquipments();
    }

    @GetMapping(value = "/{equipmentId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public EquipmentStatus getSelectedCrop(@PathVariable ("equipmentId") String code){
        String regexEquipmentID = "^EQUIPMENT-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexEquipmentID);
        var regexMatcher = regexPattern.matcher(code);
        if (!regexMatcher.matches()) {
            return (EquipmentStatus) new SelectedErrorStatus(1,"Equipment ID is not valid");
        }
        return equipmentService.getEquipment(code);
    }

}
