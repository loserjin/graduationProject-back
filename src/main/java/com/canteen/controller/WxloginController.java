package com.canteen.controller;

import com.canteen.common.dto.WxSessionDto;
import com.canteen.common.lang.Result;
import com.canteen.entity.User;
import com.canteen.service.UserService;
import com.canteen.util.HttpClientUtil;
import com.canteen.util.JsonUtils;
import com.canteen.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
@RestController
public class WxloginController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    @PostMapping("wxLogin")
    public Result wxLogin(@RequestParam String code,
                          @RequestBody User user,
                          HttpServletResponse response){
//        https://api.weixin.qq.com/sns/jscode2session?
//        appid=APPID&
//        secret=SECRET&
//        js_code=JSCODE&
//        grant_type=authorization_code
        String url="https://api.weixin.qq.com/sns/jscode2session";
        Map<String ,String> param=new HashMap<>();
        param.put("appid","wx6b85c28cf90be142");
        param.put("secret","43ab8307d583b4df10b8c724ac253f8d");
        param.put("js_code",code);
        param.put("grant_type","authorization_code");
        String wxResult=HttpClientUtil.doGet(url,param);
        WxSessionDto wxSessionDto = JsonUtils.jsonToPojo(wxResult, WxSessionDto.class);
        user.setOpenId(wxSessionDto.getOpenid());
        user.setSessionKey(wxSessionDto.getSession_key());
        userService.saveOrUpdate(user);
        //存入session到redis
        String jwt=jwtUtils.generateToken(wxSessionDto.getOpenid());
        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expose-Headers","Authorization");
        return Result.succ("登陆成功");
    }
}
