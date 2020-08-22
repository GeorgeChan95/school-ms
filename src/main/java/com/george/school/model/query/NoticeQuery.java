package com.george.school.model.query;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlUtils;
import com.george.school.entity.Notice;
import com.george.school.util.StringPool;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/14 23:06
 * @since JDK 1.8
 */
@Data
@Builder
@ApiModel(value = "NoticeQuery", description = "公告列表查询对象")
@NoArgsConstructor
@AllArgsConstructor
public class NoticeQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公告名称")
    private String name;

    /**
     * 公告对象  1-学生  2-老师  3-全体人员
     */
    @ApiModelProperty(value = "公告对象  1-学生  2-老师  3-全体人员")
    private Integer target;

    /**
     * 发布状态 0-未发布  1-已发布
     */
    @ApiModelProperty(value = "发布状态 0-未发布  1-已发布")
    private Integer status;

    /**
     * 课程开始时间
     */
    @ApiModelProperty(value = "课程发布开始时间")
    private String startTime;

    /**
     * 课程结束时间
     */
    @ApiModelProperty(value = "课程发布结束时间")
    private String endTime;

    @ApiModelProperty(value = "当前页")
    private int page;

    @ApiModelProperty(value = "每页数据量")
    private int limit;

    public String getNameLike() {
        if (StringUtils.isEmpty(this.name)) {
            return StringPool.EMPTY;
        }
        return SqlUtils.concatLike(this.name, SqlLike.DEFAULT);
    }
}
