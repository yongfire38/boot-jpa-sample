package egovframework.example.sample.domain.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import egovframework.example.sample.domain.Sample;

public class SampleSpecifications {
	
	public static Specification<Sample> searchByIdIfConditionZero(String searchKeyword) {
        return (Root<Sample> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (searchKeyword != null && !searchKeyword.isEmpty()) {
                return criteriaBuilder.equal(root.get("id"), searchKeyword);
            }
            return null;
        };
    }

    public static Specification<Sample> searchByNameIfConditionOne(String searchKeyword) {
        return (Root<Sample> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (searchKeyword != null && !searchKeyword.isEmpty()) {
                return criteriaBuilder.like(root.get("name"), "%" + searchKeyword + "%");
            }
            return null;
        };
    }
}
