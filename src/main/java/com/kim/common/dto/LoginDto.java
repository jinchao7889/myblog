package com.kim.common.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
public class LoginDto implements Serializable {

    @NotBlank(message="用户名不能为空")
    private String userName;

    @NotBlank(message="密码不能为空")
    private String userPassword;


}
