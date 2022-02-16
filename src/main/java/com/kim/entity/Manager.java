package com.kim.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author kim_japan
 * @since 2022-01-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Manager implements Serializable {

    private static final long serialVersionUID = 123432L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String managerName;

    private String managerPassword;



}
