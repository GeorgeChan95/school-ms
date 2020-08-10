package com.george.school.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/10 12:51
 * @since JDK 1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationTreeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键ID")
    private String id;

    /**
     * 代号
     */
    @ApiModelProperty(value = "代号")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String title;

    /**
     * 类型 1-院 2-系 3-专业
     */
    @ApiModelProperty(value = "级别类型")
    private Integer type;

    /**
     * 父节点id
     */
    @ApiModelProperty(value = "父节点id")
    private String parentId;

    @ApiModelProperty(value = "父节点名称")
    private String parentName;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "是否展开 默认不展开(false)")
    private boolean spread=true;

    /**
     * 子节点
     */
    @ApiModelProperty(value = "子节点数据集合")
    private List<OrganizationTreeVO> children;
}
