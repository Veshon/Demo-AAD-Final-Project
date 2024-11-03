package com.example.demo.entity.impl;

import com.example.demo.entity.Role;
import com.example.demo.entity.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Staff")

public class StaffEntity implements SuperEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;

//    @Enumerated(EnumType.STRING)
    private String designation;

    private String gender;
    private String joinedDate;
    private String dob;

    private String addressLine01;
    private String addressLine02;
    private String addressLine03;
    private String addressLine04;
    private String addressLine05;
    private String contactNo;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "fieldCode", nullable = false)
    private FieldEntity field;

    @ManyToOne
    @JoinColumn(name = "vehicleCode", nullable = false)
    private VehicleEntity vehicle;

    @OneToMany(mappedBy = "staff")
    private List<LogsEntity> staffId;

    @OneToMany(mappedBy = "staff")
    private List<EquipmentEntity> staffIds;
}
