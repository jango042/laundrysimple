package com.jango.laundrysimple.service;

import com.jango.laundrysimple.pojo.request.UserEditRequest;
import com.jango.laundrysimple.pojo.request.UserRequest;
import com.jango.laundrysimple.pojo.response.Response;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    Response createUser(UserRequest userRequest);
    Response editUser(UserEditRequest user);
    Response findAllUsers();
    Response findUserByEmail(String email);
    Response deleteUser(Long userId);
}
