package com.kim.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("blogs")
public class Blogs implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "blog_id", type = IdType.AUTO)
    private Long blogId;

    @NotBlank(message = "标题不能为空")
    private String blogTitle;

    @NotBlank(message = "描述不能为空")
    private String blogDescription;

    @NotBlank(message = "内容不能为空")
    private String blogContent;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    private Boolean logicDel;


}
