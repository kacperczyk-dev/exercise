package com.sample.exercise.service;

import com.sample.exercise.entity.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface MeasurementService {
    public void saveAllMeasurements(MultipartFile file);

    Page<Measurement> getAllMeasurements(int pageNumber, int pageSize);

    public Measurement getMeasurement(String code);

    public void deleteAllMeasuremenets();
}
