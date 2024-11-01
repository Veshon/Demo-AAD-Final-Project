package com.example.demo.dto.impl;

import com.example.demo.dto.CropStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CropDTO implements CropStatus {

    private String cropCode;
    private String CommonName;
    private String ScientificName;
    private String cropImage;
    private String category;
    private String Season;
    private String fieldCode;

}
