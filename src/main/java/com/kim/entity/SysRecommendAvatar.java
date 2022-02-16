package com.kim.entity;

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
 * @since 2021-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRecommendAvatar implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String avatarAddress;


}
