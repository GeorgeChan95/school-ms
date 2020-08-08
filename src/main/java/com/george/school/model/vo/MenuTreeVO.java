package com.george.school.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p></p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/8 15:21
 * @since JDK 1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuTreeVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 资源id
     */
    @ApiModelProperty(value = "资源ID")
    private String id;
    /**
     * 资源名称
     */
    @ApiModelProperty(value = "资源名称")
    private String title;
    /**
     * 资源类型
     */
    @ApiModelProperty(value = "资源类型 1菜单  2按钮")
    private int resourceType;
    /**
     * 资源url
     */
    @ApiModelProperty(value = "url")
    private String resourceUrl;
    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识")
    private String permission;
    /**
     * 父节点ID
     */
    @ApiModelProperty(value = "父节点id")
    private String parentId;

    @ApiModelProperty(value = "")
    private String icon;

    @ApiModelProperty(value = "是否展开 默认不展开(false)")
    private boolean spread=true;

    /**
     * 子节点
     */
    @ApiModelProperty(value = "子节点数据集合")
    private List<MenuTreeVO> children;
}
