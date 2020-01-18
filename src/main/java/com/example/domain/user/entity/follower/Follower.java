package com.example.domain.user.entity.follower;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Follower {

    @Builder.Default
    private Integer id = 0;

    @Builder.Default
    private Integer userId = 0;

    @Builder.Default
    private Integer followerId = 0;

    @Builder.Default
    private String followTime = "1900-01-01 00:00:00";
}
