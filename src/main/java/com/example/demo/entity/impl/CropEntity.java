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
@Table(name = "Crop")

public class CropEntity implements SuperEntity {

    @Id
    private String code;
    private String commonName;
    private String scientificName;
    @Column(columnDefinition = "LONGTEXT")
    private String cropImg;
    private String category;
    private String season;
    @ManyToOne
    @JoinColumn(name = "fieldCode")
    private FieldEntity field;

}
