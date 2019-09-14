package service;

import bean.Param;
import bean.SCCSystem;
import bean.SCCUser;

import java.util.List;

/**
 * @ClassName SysService
 * @Description TODO
 * @Author 逝风无言
 * @Data 2019/9/14 11:13
 * @Version 1.0
 **/
public interface SysService {
    SCCUser queryUserInfo(String token);

    List<SCCSystem> querySysInfo();

    List<Param> queryParamInfo(String sysId,String envName);

    int updateParam(String paramId,String paramValue);
}
