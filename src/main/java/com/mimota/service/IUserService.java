package com.mimota.service;


import com.mimota.pojo.User;
import com.mimota.util.common.ServerResponse;

import java.util.Map;

/**
 * Created by geely
 */
public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username, String question, String answer);

    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    ServerResponse<User> updateInformation(User user, Map<String, String> conditions);

    ServerResponse<User> getInformation(String userId);

    ServerResponse checkAdminRole(User user);
}
