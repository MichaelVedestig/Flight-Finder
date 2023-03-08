package pgp.flightfinder.service;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pgp.flightfinder.model.SearchRequestDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilterSpecification<T> {

    public Specification<T> getSearchSpecification(List<SearchRequestDTO> searchRequestDTOList){
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicateList = new ArrayList<>();
            for(SearchRequestDTO requestDTO : searchRequestDTOList){
                Predicate predicate = criteriaBuilder.equal(root.get(requestDTO.getColumn()), requestDTO.getValue());
                predicateList.add(predicate);
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
