package org.example.stamppaw_backend.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StampPawException extends RuntimeException {
    ErrorCode errorCode;
}
