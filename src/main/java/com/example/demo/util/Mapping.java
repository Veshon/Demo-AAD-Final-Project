package com.example.demo.util;

import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.FieldsDTO;
import com.example.demo.dto.impl.FieldsDTO;
import com.example.demo.dto.impl.StaffDTO;
import com.example.demo.entity.impl.CropEntity;
import com.example.demo.entity.impl.FieldEntity;
import com.example.demo.entity.impl.StaffEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {

    @Autowired
    private ModelMapper modelMapper;

    //Converting DTO to Entity
    public FieldEntity toFieldEntity(FieldsDTO fieldDTO){
        return modelMapper.map(fieldDTO, FieldEntity.class);
    }

    //Converting Entity to DTO
    public FieldsDTO toFieldDTO(FieldEntity fieldEntity){
        return modelMapper.map(fieldEntity, FieldsDTO.class);
    }

    public List<FieldsDTO> asFieldDTOList(List<FieldEntity> fieldEntities) {
        return modelMapper.map(fieldEntities, new TypeToken<List<FieldsDTO>>() {}.getType());
    }

    public CropDTO toCropDTO(CropEntity cropEntity) {
        return modelMapper.map(cropEntity, CropDTO.class);
    }

    public CropEntity toCropEntity(CropDTO cropDTO) {
        return modelMapper.map(cropDTO, CropEntity.class);
    }

    public List<CropDTO> asCropDTOList(List<CropEntity> cropEntities) {
        return modelMapper.map(cropEntities, new TypeToken<List<CropDTO>>() {}.getType());
    }

    public StaffDTO toStaffDTO(StaffEntity staffEntity) {
        return modelMapper.map(staffEntity, StaffDTO.class);
    }

    public StaffEntity toStaffEntity(StaffDTO staffDTO) {
        return modelMapper.map(staffDTO, StaffEntity.class);
    }

    public List<StaffDTO> asStaffDTOList(List<StaffEntity> staffEntities) {
        return modelMapper.map(staffEntities, new TypeToken<List<StaffDTO>>() {}.getType());
    }
}
