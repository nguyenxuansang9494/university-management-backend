package uit.edu.vn.universitymanagement.exception;

import lombok.Getter;


@Getter
public class CommonRuntimeException extends RuntimeException {
    private final ErrorType errorType;

    public CommonRuntimeException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public CommonRuntimeException(ErrorType errorType, String msg) {
        super(msg);
        this.errorType = errorType;
    }
}
