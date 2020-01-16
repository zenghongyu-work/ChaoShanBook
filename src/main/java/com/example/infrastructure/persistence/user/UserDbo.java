package com.example.infrastructure.persistence.user;

import com.example.domain.user.User;
import com.example.domain.user.valueobject.Gender;
import com.example.domain.user.valueobject.Location;
import com.example.infrastructure.persistence.BaseDbo;
import lombok.*;
import org.springframework.beans.BeanUtils;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "user")
public class UserDbo extends BaseDbo {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String name;
    private String pd;
    private String nickname;
    private String phone;
    private String gender;
    private String birthday;
    private String signature;
    private String icon;
    private String province;
    private String downtown;
    private Integer followCount;
    private Integer fanCount;
    private Integer praiseCount;
    private String token;

    public User toModule() {
        User target = User.builder().build();
        BeanUtils.copyProperties(this, target);
        target.setLocation(Location.builder()
                .province(this.province)
                .downtown(this.downtown)
                .build());
        target.setGender(Gender.valueOf(this.gender));
        return target;
    }

    public static UserDbo fromModule(User user) {
        UserDbo target = UserDbo.builder().build();
        BeanUtils.copyProperties(user, target);
        target.setProvince(user.getLocation().getProvince());
        target.setDowntown(user.getLocation().getDowntown());
        target.setGender(user.getGender().toString());
        return target;
    }
}
