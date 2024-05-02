package egovframework.example.sample.service;

import java.util.Optional;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import egovframework.example.sample.domain.Sample;
import egovframework.example.sample.domain.specification.SampleSpecifications;
import egovframework.example.sample.repository.SampleRepository;

@Service("sampleService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class EgovSampleServiceImpl extends EgovAbstractServiceImpl implements EgovSampleService {

	private static final Logger log = LoggerFactory.getLogger(EgovSampleServiceImpl.class);

	/** SampleRepository */
    @Resource(name="sampleRepository")
	private SampleRepository sampleRepository;
    
	/** ID Generation */
	@Resource(name = "egovIdGnrService")
	private EgovIdGnrService egovIdGnrService;

	public void deleteSample (Sample sample) throws Exception {
		sampleRepository.delete(sample);
	}
	
	public void saveSample (Sample sample) throws Exception {
		
		log.debug(sample.toString());
		
		if(sample.getId() == null) {
			String id = egovIdGnrService.getNextStringId();
			sample.setId(id);
			log.debug(sample.toString());
		}
		
		sampleRepository.save(sample);
	}
	
	public Page<Sample> findSampleList(Sample sample, Pageable pageable) throws Exception {
		
		Specification<Sample> spec = Specification.where(null);
		
		if (sample.getSearchCondition() != null && sample.getSearchCondition().equals("0")) {
            spec = spec.and(SampleSpecifications.searchByIdIfConditionZero(sample.getSearchKeyword()));
        } else if (sample.getSearchCondition() != null && sample.getSearchCondition().equals("1")) {
            spec = spec.and(SampleSpecifications.searchByNameIfConditionOne(sample.getSearchKeyword()));
        }
		
		pageable = PageRequest.of(sample.getPageIndex()-1, sample.getPageSize(), Sort.by("id").descending());

		Page<Sample> result = sampleRepository.findAll(spec, pageable);
		
		return result;
	}
	
	public Sample findSamplebyId (Sample sample) throws Exception {
		
		Optional<Sample> result = sampleRepository.findById(sample.getId());
		
		return result.orElseThrow(()-> processException("info.nodata.msg"));
	}
	
	public long findSampleToCnt (Sample sample) throws Exception {
		return sampleRepository.count();
	}

}
