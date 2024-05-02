package egovframework.example.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import egovframework.example.sample.domain.Sample;

public interface SampleRepository extends JpaRepository<Sample, String>, JpaSpecificationExecutor<Sample> {
	
}
