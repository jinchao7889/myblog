package com.kim.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class BlogNoteAttach implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "attach_id", type = IdType.AUTO)
    private Long attachId;

    private String attachContent;

    private Long blogId;

    private LocalDateTime createDate;


}
