package com.george.school.service.impl;

import com.george.school.entity.UserCourse;
import com.george.school.mapper.UserCourseMapper;
import com.george.school.service.IUserCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 人员与课程关联表 服务实现类
 * </p>
 *
 * @author George Chan
 * @since 2020-08-10
 */
@Service
public class UserCourseServiceImpl extends ServiceImpl<UserCourseMapper, UserCourse> implements IUserCourseService {

    @Override
    public boolean removeByCourseIds(String[] ids) {
        boolean res = this.baseMapper.deleteByCourseIds(ids) >= 0 ? true : false;
        return res;
    }
}
