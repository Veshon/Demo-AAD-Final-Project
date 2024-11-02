package com.example.demo.dto.impl;

import com.example.demo.dto.FieldStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class FieldDTO implements FieldStatus {

    private String fieldCode;
    private String fieldName;
    private String fieldLocation;
    private Double extentSize;
    private String fieldImage1;
    private String fieldImage2;

}
