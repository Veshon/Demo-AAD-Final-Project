package com.example.demo.controller;

import com.example.demo.customStatusCode.SelectedErrorStatus;
import com.example.demo.dto.CropStatus;
import com.example.demo.dto.VehicleStatus;
import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.VehicleDTO;
import com.example.demo.exception.DataPersistException;
import com.example.demo.exception.FieldNotFoundException;
import com.example.demo.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/vehicles")

public class VehicleController {
    static Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private VehicleService vehicleService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveVehicle(@RequestBody VehicleDTO vehicleDTO) {
        try {
            vehicleService.saveVehicle(vehicleDTO);
            logger.info("Vehicle POST method executed.");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            logger.info("Vehicle POST method not executed.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("Vehicle POST method not executed.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehicleDTO> getAllVehicles(){
        logger.info("Vehicle GTE_ALL method executed.");
        return vehicleService.getAllVehicles();
    }

    @GetMapping(value = "/{vehicleCode}",produces = MediaType.APPLICATION_JSON_VALUE)
    public VehicleStatus getSelectedVehicle(@PathVariable ("vehicleCode") String code){
        String regexForUserID = "^VEHICLE-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserID);
        var regexMatcher = regexPattern.matcher(code);
        if (!regexMatcher.matches()) {
            logger.info("Vehicle GTE method not executed.");
            return (VehicleStatus) new SelectedErrorStatus(1,"Vehicle code is not valid");
        }
        logger.info("Vehicle GTE method executed.");
        return vehicleService.getVehicle(code);
    }

    @DeleteMapping(value = "/{vehicleCode}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("vehicleCode") String code){

        String regexForVehicleCode = "^VEHICLE-[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
        Pattern regexPattern = Pattern.compile(regexForVehicleCode);
        var regexMatcher = regexPattern.matcher(code);

        try {

            if (!regexMatcher.matches()){
                logger.info("Vehicle DELETE method not executed.");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            vehicleService.deleteVehicle(code);
            logger.info("Vehicle DELETE method executed.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (FieldNotFoundException e){
            e.printStackTrace();
            logger.info("Vehicle DELETE method not executed.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("Vehicle DELETE method not executed.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping(value = "/{vehicleCode}")
    public ResponseEntity<Void> updateStaff(@PathVariable ("vehicleCode") String code,
                                            @RequestBody VehicleDTO updatedVehicleDTO){
        String regexForVehicleCode = "^VEHICLE-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForVehicleCode);
        var regexMatcher = regexPattern.matcher(code);
        try {
            if(!regexMatcher.matches() || updatedVehicleDTO == null){
                logger.info("Vehicle UPDATE method not executed.");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            vehicleService.updateVehicle(code,updatedVehicleDTO);
            logger.info("Vehicle UPDATE method executed.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (FieldNotFoundException e){
            e.printStackTrace();
            logger.info("Vehicle UPDATE method not executed.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("Vehicle UPDATE method not executed.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
