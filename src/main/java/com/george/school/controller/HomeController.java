package com.george.school.controller;

import com.george.school.constants.UserLoginConst;
import com.george.school.entity.User;
import com.george.school.model.vo.HomeInfoVO;
import com.george.school.model.vo.HomeResouceVO;
import com.george.school.model.vo.MainInfoVO;
import com.george.school.service.INoticeService;
import com.george.school.service.IResourcesService;
import com.george.school.service.IUserService;
import com.george.school.util.Result;
import com.george.school.util.StatusCode;
import com.george.school.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 * 主页面前端控制器
 * <p>
 * 加载头部和菜单数据
 * 基本资料
 * 更换密码
 * 关于
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/8/1 9:44
 * @since JDK 1.8
 */
@Slf4j
@Api(tags = "主页面-头部与菜单")
@RequestMapping("/api/home")
@RestController
public class HomeController {
    private final IUserService userService;
    private final IResourcesService resourcesService;
    private final INoticeService noticeService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public HomeController(IUserService userService, IResourcesService resourcesService,
                          INoticeService noticeService, RedisTemplate<String, Object> redisTemplate) {
        this.userService = userService;
        this.resourcesService = resourcesService;
        this.noticeService = noticeService;
        this.redisTemplate = redisTemplate;
    }

    @ApiOperation("首页信息")
    @GetMapping("/info")
    public Result getHomeInfo() {
        Subject subject = SecurityUtils.getSubject();
        User principal = (User) subject.getPrincipal();
        Session session = subject.getSession();

        // 获取用户上次的登录信息
        User lastUser = (User) redisTemplate.opsForValue().get(principal.getUsername() + UserLoginConst.USER_LOGIN_KEY);


        // 加载用户的权限数据
        List<HomeResouceVO> resouceVOList = resourcesService.findUrlAndPermision(principal.getId());

        HomeInfoVO infoVO = HomeInfoVO.builder()
                                        .username(principal.getUsername())
                                        .nickname(principal.getNickname())
                                        .email(principal.getEmail())
                                        .mobile(principal.getMobile())
                                        .lastLoginIp(lastUser.getLastLoginIp())
                                        .lastLoginTime(lastUser.getLastLoginTime() == null ? null : lastUser.getLastLoginTime().format(DateTimeFormatter.ISO_DATE_TIME))
                                        .loginCount(principal.getLoginCount())
                                        .resouceVOList(resouceVOList)
                                        .build();

        return new Result(true, StatusCode.OK, "操作成功！", infoVO);
    }

    @ApiOperation("主页用户信息信息")
    @GetMapping("/mainInfo")
    public Result getMainInfo() {
        Subject subject = SecurityUtils.getSubject();
        User principal = (User) subject.getPrincipal();

        // 获取用户上次的登录信息
        User lastUser = (User) redisTemplate.opsForValue().get(principal.getUsername() + UserLoginConst.USER_LOGIN_KEY);

        MainInfoVO infoVO = MainInfoVO.builder()
                .username(principal.getUsername())
                .nickname(principal.getNickname())
                .email(principal.getEmail())
                .mobile(principal.getMobile())
                .lastLoginIp(lastUser.getLastLoginIp())
                .lastLoginTime(lastUser.getLastLoginTime() == null ? null : lastUser.getLastLoginTime().format(DateTimeFormatter.ISO_DATE) + StringPool.SPACE + lastUser.getLastLoginTime().format(DateTimeFormatter.ISO_LOCAL_TIME))
                .loginCount(principal.getLoginCount())
                .build();

        // 头像地址
        String image = userService.getUserImage(principal.getId());

        String orgName = userService.getUserOrgInfo(principal.getId());
        infoVO.setOrgName(orgName);

        // 公告信息
        MainInfoVO notice = noticeService.getUserNoticeInfo(principal);

        infoVO.setImage(image);
        infoVO.setLastNoticeTitle(StringUtils.isEmpty(notice.getLastNoticeTitle()) ? StringPool.EMPTY : notice.getLastNoticeTitle());
        infoVO.setLastNoticeContent(StringUtils.isEmpty(notice.getLastNoticeContent()) ? StringPool.EMPTY : notice.getLastNoticeContent());
        infoVO.setBeforeNoticeTitle(StringUtils.isEmpty(notice.getBeforeNoticeTitle()) ? StringPool.EMPTY : notice.getBeforeNoticeTitle());
        infoVO.setBeforeNoticeContent(StringUtils.isEmpty(notice.getBeforeNoticeContent()) ? StringPool.EMPTY : notice.getBeforeNoticeContent());
        return new Result(true, StatusCode.OK, "操作成功", infoVO);
    }
}
