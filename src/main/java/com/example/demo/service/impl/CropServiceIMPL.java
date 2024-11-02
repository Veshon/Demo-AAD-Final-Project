package com.example.demo.service.impl;

import com.example.demo.customStatusCode.SelectedErrorStatus;
import com.example.demo.dao.CropDAO;
import com.example.demo.dto.CropStatus;
import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.FieldDTO;
import com.example.demo.entity.impl.CropEntity;
import com.example.demo.entity.impl.FieldEntity;
import com.example.demo.exception.DataPersistException;
import com.example.demo.exception.FieldNotFoundException;
import com.example.demo.service.CropService;
import com.example.demo.util.AppUtil;
import com.example.demo.util.Mapping;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class CropServiceIMPL implements CropService {

    @Autowired
    private CropDAO cropDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void saveCrop(CropDTO cropDTO) {
        cropDTO.setCode(AppUtil.generateCropCode());

        CropEntity savedCrop =
                cropDAO.save(mapping.toCropEntity(cropDTO));
        if (savedCrop == null) {
            throw new DataPersistException("Crop not saved");
        }
    }

    @Override
    public List<CropDTO> getAllCrops() {
        List<CropEntity> allCrops = cropDAO.findAll();
        return mapping.asCropDTOList(allCrops);
    }

    @Override
    public CropStatus getCrop(String code) {
        if (cropDAO.existsById(code)){
            CropEntity selectedCrop = cropDAO.getReferenceById(code);
            return mapping.toCropDTO(selectedCrop);
        }
        return new SelectedErrorStatus(2, "Crop with code " + code + " not found");
    }

    @Override
    public void deleteCrop(String code) {
        Optional<CropEntity> existedCrop = cropDAO.findById(code);

        if (!existedCrop.isPresent()){
            throw new FieldNotFoundException("Crop code" + code + "Not found");
        }else {
            cropDAO.deleteById(code);
        }
    }
}
