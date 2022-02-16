package com.kim.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author kim_japan
 * @since 2021-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Carousel implements Serializable {

    private static final long serialVersionUID = 2L;

    @TableId(value = "c_id", type = IdType.AUTO)
    private Long cId;


    @NotBlank(message = "图片地址不能为空")
    private String cAddress;


    @NotBlank(message = "图片描述不能为空")
    private String cDescription;


    private LocalDateTime createDate;


}
