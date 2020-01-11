package com.example.domain.user;

import com.example.domain.user.valueobject.Gender;
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
    private Gender gender = Gender.MALE;

    @Builder.Default
    private String icon = "";

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
