package com.example.demo.controller;

import com.example.demo.dto.impl.FieldDTO;
import com.example.demo.exception.DataPersistException;
import com.example.demo.service.FieldService;
import com.example.demo.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/fields")

public class FieldController {

    @Autowired
    public FieldService fieldService;

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
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
