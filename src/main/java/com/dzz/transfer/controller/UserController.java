package com.dzz.transfer.controller;

import com.dzz.transfer.common.ApiRestResponse;
import com.dzz.transfer.common.Constant;
import com.dzz.transfer.exception.TransferException;
import com.dzz.transfer.exception.TransferExceptionEnum;
import com.dzz.transfer.model.pojo.User;
import com.dzz.transfer.service.UserService;
import com.dzz.transfer.util.MD5Utils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 描述:用户控制器
 */
@Controller
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/test")
    @ResponseBody
    public User personalPage(){
        return userService.getUser();
    }


    /**
     * 注册接口
     * @param userName
     * @param password
     * @return
     * @throws TransferException
     */
    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("userName") String userName, @RequestParam("password") String password) throws TransferException {
        if (StringUtils.isEmpty(userName)) {
            return ApiRestResponse.error(TransferExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(TransferExceptionEnum.NEED_PASSWORD);
        }
        //密码长度不能少于8位
        if (password.length() < 8) {
            return ApiRestResponse.error(TransferExceptionEnum.PASSWORD_TOO_SHORT);
        }
        userService.register(userName, password);
        return ApiRestResponse.success();
    }

    /**
     * 登陆接口
     * @param userName
     * @param password
     * @param session
     * @return
     * @throws TransferException
     */
    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession session) throws TransferException {
        if (StringUtils.isEmpty(userName)) {
            return ApiRestResponse.error(TransferExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(TransferExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        session.setAttribute(Constant.TRANSFER_USER, user);
        user.setPassword(null);
        return ApiRestResponse.success(user);
    }

    /**
     * 更改密码接口
     * @param session
     * @param password
     * @return
     * @throws TransferException
     */
    @PostMapping("/user/update")
    @ResponseBody
    public ApiRestResponse updateUserInfo(HttpSession session, @RequestParam("password") String password) throws TransferException {
        User currentUser = (User) session.getAttribute(Constant.TRANSFER_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(TransferExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        String md5Password = null;
        md5Password = MD5Utils.getMD5Str(password);
        user.setPassword(md5Password);
        userService.updateInformation(user);
        return ApiRestResponse.success();
    }

    /**
     * 登出接口，清除session
     * @param session
     * @return
     */
    @PostMapping("/user/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session) {
        session.removeAttribute(Constant.TRANSFER_USER);
        return ApiRestResponse.success();
    }

    /**
     * 管理员登陆接口
     * @param userName
     * @param password
     * @param session
     * @return
     * @throws TransferException
     */
    @PostMapping("/adminLogin")
    @ResponseBody
    public ApiRestResponse adminLogin(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession session) throws TransferException {
        if (StringUtils.isEmpty(userName)) {
            return ApiRestResponse.error(TransferExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(TransferExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        //校验是否是管理员
        if (userService.checkAdminRole(user)) {
            //是管理员，执行操作
            //保存用户信息，不保存密码
            session.setAttribute(Constant.TRANSFER_USER, user);
            user.setPassword(null);
            return ApiRestResponse.success(user);
        } else {
            return ApiRestResponse.error(TransferExceptionEnum.NEED_ADMIN);
        }


    }
}
