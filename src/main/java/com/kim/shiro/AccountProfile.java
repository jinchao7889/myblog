package com.kim.shiro;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AccountProfile implements Serializable {
    private Long id;

    private String userName;

    private String userAvatar;

    private String userMail;

    private LocalDateTime createDate;

    private String userRole;

    private Integer status;

    /*
    * @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String userPassword;

    private String userAvatar;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String userMail;

    private String userRole;

    private Integer status;*/
}
