package com.example.demo.service;

import com.example.demo.dto.impl.CropDTO;
import com.example.demo.entity.impl.CropEntity;
import com.example.demo.entity.impl.FieldEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapping {

    private final ModelMapper modelMapper;

    // Constructor initializes ModelMapper with custom mappings
    public Mapping() {
        this.modelMapper = new ModelMapper();
        modelMapper.typeMap(CropDTO.class, CropEntity.class).addMappings(mapper -> {
            mapper.map(CropDTO::getCropCode, CropEntity::setCode);
            mapper.map(src -> src.getFieldCode(), (dest, v) -> {
                FieldEntity fieldEntity = new FieldEntity();
                fieldEntity.setFieldCode((String) v);
                dest.setField(fieldEntity);
            });
        });
    }

    public CropEntity toCropEntity(CropDTO cropDTO) {
        return modelMapper.map(cropDTO, CropEntity.class);
    }
}
