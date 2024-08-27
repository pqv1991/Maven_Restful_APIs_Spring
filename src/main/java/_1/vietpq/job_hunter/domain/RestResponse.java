package _1.vietpq.job_hunter.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResponse<T> {
    private int statusCode;
        private String error;
        // message có thể là string, hoặc arrayList
        private Object message;
        private T data;
    
}
