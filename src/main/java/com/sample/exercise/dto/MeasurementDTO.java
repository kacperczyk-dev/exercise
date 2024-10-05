package com.sample.exercise.dto;

import java.util.Date;

public record MeasurementDTO(String code, String source, String codeListCode, String displayValue,
                             String longDescription, Date fromDate, Date toDate, Integer sortingPriority) {
}
