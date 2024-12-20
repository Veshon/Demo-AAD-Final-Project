package com.example.demo.util;

import com.example.demo.dto.impl.*;
import com.example.demo.dto.impl.FieldDTO;
import com.example.demo.entity.impl.*;
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
    public FieldEntity toFieldEntity(FieldDTO fieldDTO){
        return modelMapper.map(fieldDTO, FieldEntity.class);
    }

    //Converting Entity to DTO
    public FieldDTO toFieldDTO(FieldEntity fieldEntity){
        return modelMapper.map(fieldEntity, FieldDTO.class);
    }

    public List<FieldDTO> asFieldDTOList(List<FieldEntity> fieldEntities) {
        return modelMapper.map(fieldEntities, new TypeToken<List<FieldDTO>>() {}.getType());
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

    public VehicleDTO toVehicleDTO(VehicleEntity vehicleEntity) {
        return modelMapper.map(vehicleEntity, VehicleDTO.class);
    }

    public VehicleEntity toVehicleEntity(VehicleDTO vehicleDTO) {
        return modelMapper.map(vehicleDTO, VehicleEntity.class);
    }

    public List<VehicleDTO> asVehicleDTOList(List<VehicleEntity> vehicleEntities) {
        return modelMapper.map(vehicleEntities, new TypeToken<List<VehicleDTO>>() {}.getType());
    }

    public EquipmentDTO toEquipmentDTO(EquipmentEntity equipmentEntity) {
        return modelMapper.map(equipmentEntity, EquipmentDTO.class);
    }

    public EquipmentEntity toEquipmentEntity(EquipmentDTO equipmentDTO) {
        return modelMapper.map(equipmentDTO, EquipmentEntity.class);
    }

    public List<EquipmentDTO> asEquipmentDTOList(List<EquipmentEntity> equipmentEntities) {
        return modelMapper.map(equipmentEntities, new TypeToken<List<EquipmentDTO>>() {}.getType());
    }

    public LogsDTO toLogsDTO(LogsEntity logsEntity) {
        return modelMapper.map(logsEntity, LogsDTO.class);
    }

    public LogsEntity toLogsEntity(LogsDTO logsDTO) {
        return modelMapper.map(logsDTO, LogsEntity.class);
    }

    public List<LogsDTO> asLogsDTOList(List<LogsEntity> logsEntities) {
        return modelMapper.map(logsEntities, new TypeToken<List<LogsDTO>>() {}.getType());
    }

    //Converting DTO to Entity
    public UserEntity toUserEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, UserEntity.class);
    }

    //Converting Entity to DTO
    public UserDTO toUserDTO(UserEntity userEntity){
        return modelMapper.map(userEntity, UserDTO.class);
    }

    public List<UserDTO> asUserDTOList(List<UserEntity> userEntities) {
        return modelMapper.map(userEntities, new TypeToken<List<UserDTO>>() {}.getType());
    }
}
