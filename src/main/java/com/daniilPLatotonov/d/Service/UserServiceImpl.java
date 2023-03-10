package com.daniilPLatotonov.d.Service;

import com.daniilPLatotonov.d.domain.Address;
import com.daniilPLatotonov.d.domain.User;
import com.daniilPLatotonov.d.model.request.CreateAddressRequest;
import com.daniilPLatotonov.d.model.request.CreateUserRequest;
import com.daniilPLatotonov.d.model.response.AddressResponse;
import com.daniilPLatotonov.d.model.response.UserResponse;
import com.daniilPLatotonov.d.repository.UserRepository;
import com.sun.istack.NotNull;


import static java.util.Optional.ofNullable;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private static UserRepository userRepository;

    @NotNull
    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::buildUserResponse)
                .collect(Collectors.toList());
    }

    @NotNull
    private UserResponse buildUserResponse(@NotNull User user) {
        return new UserResponse()
                .setId(user.getId())
                .setLogin(user.getLogin())
                .setAge(user.getAge())
                .setFirstName(user.getFirstName())
                .setMiddleName(user.getMiddleName())
                .setLastName(user.getLastName())
                .setAddress(new AddressResponse()
                        .setCity(user.getAddress().getCity())
                        .setBuilding(user.getAddress().getBuilding())
                        .setStreet(user.getAddress().getStreet()));
    }

    @NotNull
    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(@NotNull Integer userId) {
        return userRepository.findById(userId)
                .map(this::buildUserResponse)
                .orElseThrow(() -> new EntityNotFoundException("User " + userId + " is not found"));
    }

    @NotNull
    @Override
    @Transactional
    public UserResponse createUser(@NotNull CreateUserRequest request) {
        User user = buildUserRequest(request);
        return buildUserResponse(userRepository.save(user));
    }

    @NotNull
    private User buildUserRequest(@NotNull CreateUserRequest request) {
        return new User()
                .setLogin(request.getLogin())
                .setAge(request.getAge())
                .setFirstName(request.getFirstName())
                .setMiddleName(request.getMiddleName())
                .setLastName(request.getLastName())
                .setAddress(new Address()
                        .setCity(request.getAddress().getCity())
                        .setBuilding(request.getAddress().getBuilding())
                        .setStreet(request.getAddress().getStreet()));
    }

    @NotNull
    @Override
    @Transactional
    public UserResponse update(@NotNull Integer userId, @NotNull CreateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User " + userId + " is not found"));
        userUpdate(user, request);
        return buildUserResponse(userRepository.save(user));
    }

    private void userUpdate(@NotNull User user, @NotNull CreateUserRequest request) {
        ofNullable(request.getLogin()).map(user::setLogin);
        ofNullable(request.getFirstName()).map(user::setFirstName);
        ofNullable(request.getMiddleName()).map(user::setMiddleName);
        ofNullable(request.getLastName()).map(user::setLastName);
        ofNullable(request.getAge()).map(user::setAge);

        CreateAddressRequest addressRequest = request.getAddress();
        if (addressRequest != null) {
            ofNullable(addressRequest.getBuilding()).map(user.getAddress()::setBuilding);
            ofNullable(addressRequest.getStreet()).map(user.getAddress()::setStreet);
            ofNullable(addressRequest.getCity()).map(user.getAddress()::setCity);
        }
    }


    @Override
    @Transactional
    public void delete(@NotNull Integer userId) {
        userRepository.deleteById(userId);
    }
}
