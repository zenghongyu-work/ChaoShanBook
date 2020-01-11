package com.example.controller.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    @Builder.Default
    private Integer code = 1;

    @Builder.Default
    private String msg = "";

    private Object data;
}
