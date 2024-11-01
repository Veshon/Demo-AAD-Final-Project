package com.example.demo.service.impl;

import com.example.demo.dao.CropDAO;
import com.example.demo.dto.impl.CropDTO;
import com.example.demo.entity.impl.CropEntity;
import com.example.demo.exception.DataPersistException;
import com.example.demo.service.CropService;
import com.example.demo.util.AppUtil;
import com.example.demo.util.Mappingg;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CropServiceIMPL implements CropService {

    @Autowired
    private CropDAO cropDAO;

    @Autowired
    private Mappingg cropMapping;

    @Override
    public void saveCrop(CropDTO cropDTO) {
        // Generate and set the crop code for the new crop
        cropDTO.setCropCode(AppUtil.generateCropCode());

        // Convert CropDTO to CropEntity using custom mapping configuration
        CropEntity cropEntity = cropMapping.toCropEntity(cropDTO);

        // Save the CropEntity
        CropEntity savedCrop = cropDAO.save(cropEntity);

        // Check if the crop was successfully saved
        if (savedCrop == null) {
            throw new DataPersistException("Crop not saved");
        }
    }
}
