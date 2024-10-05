package com.sample.exercise.service;

import com.sample.exercise.entity.Measurement;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


@Service
public class MeasurementCSVMappingServiceImpl implements MeasurementCSVMappingService {

    private static final String DATE_PATTERN = "dd-MM-yyyy";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);


    public List<Measurement> mapFile(MultipartFile file) throws IOException, ParseException {
        List<Measurement> measurements = new ArrayList<>();
        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : csvParser) {
                Measurement entity = mapRecordToMeasurement(record);
                measurements.add(entity);
            }
        }
        return measurements;
    }

    private Measurement mapRecordToMeasurement(CSVRecord record) throws ParseException {
        Measurement measurement = new Measurement();
        measurement.setCode(record.get("code"));
        measurement.setSource(record.get("source"));
        measurement.setCodeListCode(record.get("codeListCode"));
        measurement.setDisplayValue(record.get("displayValue"));
        measurement.setLongDescription(record.get("longDescription"));
        measurement.setFromDate(DATE_FORMAT.parse(record.get("fromDate")));
        if (record.get("toDate") != null && !record.get("toDate").isEmpty()) {
            measurement.setToDate(DATE_FORMAT.parse(record.get("toDate")));
        }
        if (record.get("sortingPriority") != null && !record.get("sortingPriority").isEmpty()) {
            measurement.setSortingPriority(Integer.parseInt(record.get("sortingPriority")));
        }
        return measurement;
    }
}
