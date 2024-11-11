package com.example.demo.controller;

import com.example.demo.customStatusCode.SelectedErrorStatus;
import com.example.demo.dto.CropStatus;
import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.FieldDTO;
import com.example.demo.exception.DataPersistException;
import com.example.demo.exception.FieldNotFoundException;
import com.example.demo.service.CropService;
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
@RequestMapping("api/v1/crops")

public class CropController {
    static Logger logger = LoggerFactory.getLogger(CropController.class);

    @Autowired
    private CropService cropService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCrop(
            @RequestPart("commonName") String commonName,
            @RequestPart("scientificName") String scientificName,
            @RequestPart("category") String category,
            @RequestPart("season") String season,
            @RequestPart("fieldCode") String fieldCode,
            @RequestPart("cropImg") MultipartFile profilePic){

        logger.info("POST method executed.");

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

            logger.info("Crop data saved.");

            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            logger.info("Data not saved, because of BAD REQUEST.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            logger.info("Data not saved, because of server error.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CropDTO> getAllCrops(){
        logger.info("Crop data GET method executed.");
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

    @DeleteMapping(value = "/{code}")
    public ResponseEntity<Void> deleteCode(@PathVariable("code") String code){

        String regexForUserId = "^CROP-[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserId);
        var regexMatcher = regexPattern.matcher(code);

        try {

            if (!regexMatcher.matches()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            cropService.deleteCrop(code);
            logger.info("Crop deleted.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (FieldNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{code}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateCode(
            @RequestPart("commonName") String commonName,
            @RequestPart("scientificName") String scientificName,
            @RequestPart("category") String category,
            @RequestPart("season") String season,
            @RequestPart("cropImg") MultipartFile cropImg,
            @PathVariable ("code") String code
    ){
        // profilePic ----> Base64
        String base64ProPic = "";

        try {
            byte [] bytesProPic = cropImg.getBytes();
            base64ProPic = AppUtil.profilePicToBase64(bytesProPic);
        }catch (Exception e){
            e.printStackTrace();
        }

        //Build the Object
        CropDTO buildCropDTO = new CropDTO();
        buildCropDTO.setCode(code);
        buildCropDTO.setCommonName(commonName);
        buildCropDTO.setScientificName(scientificName);
        buildCropDTO.setCategory(category);
        buildCropDTO.setSeason(season);
        buildCropDTO.setCropImg(base64ProPic);
        cropService.updateCrop(code,buildCropDTO);
    }

}
