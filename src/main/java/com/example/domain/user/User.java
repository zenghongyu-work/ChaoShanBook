package com.example.domain.user;

import com.example.domain.common.EnumType;
import com.example.domain.user.valueobject.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Builder.Default
    private Integer id = 0;

    @Builder.Default
    private String name = "";

    @Builder.Default
    private String pd = "";

    @Builder.Default
    private String nickname = "";

    @Builder.Default
    private String phone = "";

    @Builder.Default
    private String gender = EnumType.Gender.MALE;

    @Builder.Default
    private String birthday = "1900-01-01 00:00:00";

    @Builder.Default
    private String signature = "";

    @Builder.Default
    private String icon = "default.jpg";

    @Builder.Default
    private Location location = Location.builder().build();

    @Builder.Default
    private Integer followCount = 0;

    @Builder.Default
    private Integer fanCount = 0;

    @Builder.Default
    private Integer praiseCount = 0;

    @Builder.Default
    private String token = "";
}
