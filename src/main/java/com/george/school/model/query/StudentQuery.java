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

/**
 * <p>
 *
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/14 19:18
 * @since JDK 1.8
 */
@Data
@Builder
@ApiModel(value = "StudentQuery", description = "学生列表查询对象")
@NoArgsConstructor
@AllArgsConstructor
public class StudentQuery {
    /**
     * 学生姓名
     */
    @ApiModelProperty(value = "学生姓名")
    private String nickName;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 院ID
     */
    @ApiModelProperty(value = "院ID")
    private String collegeId;
    /**
     * 系ID
     */
    @ApiModelProperty(value = "系ID")
    private String departmentId;
    /**
     * 专业ID
     */
    @ApiModelProperty(value = "专业ID")
    private String majorId;

    /**
     * 课程开始时间
     */
    @ApiModelProperty(value = "创建开始时间")
    private String startTime;

    /**
     * 课程结束时间
     */
    @ApiModelProperty(value = "创建结束时间")
    private String endTime;

    @ApiModelProperty(value = "当前页")
    private int page;

    @ApiModelProperty(value = "每页数据量")
    private int limit;

    public String getNickNameLike() {
        if (StringUtils.isEmpty(this.nickName)) {
            return StringPool.EMPTY;
        }
        return SqlUtils.concatLike(this.nickName, SqlLike.DEFAULT);
    }
}
