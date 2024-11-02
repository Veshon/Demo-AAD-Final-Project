package com.example.demo.entity.impl;

import com.example.demo.entity.SuperEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Logs")

public class LogsEntity implements SuperEntity {

    @Id
    private String logCode;
    private String logDate;
    private String logDetails;

    @Column(columnDefinition = "LONGTEXT")
    private String observedImage;

    @ManyToOne
    @JoinColumn(name = "fieldCode", nullable = false)
    private FieldEntity fieldId;

    @ManyToOne
    @JoinColumn(name = "cropCode", nullable = false)
    private CropEntity cropCode;

    @ManyToOne
    @JoinColumn(name = "staffId", nullable = false)
    private StaffEntity staffId;
}
