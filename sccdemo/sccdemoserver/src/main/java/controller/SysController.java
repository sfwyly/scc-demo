package controller;

import bean.Param;
import bean.RequestResult;
import bean.SCCSystem;
import bean.SCCUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.SysService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName SysController
 * @Description TODO
 * @Author 逝风无言
 * @Data 2019/9/14 10:59
 * @Version 1.0
 **/
@RestController
public class SysController {

    @Autowired
    private SysService sysService;
    //查询用户信息
    @PostMapping("/queryuserinfo")
    public RequestResult queryForUserInfo(@RequestParam(value = "token",required = false) String token){
        if(StringUtils.isEmpty(token)){
            return new RequestResult("1","token为空，非法请求",null);
        }

        SCCUser user = sysService.queryUserInfo(token);
        return new RequestResult("0",null,user);
    }

    //查询子系统信息
    @PostMapping("/querysysinfo")
    public RequestResult quertSysInfo(){
        List<SCCSystem> sccSystemList = sysService.querySysInfo();
        return new RequestResult("0",null,sccSystemList);
    }

    //查询配置参数
    @PostMapping("/queryparaminfo/{sysId}/{envName}")
    public RequestResult queryParamInfo(@PathVariable("sysId") String sysId,@PathVariable("envName") String envName){

        List<Param> params = sysService.queryParamInfo(sysId,envName);
        return new RequestResult("0",null,params);
    }
    //更新配置项
    @PostMapping("/updateparam/{paramId}/{paramValue}")
    public RequestResult updateParam(@PathVariable String paramId,@PathVariable  String paramValue){

        int affectCount =  sysService.updateParam(paramId,paramValue);
        return new RequestResult();
    }
}
