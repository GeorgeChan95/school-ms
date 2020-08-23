package com.george.school.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.george.school.entity.User;
import com.george.school.model.dto.LoginUserDto;
import com.george.school.model.dto.UserTableDTO;
import com.george.school.model.query.StudentQuery;
import com.george.school.model.query.UserListQuery;
import com.george.school.model.vo.StudentScoreVO;
import com.george.school.model.vo.StudentTableVO;
import com.george.school.model.vo.TeacherTreeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author George Chan
 * @since 2020-07-21
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    LoginUserDto selectUserByUserName(@Param("username") String username);

    /**
     * 通过用户名获取用户信息
     * @param username
     * @return
     */
    User getByUserName(@Param("username") String username);

    /**
     * 获取用户列表的分页数据
     * @param query 查询条件
     * @return
     */
    List<UserTableDTO> getUserPageList(@Param("query") UserListQuery query);

    /**
     * 根据手机号判断用户是否存在
     * @param mobile
     * @return
     */
    int findUserByMobile(@Param("mobile") String mobile);

    /**
     * 根据用户登陆账户判断用户是否已存在
     * @param username
     * @return
     */
    int findByUserName(@Param("username") String username);

    /**
     * 获取所有是教师的用户
     * @return
     */
    List<TeacherTreeVo> findAllTeacherData();

    /**
     * 获取学生分页列表
     * @param query 参数列表
     * @return
     */
    List<StudentTableVO> getStudentPageList(@Param("query") StudentQuery query);

    /**
     * 根据学生id，分页获取学生的成绩列表数据
     * @param studentId 学生ID
     * @return
     */
    List<StudentScoreVO> getStudentScoreList(@Param("studentId") String studentId);

    /**
     * 获取用户的个人信息
     * @param id 用户id
     * @return
     */
    UserTableDTO findUserTableInfo(@Param("id") String id);

    /**
     * 获取用户头像地址
     * @param id
     * @return
     */
    String getUserImage(@Param("id") String id);

    /**
     * 获取用户所属组织
     * @param id 用户id
     * @return
     */
    String findUserOrgName(String id);
}
