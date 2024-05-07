package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    //微信服务接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

    /**
     * 微信登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        String openid = getOpenid(userLoginDTO.getCode());

        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        User user = userMapper.getByOpenId(openid);

        if (user == null) {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now()).build();

            userMapper.insert(user);
        }
        return user;
    }

    /**
     * 调用微信接口服务，获取微信用户的openid
     *
     * @param code
     * @return
     */
    private String getOpenid(String code) {
        log.info("coed:" + code);
        //调用微信接口服务，获得当前微信用户的openid
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN, map);

        JSONObject jsonObject = JSON.parseObject(json);
        System.out.println(jsonObject);
        String openid = jsonObject.getString("openid");
        log.info("openid: " + openid);
        return openid;
    }

//    public static void main(String[] args) {
//        Map<String, String> map = new HashMap<>();
//        map.put("appid", "wx7c00a3501c8d7004");
//        map.put("secret", "b5876c1c062a50596e2d8f1a01e17274");
////        map.put("js_code", "0c3y3f100nKXQR1Qa6200ZTQDv2y3f1w");
//        map.put("js_code", "0b3gHb1w3Xvgw23X8q2w3XzDpF1gHb1E");
//        map.put("grant_type", "authorization_code");
//        String json = HttpClientUtil.doGet(WX_LOGIN, map);
//
//        JSONObject jsonObject = JSON.parseObject(json);
//        String openid = jsonObject.getString("openid");
//        System.out.println(openid);
//    }
}
