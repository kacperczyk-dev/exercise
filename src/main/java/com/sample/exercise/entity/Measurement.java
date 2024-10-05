package com.sample.exercise.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "measurement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Measurement {

    @Id
    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "code_list_code", nullable = false)
    private String codeListCode;

    @Column(name = "display_value", nullable = false)
    private String displayValue;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;

    @Column(name = "sorting_priority")
    private Integer sortingPriority;

}
