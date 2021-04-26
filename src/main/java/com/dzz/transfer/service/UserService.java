package com.dzz.transfer.service;

import com.dzz.transfer.exception.TransferException;
import com.dzz.transfer.model.pojo.User;

/**
 * 用户逻辑Service
 */
public interface UserService {
    User getUser();

    void register(String userName, String password) throws TransferException;

    User login(String userName, String password) throws TransferException;

    void updateInformation(User user) throws TransferException;

    boolean checkAdminRole(User user);
}
