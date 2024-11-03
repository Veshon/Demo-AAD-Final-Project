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
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LogsDTO> getAllLogs(){
        return logsService.getAllLogs();
    }

    @GetMapping(value = "/{logCode}",produces = MediaType.APPLICATION_JSON_VALUE)
    public LogsStatus getSelectedLog(@PathVariable ("logCode") String code){
        String regexForLogCode = "^LOG-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForLogCode);
        var regexMatcher = regexPattern.matcher(code);
        if (!regexMatcher.matches()) {
            return (LogsStatus) new SelectedErrorStatus(1,"Log ID is not ");
        }
        return logsService.getLog(code);
    }

    @DeleteMapping(value = "/{logCode}")
    public ResponseEntity<Void> deleteCode(@PathVariable("logCode") String code){

        String regexForLogCode = "^LOG-[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
        Pattern regexPattern = Pattern.compile(regexForLogCode);
        var regexMatcher = regexPattern.matcher(code);

        try {

            if (!regexMatcher.matches()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            logsService.deleteLog(code);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (FieldNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
