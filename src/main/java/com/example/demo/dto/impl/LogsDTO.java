package com.example.demo.dto.impl;

import com.example.demo.dto.LogsStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class LogsDTO implements LogsStatus {

    private String logCode;
    private String logDate;
    private String logDetails;
    private String observedImage;
    private String fieldCode;
    private String cropCode;
    private String staffId;
}
