package com.sample.exercise.controller;

import com.sample.exercise.dto.MeasurementDTO;
import com.sample.exercise.entity.Measurement;
import com.sample.exercise.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("measurements")
public class MeasurementRestController {

    @Autowired
    private MeasurementService measurementService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        measurementService.saveAllMeasurements(file);
        return ResponseEntity.ok("Measurements processed successfully");
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<MeasurementDTO>> fetchAll(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "5") int pageSize) {
        Page<Measurement> measurements = measurementService.getAllMeasurements(pageNumber, pageSize);
        List<MeasurementDTO> dto = measurements.stream().map((m) -> new MeasurementDTO(
                m.getCode(),
                m.getSource(),
                m.getCodeListCode(),
                m.getDisplayValue(),
                m.getLongDescription(),
                m.getFromDate(),
                m.getToDate(),
                m.getSortingPriority()
        )).toList();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{code}")
    public ResponseEntity<MeasurementDTO> fetchBy(@PathVariable String code) {
        Measurement m = measurementService.getMeasurement(code);
        MeasurementDTO dto = new MeasurementDTO(
                m.getCode(),
                m.getSource(),
                m.getCodeListCode(),
                m.getDisplayValue(),
                m.getLongDescription(),
                m.getFromDate(),
                m.getToDate(),
                m.getSortingPriority()
        );
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAll() {
        measurementService.deleteAllMeasuremenets();
        return ResponseEntity.ok("All measurements were deleted successfully");
    }


}
