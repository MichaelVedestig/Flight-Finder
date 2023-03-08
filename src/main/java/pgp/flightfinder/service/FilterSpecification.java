package pgp.flightfinder.service;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pgp.flightfinder.model.SearchRequestDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FilterSpecification<T> {

    public Specification<T> getSearchSpecification(List<SearchRequestDTO> searchRequestDTOList){
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicateList = new ArrayList<>();
            for(SearchRequestDTO requestDto : searchRequestDTOList){

                switch (requestDto.getOperation()) {
                    case EQUAL -> {
                        Predicate equal = criteriaBuilder.equal(root.get(requestDto.getFilterBy()), requestDto.getValue());
                        predicateList.add(equal);
                    }
                    case LIKE -> {
                        Predicate like = criteriaBuilder.like(root.get(requestDto.getFilterBy()), "%" + requestDto.getValue() + "%");
                        predicateList.add(like);
                    }
                    case IN -> {
                        String[] split = requestDto.getValue().split(",");
                        Predicate in = root.get(requestDto.getFilterBy()).in(Arrays.asList(split));
                        predicateList.add(in);
                    }
                    case GREATER_THAN -> {
                        Predicate greaterThan = criteriaBuilder.greaterThan(root.get(requestDto.getFilterBy()), requestDto.getValue());
                        predicateList.add(greaterThan);
                    }
                    case LESS_THAN -> {
                        Predicate lessThan = criteriaBuilder.lessThan(root.get(requestDto.getFilterBy()), requestDto.getValue());
                        predicateList.add(lessThan);
                    }
                    case BETWEEN -> {
                        //"10, 20"
                        String[] split1 = requestDto.getValue().split(",");
                        Predicate between = criteriaBuilder.between(root.get(requestDto.getFilterBy()), Long.parseLong(split1[0]), Long.parseLong(split1[1]));
                        predicateList.add(between);
                    }
                    case JOIN -> {
                        Predicate join = criteriaBuilder.equal(root.join(requestDto.getJoinTable()).get(requestDto.getFilterBy()), requestDto.getValue());
                        predicateList.add(join);
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + "");
                }

            }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
