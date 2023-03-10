package com.daniilPLatotonov.d.Service;

import com.daniilPLatotonov.d.model.request.CreateUserRequest;
import com.daniilPLatotonov.d.model.response.UserResponse;
import com.sun.istack.NotNull;

import java.util.List;

public interface UserService {

    @NotNull
    List<UserResponse> findAll();

    @NotNull
    UserResponse findById(@NotNull Integer userId);

    @NotNull
    UserResponse createUser(@NotNull CreateUserRequest request);

    @NotNull
    UserResponse update(@NotNull Integer userId, @NotNull CreateUserRequest request);

    void delete(@NotNull Integer userId);
}
