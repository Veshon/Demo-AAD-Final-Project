package com.example.demo.entity.impl;

import com.example.demo.entity.Desig;
import com.example.demo.entity.Gender;
import com.example.demo.entity.Role;
import com.example.demo.entity.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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

    @Enumerated(EnumType.STRING)
    private Desig designation;

    @Enumerated(EnumType.STRING)
    private Gender gender;
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

    @ManyToMany(mappedBy = "staff")
    private List<FieldEntity> fields;

    @ManyToMany
    private List<VehicleEntity> vehicles;

    @OneToMany(mappedBy = "assignedStaff")
    private List<EquipmentEntity> equipment;

}
