package _1.vietpq.job_hunter.dto.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ResultPaginationDTO {
    private Meta meta;
    private Object result;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public static class Meta {
    private int page;
    private int pageSize;
    private int pages;
    private Long total;
    
}
}
