package controller;

import bean.RequestResult;
import com.google.common.collect.ImmutableMap;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author 逝风无言
 * @Data 2019/9/12 17:51
 * @Version 1.0
 **/
@RestController
@CrossOrigin(allowCredentials = "true",origins = {"*"})
public class UserController {

    private static final String Login_Status_Fail="1";

    private static final String Login_Status_Success="0";

    private static final String Login_Status_Expire="2";

    //1.loginfail登录失败
    @PostMapping("/loginfail")
    public RequestResult loginFail(){
        return new RequestResult(Login_Status_Fail,"用户名、密码错误",null);
    }

    //2.loginsuccess登录成功

    @PostMapping("/loginsucces/{token}")
    public RequestResult loginSuccess(@PathVariable String token){
        return new RequestResult(Login_Status_Success,"登录成功", ImmutableMap.of("token",token));
    }

    //3.loginexpire
    @PostMapping("/loginexpire")
    public RequestResult loginExpire(){
        return new RequestResult(Login_Status_Expire,"缓存超时",null);
    }
}
