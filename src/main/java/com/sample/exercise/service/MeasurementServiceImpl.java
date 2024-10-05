package com.sample.exercise.service;

import com.sample.exercise.entity.Measurement;
import com.sample.exercise.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private MeasurementCSVMappingService measurementCSVMappingService;

    @Override
    public void saveAllMeasurements(MultipartFile file) {
        List<Measurement> measurements = null;
        try {
            measurements = measurementCSVMappingService.mapFile(file);
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong while accessing the file", e);
        } catch (ParseException e) {
            throw new RuntimeException("Something went wrong while parsing the file", e);
        }
        /*
            Note that this will overwrite any existing measurements with the same 'code'.
            Depending on the requirements we might instead:
                1. Check for existance and not overwrite.
                2. Abort the whole operation.
                3. Print a message that only some records were processed.
         */
        measurementRepository.saveAll(measurements);
    }

    @Override
    public Page<Measurement> getAllMeasurements(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("code"));
        return measurementRepository.findAll(pageable);
    }

    @Override
    public Measurement getMeasurement(String code) {
        return measurementRepository.findById(code).orElseThrow();
    }

    @Override
    public void deleteAllMeasuremenets() {
        measurementRepository.deleteAll();
    }
}
