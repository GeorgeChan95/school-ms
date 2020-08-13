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
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/13 18:21
 * @since JDK 1.8
 */
@Data
@Builder
@ApiModel(value = "GradeEvaluateQuery", description = "成绩评定列表查询对象")
@NoArgsConstructor
@AllArgsConstructor
public class GradeEvaluateQuery {
    /**
     * 学生姓名
     */
    @ApiModelProperty(value = "学生姓名")
    private String nickName;
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
     * 教师 user_id
     */
    @ApiModelProperty(value = "教师user_id")
    private String teacherId;

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
