package com.example.demo.service;

import com.example.demo.dto.CropStatus;
import com.example.demo.dto.LogsStatus;
import com.example.demo.dto.impl.CropDTO;
import com.example.demo.dto.impl.LogsDTO;

import java.util.List;

public interface LogsService {

    void saveLog(LogsDTO logsDTO);
    List<LogsDTO> getAllLogs();
    LogsStatus getLog(String code);

}
