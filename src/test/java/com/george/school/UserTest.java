package com.george.school;

import com.george.school.entity.User;
import com.george.school.service.IUserService;
import com.george.school.util.Md5Util;
import com.george.school.util.StringPool;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * <p>
 *     测试用户的添加
 * </p>
 *
 * @author George Chan
 * @version 1.0
 * @date 2020/7/25 22:55
 * @since JDK 1.8
 */
@SpringBootTest
@ActiveProfiles("dev")
public class UserTest {
    private final Logger LOGGER = LoggerFactory.getLogger(UserTest.class);

    @Autowired
    private IUserService userService;

    @Test
    public void testAddUser() {

        String password = StringPool.EMPTY;
        try {
            password = Md5Util.encrypt("admin", "admin");
        } catch (Exception e) {
            e.printStackTrace();
        }

        User user = User.builder()
                .username("admin")
                .password(password)
                .nickname("管理员")
                .mobile("18326088610")
                .email("george_95@126.com")
                .build();

        boolean save = userService.saveOrUpdate(user);

        LOGGER.info("用户保存 ==》 {}", save);
    }
}
