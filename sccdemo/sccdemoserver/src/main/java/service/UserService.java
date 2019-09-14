package service;

import bean.SCCUser;

/**
 * @ClassName UserService
 * @Description 用户业务逻辑接口
 * @Author 逝风无言
 * @Data 2019/9/12 14:03
 * @Version 1.0
 **/
public interface UserService {
    SCCUser login(String username, String password);

    boolean userExistInCache(String token);
}
