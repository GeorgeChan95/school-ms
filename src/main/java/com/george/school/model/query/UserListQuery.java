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
 * @date 2020/8/2 11:29
 * @since JDK 1.8
 */
@Data
@Builder
@ApiModel(value = "用户列表查询对象", description = "用户列表查询对象")
public class UserListQuery implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "用户登陆账户/编码")
    private String username;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户类型（0-学生 1-老师 2-管理员 3-系统管理员）")
    private Integer userType;

    @ApiModelProperty(value = "用户状态(0-正常 1-锁定)")
    private Integer status;

    @ApiModelProperty(value = "创建时间-开始")
    private String startTime;

    @ApiModelProperty(value = "创建时间-结束")
    private String endTime;

    @ApiModelProperty(value = "页码")
    private int page;

    @ApiModelProperty(value = "页大小")
    private int limit;

    @ApiModelProperty(value = "排序字段")
    private String field;

    @ApiModelProperty(value = "排序类型")
    private String order;

    /**
     * 在sql查询时，传入此参数，并在参数前后加 % ，用以模糊搜索
     * @return
     */
    public String getUserNameLike() {
        if (StringUtils.isEmpty(this.username)) {
            return StringPool.EMPTY;
        }
        return SqlUtils.concatLike(this.username, SqlLike.DEFAULT);
    }

    public String getNickNameLike() {
        if (StringUtils.isEmpty(this.nickname)) {
            return StringPool.EMPTY;
        }
        return SqlUtils.concatLike(this.nickname, SqlLike.DEFAULT);
    }
}
