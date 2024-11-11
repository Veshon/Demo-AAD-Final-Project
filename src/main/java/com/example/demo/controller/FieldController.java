package com.example.demo.controller;

import com.example.demo.customStatusCode.SelectedFieldErrorStatus;
import com.example.demo.dto.FieldStatus;
import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.FieldDTO;
import com.example.demo.dto.impl.FieldDTO;
import com.example.demo.exception.DataPersistException;
import com.example.demo.exception.FieldNotFoundException;
import com.example.demo.service.FieldService;
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
@RequestMapping("api/v1/fields")

public class FieldController {
    static Logger logger = LoggerFactory.getLogger(FieldController.class);

    @Autowired
    private FieldService fieldService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveField(
            @RequestPart("fieldName") String fieldName,
            @RequestPart("fieldLocation") String fieldLocation,
            @RequestPart("extentSize") String extentSize,
            @RequestPart("fieldImage1") MultipartFile fieldImage1,
            @RequestPart("fieldImage2") MultipartFile fieldImage2){

        // profilePic ----> Base64
        String base64ProPic = "";
        String base64ProPic2 = "";

        try {
            byte [] bytesFieldImg = fieldImage1.getBytes(); //Converting profile pic to byte array
            byte [] bytesFieldImg2 = fieldImage2.getBytes(); //Converting profile pic to byte array
            base64ProPic = AppUtil.profilePicToBase64(bytesFieldImg);
            base64ProPic2 = AppUtil.profilePicToBase64(bytesFieldImg2);
            String fieldCode = AppUtil.generateFieldCode(); //Generating UUID

            var buildFieldDTO = new FieldDTO(); //Creating obj
            buildFieldDTO.setFieldCode(fieldCode);
            buildFieldDTO.setFieldName(fieldName);
            buildFieldDTO.setFieldLocation(fieldLocation);
            buildFieldDTO.setExtentSize(Double.valueOf(extentSize));
            buildFieldDTO.setFieldImage1(base64ProPic);
            buildFieldDTO.setFieldImage2(base64ProPic2);

            fieldService.saveField(buildFieldDTO);
            logger.info("Field POST method executed.");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            logger.info("Equipment POST method not executed.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.info("Equipment POST method not executed.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{fieldCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldStatus getSelectedField(@PathVariable ("fieldCode") String fieldCode){

        String regexForUserId = "^FIELD-[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserId);
        var regexMatcher = regexPattern.matcher(fieldCode);

        if (!regexMatcher.matches()){
            logger.info("Field GET method not executed.");
            return new SelectedFieldErrorStatus(1,"Field code is not valid");
        }
        logger.info("Field GET method executed.");
        return fieldService.getField(fieldCode);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldDTO> getAllFields(){
        logger.info("Field GET_ALL method executed.");
        return fieldService.getAllFields();
    }

    @DeleteMapping(value = "/{fieldCode}")
    public ResponseEntity<Void> deleteField(@PathVariable("fieldCode") String fieldCode){

        String regexForUserId = "^FIELD-[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserId);
        var regexMatcher = regexPattern.matcher(fieldCode);

        try {

            if (!regexMatcher.matches()){
                logger.info("Field DELETE method not executed.");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            fieldService.deleteField(fieldCode);
            logger.info("Field DELETE method executed.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (FieldNotFoundException e){
            e.printStackTrace();
            logger.info("Field DELETE method not executed.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("Field DELETE method not executed.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{fieldCode}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateField(
            @RequestPart("fieldName") String fieldName,
            @RequestPart("fieldLocation") String fieldLocation,
            @RequestPart("extentSize") String extentSize,
            @RequestPart("fieldImage1") MultipartFile fieldImage1,
            @RequestPart("fieldImage2") MultipartFile fieldImage2,
            @PathVariable ("fieldCode") String fieldCode
    ){
        // profilePic ----> Base64
        String base64ProPic1 = "";
        String base64ProPic2 = "";

        try {
            byte [] bytesProPic = fieldImage1.getBytes();
            byte [] bytesProPic2 = fieldImage2.getBytes();
            base64ProPic1 = AppUtil.profilePicToBase64(bytesProPic);
            base64ProPic2 = AppUtil.profilePicToBase64(bytesProPic2);
        }catch (Exception e){
            e.printStackTrace();
        }

        //Build the Object
        FieldDTO buildFieldDTO = new FieldDTO();
        buildFieldDTO.setFieldCode(fieldCode);
        buildFieldDTO.setFieldName(fieldName);
        buildFieldDTO.setFieldLocation(fieldLocation);
        buildFieldDTO.setExtentSize(Double.valueOf(extentSize));
        buildFieldDTO.setFieldImage1(base64ProPic1);
        buildFieldDTO.setFieldImage2(base64ProPic2);
        fieldService.updateField(fieldCode,buildFieldDTO);
        logger.info("Field UPDATE method executed.");
    }
}
