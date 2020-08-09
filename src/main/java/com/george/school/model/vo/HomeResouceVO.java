package com.george.school.model.vo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * <p></p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/1 14:52
 * @since JDK 1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeResouceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 资源id
     */
    private String id;
    /**
     * 资源名称
     */
    private String resourceName;
    /**
     * 资源类型
     */
    private int resourceType;
    /**
     * 资源url
     */
    private String resourceUrl;
    /**
     * 权限标识
     */
    private String permission;
    /**
     * 父节点ID
     */
    private String parentId;

    private String icon;

    private String sort;

    /**
     * 子节点
     */
    private List<HomeResouceVO> children;
}
