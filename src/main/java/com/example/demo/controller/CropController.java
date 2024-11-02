package com.example.demo.controller;

import com.example.demo.customStatusCode.SelectedErrorStatus;
import com.example.demo.dto.CropStatus;
import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.FieldDTO;
import com.example.demo.exception.DataPersistException;
import com.example.demo.service.CropService;
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
@RequestMapping("api/v1/crops")

public class CropController {

    @Autowired
    private CropService cropService;

/*    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveNote(@RequestBody CropDTO noteDTO) {
        try {
            cropService.saveCrop(noteDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCrop(
            @RequestPart("commonName") String commonName,
            @RequestPart("scientificName") String scientificName,
            @RequestPart("category") String category,
            @RequestPart("season") String season,
            @RequestPart("fieldCode") String fieldCode,
            @RequestPart("cropImg") MultipartFile profilePic){

        // profilePic ----> Base64
        String base64ProPic = "";

        try {
            byte [] bytesProPic = profilePic.getBytes(); //Converting profile pic to byte array
            base64ProPic = AppUtil.profilePicToBase64(bytesProPic);
            String code = AppUtil.generateCropCode(); //Generating UUID

            var buildUserDTO = new CropDTO(); //Creating obj
            buildUserDTO.setCode(code);
            buildUserDTO.setCommonName(commonName);
            buildUserDTO.setScientificName(scientificName);
            buildUserDTO.setCategory(category);
            buildUserDTO.setSeason(season);
            buildUserDTO.setFieldCode(fieldCode);
            buildUserDTO.setCropImg(base64ProPic);

            cropService.saveCrop(buildUserDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDTO> getAllCrops(){
        return cropService.getAllCrops();
    }

    @GetMapping(value = "/{code}",produces = MediaType.APPLICATION_JSON_VALUE)
    public CropStatus getSelectedCrop(@PathVariable ("code") String code){
        String regexForUserID = "^CROP-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserID);
        var regexMatcher = regexPattern.matcher(code);
        if (!regexMatcher.matches()) {
            return new SelectedErrorStatus(1,"Crop ID is not ");
        }
        return cropService.getCrop(code);
    }
}
