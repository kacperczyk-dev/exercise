package com.sample.exercise.service;

import com.sample.exercise.entity.Measurement;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface MeasurementCSVMappingService {
    public List<Measurement> mapFile(MultipartFile file) throws IOException, ParseException;
}
