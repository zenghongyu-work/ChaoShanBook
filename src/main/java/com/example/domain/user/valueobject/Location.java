package com.example.domain.user.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Builder.Default
    private String province = "";

    @Builder.Default
    private String downtown = "";
}
