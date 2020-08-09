package com.george.school.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     用户保存 数据传输对象
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/9 13:09
 * @since JDK 1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "id")
    private String id;

    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码")
    private String code;

    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名")
    private String name;

    /**
     * 角色备注
     */
    @ApiModelProperty(value = "角色备注")
    private String remark;

    /**
     * 角色Id集合
     */
    @ApiModelProperty(value = "资源id集合")
    private List<String> permissionIds = new ArrayList<>();
}
