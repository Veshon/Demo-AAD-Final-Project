package com.example.demo.controller;

import com.example.demo.customStatusCode.SelectedErrorStatus;
import com.example.demo.dto.CropStatus;
import com.example.demo.dto.LogsStatus;
import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.LogsDTO;
import com.example.demo.exception.DataPersistException;
import com.example.demo.exception.FieldNotFoundException;
import com.example.demo.service.CropService;
import com.example.demo.service.LogsService;
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
@RequestMapping("api/v1/logs")

public class LogsController {
    static Logger logger = LoggerFactory.getLogger(LogsController.class);

    @Autowired
    private LogsService logsService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveLog(
            @RequestPart("logDate") String logDate,
            @RequestPart("logDetails") String logDetails,
            @RequestPart("fieldCode") String fieldCode,
            @RequestPart("cropCode") String cropCode,
            @RequestPart("staffId") String staffId,
            @RequestPart("observedImage") MultipartFile profilePic){

        // profilePic ----> Base64
        String base64ProPic = "";

        try {
            byte [] bytesProPic = profilePic.getBytes(); //Converting profile pic to byte array
            base64ProPic = AppUtil.profilePicToBase64(bytesProPic);
            String code = AppUtil.generateLogCode(); //Generating UUID

            var buildLogDTO = new LogsDTO(); //Creating obj
            buildLogDTO.setLogCode(code);
            buildLogDTO.setLogDate(logDate);
            buildLogDTO.setLogDetails(logDetails);
            buildLogDTO.setFieldCode(fieldCode);
            buildLogDTO.setCropCode(cropCode);
            buildLogDTO.setStaffId(staffId);
            buildLogDTO.setObservedImage(base64ProPic);

            logsService.saveLog(buildLogDTO);
            logger.info("Logs POST method executed.");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            logger.info("Logs POST method not executed.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.info("Logs POST method not executed.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LogsDTO> getAllLogs(){
        logger.info("Logs GET_ALL method executed.");
        return logsService.getAllLogs();
    }

    @GetMapping(value = "/{logCode}",produces = MediaType.APPLICATION_JSON_VALUE)
    public LogsStatus getSelectedLog(@PathVariable ("logCode") String code){
        String regexForLogCode = "^LOG-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForLogCode);
        var regexMatcher = regexPattern.matcher(code);
        if (!regexMatcher.matches()) {
            logger.info("Logs GET method not executed.");
            return (LogsStatus) new SelectedErrorStatus(1,"Log ID is not ");
        }
        logger.info("Logs GET method executed.");
        return logsService.getLog(code);
    }

    @DeleteMapping(value = "/{logCode}")
    public ResponseEntity<Void> deleteCode(@PathVariable("logCode") String code){

        String regexForLogCode = "^LOG-[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
        Pattern regexPattern = Pattern.compile(regexForLogCode);
        var regexMatcher = regexPattern.matcher(code);

        try {

            if (!regexMatcher.matches()){
                logger.info("Logs DELETE method not executed.");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            logsService.deleteLog(code);
            logger.info("Logs DELETE method executed.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (FieldNotFoundException e){
            e.printStackTrace();
            logger.info("Logs DELETE method not executed.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("Logs DELETE method not executed.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{logCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateLog(
            @RequestPart("logDate") String logDate,
            @RequestPart("logDetails") String logDetails,
            @RequestPart("fieldCode") String fieldCode,
            @RequestPart("cropCode") String cropCode,
            @RequestPart("logDetails") String staffId,
            @RequestPart("observedImage") MultipartFile observedImage,
            @PathVariable ("logCode") String logCode
    ){
        // profilePic ----> Base64
        String base64ProPic = "";

        try {
            byte [] bytesProPic = observedImage.getBytes();
            base64ProPic = AppUtil.profilePicToBase64(bytesProPic);
        }catch (Exception e){
            e.printStackTrace();
        }

        //Build the Object
        LogsDTO buildLogDTO = new LogsDTO();
        buildLogDTO.setLogCode(logCode);
        buildLogDTO.setLogDate(logDate);
        buildLogDTO.setLogDetails(logDetails);
        buildLogDTO.setObservedImage(base64ProPic);
//        buildLogDTO.setFieldCode(fieldCode);
//        buildLogDTO.setCropCode(cropCode);
//        buildLogDTO.setStaffId(staffId);
        logsService.updateLog(logCode,buildLogDTO);
        logger.info("Logs UPDATE method not executed.");
    }

}
