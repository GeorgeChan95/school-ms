package com.george.school.model.query;

import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlUtils;
import com.george.school.util.StringPool;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * <p></p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/9 10:09
 * @since JDK 1.8
 */
@Data
@Builder
@ApiModel(value = "系统角色查询对象", description = "系统角色查询对象")
public class RoleListQuery  implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名称/编码")
    private String name;

    @ApiModelProperty(value = "创建时间-开始")
    private String startTime;

    @ApiModelProperty(value = "创建时间-结束")
    private String endTime;

    @ApiModelProperty(value = "页码")
    private int page;

    @ApiModelProperty(value = "页大小")
    private int limit;

    /**
     * 在sql查询时，传入此参数，并在参数前后加 % ，用以模糊搜索
     * @return
     */
    public String getNameLike() {
        if (StringUtils.isEmpty(this.name)) {
            return StringPool.EMPTY;
        }
        return SqlUtils.concatLike(this.name, SqlLike.DEFAULT);
    }
}
