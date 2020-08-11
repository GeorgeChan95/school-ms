package com.george.school.model.query;

import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlUtils;
import com.george.school.util.StringPool;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * <p>
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/10 21:15
 * @since JDK 1.8
 */
@Data
@Builder
@ApiModel(value = "CourseListQuery", description = "课程列表查询对象")
@NoArgsConstructor
@AllArgsConstructor
public class CourseListQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程名称/编码")
    private String code;

    @ApiModelProperty(value = "是否发布 0-未发布 1已发布")
    private Integer publishFlag;

    @ApiModelProperty(value = "选课截止-开始时间")
    private String startTime;

    @ApiModelProperty(value = "选课截止-结束时间")
    private String endTime;

    @ApiModelProperty(value = "是否已选 已选-1 未选-2")
    private Integer chooseFlag;

    @ApiModelProperty(value = "当前用户id")
    private String userId;

    @ApiModelProperty(value = "当前页")
    private int page;

    @ApiModelProperty(value = "每页数据量")
    private int limit;

    public String getCodeLike() {
        if (StringUtils.isEmpty(this.code)) {
            return StringPool.EMPTY;
        }
        return SqlUtils.concatLike(this.code, SqlLike.DEFAULT);
    }
}
