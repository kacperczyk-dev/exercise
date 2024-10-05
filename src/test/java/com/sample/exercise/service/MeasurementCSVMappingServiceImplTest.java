package com.sample.exercise.service;

import com.sample.exercise.entity.Measurement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MeasurementCSVMappingServiceImplTest {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    @Autowired
    private MeasurementCSVMappingService measurementCSVMappingService;

    @Test
    public void testMapFile_withValidData() throws IOException, ParseException {
        String csvContent = "code,source,codeListCode,displayValue,longDescription,fromDate,toDate,sortingPriority\n" +
                "A001,SRC1,CLC1,Display1,LongDesc1,01-01-2020,31-12-2020,1\n" +
                "A002,SRC2,CLC2,Display2,LongDesc2,02-02-2020,,2\n";
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", csvContent.getBytes());

        List<Measurement> measurements = measurementCSVMappingService.mapFile(file);

        assertEquals(2, measurements.size());

        Measurement m1 = measurements.get(0);
        assertEquals("A001", m1.getCode());
        assertEquals("SRC1", m1.getSource());
        assertEquals("CLC1", m1.getCodeListCode());
        assertEquals("Display1", m1.getDisplayValue());
        assertEquals("LongDesc1", m1.getLongDescription());
        assertEquals(DATE_FORMAT.parse("01-01-2020"), m1.getFromDate());
        assertEquals(DATE_FORMAT.parse("31-12-2020"), m1.getToDate());
        assertEquals(Integer.valueOf(1), m1.getSortingPriority());

        Measurement m2 = measurements.get(1);
        assertEquals("A002", m2.getCode());
        assertEquals("SRC2", m2.getSource());
        assertEquals("CLC2", m2.getCodeListCode());
        assertEquals("Display2", m2.getDisplayValue());
        assertEquals("LongDesc2", m2.getLongDescription());
        assertEquals(DATE_FORMAT.parse("02-02-2020"), m2.getFromDate());
        assertNull(m2.getToDate());
        assertEquals(Integer.valueOf(2), m2.getSortingPriority());
    }

    @Test
    public void testMapFile_withInvalidDate() throws IOException {
        String csvContent = "code,source,codeListCode,displayValue,longDescription,fromDate,toDate,sortingPriority\n" +
                "A003,SRC3,CLC3,Display3,LongDesc3,invalid-date,31-12-2020,3\n";
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", csvContent.getBytes());

        assertThrows(ParseException.class, () -> {
            measurementCSVMappingService.mapFile(file);
        });
    }

    @Test
    public void testMapFile_withMissingOptionalFields() throws IOException, ParseException {
        String csvContent = "code,source,codeListCode,displayValue,longDescription,fromDate,toDate,sortingPriority\n" +
                "A004,SRC4,CLC4,Display4,LongDesc4,01-01-2020,,\n";
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", csvContent.getBytes());

        List<Measurement> measurements = measurementCSVMappingService.mapFile(file);

        assertEquals(1, measurements.size());

        Measurement m = measurements.get(0);
        assertEquals("A004", m.getCode());
        assertEquals("SRC4", m.getSource());
        assertEquals("CLC4", m.getCodeListCode());
        assertEquals("Display4", m.getDisplayValue());
        assertEquals("LongDesc4", m.getLongDescription());
        assertEquals(DATE_FORMAT.parse("01-01-2020"), m.getFromDate());
        assertNull(m.getToDate());
        assertNull(m.getSortingPriority());
    }

    @Test
    public void testMapFile_withEmptyFile() throws IOException, ParseException {
        String csvContent = "code,source,codeListCode,displayValue,longDescription,fromDate,toDate,sortingPriority\n";
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", csvContent.getBytes());

        List<Measurement> measurements = measurementCSVMappingService.mapFile(file);

        assertTrue(measurements.isEmpty());
    }
}
