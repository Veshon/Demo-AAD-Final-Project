package com.example.demo.dto.impl;

import com.example.demo.dto.StaffStatus;
import com.example.demo.entity.Desig;
import com.example.demo.entity.Gender;
import com.example.demo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class StaffDTO implements StaffStatus {
    private String id;
    private String firstName;
    private String lastName;
    private Desig designation;
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
    private Role role;
    private String fieldCode;
    private String vehicleCode;
    private String equipmentId;

}
