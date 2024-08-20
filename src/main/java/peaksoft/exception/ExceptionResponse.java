package peaksoft.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Builder
@Data
@AllArgsConstructor
public class ExceptionResponse {
    private HttpStatus httpStatus;
    private String exceptionClassName;
    private String exceptionMessage;
}
