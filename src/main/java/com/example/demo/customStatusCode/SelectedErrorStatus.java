package com.example.demo.customStatusCode;

import com.example.demo.dto.CropStatus;
import com.example.demo.dto.FieldStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedErrorStatus implements FieldStatus, CropStatus {
    private int statusCode;
    private String statusMessage;
}