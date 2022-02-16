package com.kim.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "link_id", type = IdType.AUTO)
    private Long linkId;

    @NotBlank(message = "链接名称不能为空")
    private String linkName;

    @NotBlank(message = "链接地址不能为空")
    private String linkAddress;

    private String linkPic;


}
