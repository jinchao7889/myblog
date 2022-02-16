package com.kim.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

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
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "article_id", type = IdType.AUTO)
    private Long articleId;

    @NotBlank(message = "封面图片不能为空")
    private String coverAddress;

    @NotBlank(message = "文章标题不能为空")
    private String articleTitle;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createDate;

    private Boolean logicDel;

    @NotBlank(message = "内容不能为空")
    private String articleContent;

    @NotBlank(message = "地址不能为空")
    private String address;


}
