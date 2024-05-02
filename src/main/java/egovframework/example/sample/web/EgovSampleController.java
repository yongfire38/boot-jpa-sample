package egovframework.example.sample.web;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import egovframework.example.sample.domain.Sample;
import egovframework.example.sample.service.EgovSampleService;

@Controller
@SessionAttributes(types=Sample.class)
public class EgovSampleController {

	/** EgovSampleService */
	@Resource(name = "sampleService")
	private EgovSampleService sampleService;
	
	@Resource(name="validator")
	private Validator beanValidator;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@GetMapping("/")
	public String search(Sample sample, Model model, Pageable pageable) throws Exception {
		return this.list(sample, model, pageable);
	}

	@PostMapping("/sample/list")
	public String list(Sample sample, Model model, Pageable pageable) throws Exception {

		sample.setPageUnit(propertiesService.getInt("pageUnit"));
		sample.setPageSize(propertiesService.getInt("pageSize"));
		
		// pagination setting
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(sample.getPageIndex());
		paginationInfo.setRecordCountPerPage(sample.getPageUnit());
		paginationInfo.setPageSize(sample.getPageSize());
		
		sample.setFirstIndex(paginationInfo.getFirstRecordIndex());
		sample.setLastIndex(paginationInfo.getLastRecordIndex());
		sample.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// Search
		Page<Sample> samplePage = sampleService.findSampleList(sample, pageable);

		// List
		model.addAttribute("resultList", samplePage.getContent());
		
		// Count
		paginationInfo.setTotalRecordCount((int)samplePage.getTotalElements());
		
		// Pagination
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "egovSampleList";
	}

	@PostMapping("/sample/detail")
	public String detail(Sample sample, @RequestParam String id, Model model) throws Exception {
		sample.setId(id);
		Sample detail = this.sampleService.findSamplebyId(sample);
		model.addAttribute("sample", detail);
		return "egovSampleRegister";
	}

	@GetMapping("/sample/add")
	public String form(Sample sample, Model model) {
		
		sample = new Sample();

		model.addAttribute("sample", sample);
		return "egovSampleRegister";
	}

	@PostMapping("/sample/add")
	public String add(@Valid Sample sample, BindingResult bindingResult) throws Exception {
		
		if (bindingResult.hasErrors()) {
			return "egovSampleRegister";
		}
		this.sampleService.saveSample(sample);
		return "redirect:/";
	}

	@PostMapping("/sample/update")
	public String update(@Valid Sample sample, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return "egovSampleRegister";
		}
		this.sampleService.saveSample(sample);
		return "redirect:/";
	}

	@PostMapping("/sample/delete")
	public String delete(Sample sample) throws Exception {
		this.sampleService.deleteSample(sample);
		return "redirect:/";
	}

}
