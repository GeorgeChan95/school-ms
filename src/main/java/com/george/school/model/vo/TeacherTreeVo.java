package com.george.school.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/11 20:01
 * @since JDK 1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TeacherTreeVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @ApiModelProperty(value = "ID")
    private String id;
    /**
     * 教师姓名
     */
    @ApiModelProperty(value = "教师姓名")
    private String title;
    /**
     * 父节点ID
     */
    @ApiModelProperty(value = "父节点ID")
    private String parentId;

    @ApiModelProperty(value = "是否展开 默认不展开(false)")
    private boolean spread=true;

    /**
     * 子节点
     */
    @ApiModelProperty(value = "子节点数据集合")
    private List<TeacherTreeVo> children;
}
