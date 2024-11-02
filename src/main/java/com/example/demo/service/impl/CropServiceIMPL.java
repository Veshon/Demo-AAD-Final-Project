package com.example.demo.service.impl;

import com.example.demo.dao.CropDAO;
import com.example.demo.dto.impl.CropDTO;
import com.example.demo.entity.impl.CropEntity;
import com.example.demo.exception.DataPersistException;
import com.example.demo.service.CropService;
import com.example.demo.util.AppUtil;
import com.example.demo.util.Mapping;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CropServiceIMPL implements CropService {

    @Autowired
    private CropDAO cropDAO;

    @Autowired
    private Mapping cropMapping;

    @Override
    public void saveCrop(CropDTO cropDTO) {
        cropDTO.setCode(AppUtil.generateCropCode());
        CropEntity cropEntity = cropMapping.toCropEntity(cropDTO);
        CropEntity savedCrop = cropDAO.save(cropEntity);

        if (savedCrop == null) {
            throw new DataPersistException("Crop not saved");
        }
    }
}
