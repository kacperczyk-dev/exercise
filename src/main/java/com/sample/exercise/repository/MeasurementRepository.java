package com.sample.exercise.repository;

import com.sample.exercise.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, String> {

}
