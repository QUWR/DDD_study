package org.example.ddd_study.adapter.in.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API 에러 응답
 */
@Getter
@AllArgsConstructor
public class ApiErrorResponse {

    private final String code;
    private final String message;
}
