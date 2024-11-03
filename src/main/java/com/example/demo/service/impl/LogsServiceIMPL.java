package com.example.demo.service.impl;

import com.example.demo.customStatusCode.SelectedErrorStatus;
import com.example.demo.dao.CropDAO;
import com.example.demo.dao.LogsDAO;
import com.example.demo.dto.LogsStatus;
import com.example.demo.dto.impl.LogsDTO;
import com.example.demo.entity.impl.CropEntity;
import com.example.demo.entity.impl.LogsEntity;
import com.example.demo.exception.DataPersistException;
import com.example.demo.service.LogsService;
import com.example.demo.util.Mapping;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional

public class LogsServiceIMPL implements LogsService {

    @Autowired
    private LogsDAO logsDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void saveLog(LogsDTO logsDTO) {
        LogsEntity savedLog =
                logsDAO.save(mapping.toLogsEntity(logsDTO));
        if (savedLog == null) {
            throw new DataPersistException("Log not saved");
        }
    }

    @Override
    public List<LogsDTO> getAllLogs() {
        List<LogsEntity> allLogs = logsDAO.findAll();
        return mapping.asLogsDTOList(allLogs);
    }

    @Override
    public LogsStatus getLog(String code) {
        if (logsDAO.existsById(code)){
            LogsEntity selectedLog = logsDAO.getReferenceById(code);
            return mapping.toLogsDTO(selectedLog);
        }
        return (LogsStatus) new SelectedErrorStatus(2, "Log with code " + code + " not found");
    }
}
