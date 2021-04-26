package com.dzz.transfer.service.impl;

import com.dzz.transfer.exception.TransferException;
import com.dzz.transfer.exception.TransferExceptionEnum;
import com.dzz.transfer.model.dao.UserMapper;
import com.dzz.transfer.model.pojo.User;
import com.dzz.transfer.service.UserService;
import com.dzz.transfer.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 描述：UserService实现类
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey(4);
    }

    /**
     * 注册用户功能
     * @param userName
     * @param password
     * @throws TransferException
     */
    @Override
    public void register(String userName, String password) throws TransferException {
        //查询用户名是否存在，不允许重名
        User result = userMapper.selectByName(userName);
        if (result != null) {
            throw new TransferException(TransferExceptionEnum.NAME_EXISTED);
        }
        //写到数据库
        User user = new User();
        user.setUsername(userName);
        user.setPassword(MD5Utils.getMD5Str(password));
        int count = userMapper.insertSelective(user);
        if (count == 0) {
            throw new TransferException(TransferExceptionEnum.INSERT_FAILED);
        }
    }

    /**
     * 登陆功能
     * @param userName
     * @param password
     * @return
     * @throws TransferException
     */
    @Override
    public User login(String userName, String password) throws TransferException {
        String md5Password = null;
        md5Password = MD5Utils.getMD5Str(password);
        User user = userMapper.selectLogin(userName, md5Password);
        if (user == null) {
            throw new TransferException(TransferExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }

    /**
     * 更新密码
     * @param user
     * @throws TransferException
     */
    @Override
    public void updateInformation(User user) throws TransferException {
        //更改密码
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 1) {
            throw new TransferException(TransferExceptionEnum.UPDATE_FAILED);
        }
    }

    /**
     * 检查用户权限
     * @param user
     * @return
     */
    @Override
    public boolean checkAdminRole(User user) {
        return user.getRole().equals(2);//1是普通用户，2是管理员
    }
}
