package com.kim.common.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
public class ManagerLoginDto implements Serializable {


    @NotBlank(message="管理员账户不能为空")
    private String managerName;

    @NotBlank(message="管理员密码不能为空")
    private String managerPassword;
}
