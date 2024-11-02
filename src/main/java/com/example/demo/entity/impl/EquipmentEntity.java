package com.example.demo.entity.impl;

import com.example.demo.entity.EquipmentStatus;
import com.example.demo.entity.EquipmentType;
import com.example.demo.entity.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Equipment")

public class EquipmentEntity implements SuperEntity {

    @Id
    private String equipmentId;
    private String name;

    @Enumerated(EnumType.STRING)
    private EquipmentType type;

    @Enumerated(EnumType.STRING)
    private EquipmentStatus status;

    @ManyToOne
    @JoinColumn(name = "fieldCode", nullable = false)
    private FieldEntity fieldId;

    @ManyToOne
    @JoinColumn(name = "staffId", nullable = false)
    private StaffEntity staffId;
}
