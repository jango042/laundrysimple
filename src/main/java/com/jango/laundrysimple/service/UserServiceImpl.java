package com.jango.laundrysimple.service;

import com.jango.laundrysimple.model.Address;
import com.jango.laundrysimple.model.Roles;
import com.jango.laundrysimple.model.User;
import com.jango.laundrysimple.pojo.AddressPojo;
import com.jango.laundrysimple.pojo.request.UserEditRequest;
import com.jango.laundrysimple.pojo.request.UserRequest;
import com.jango.laundrysimple.pojo.response.Response;
import com.jango.laundrysimple.pojo.response.UserResponsePojo;
import com.jango.laundrysimple.repository.AddressRepository;
import com.jango.laundrysimple.repository.RolesRepository;
import com.jango.laundrysimple.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private RolesRepository rolesRepository;


    @Transactional
    public Response createUser(UserRequest userRequest) {

        try {
            if (checkIfUserExistByEmail(userRequest.getEmail())) {
                return new Response.ResponseBuilder<>()
                        .code(300)
                        .status(false)
                        .message("User Already Exists, user a different email and try again")
                        .build();
            }
            User user = new ModelMapper().map(userRequest, User.class);
            user.setId(0L);
            user.setRolesList(getRoleList());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            user.setRegistrationTime(LocalDateTime.now());
            log.info("DOB::::{} ", LocalDate.now());

            User savedUser = userRepository.save(user);


            Address address = getAddress(userRequest, savedUser);

            savedUser.setAddressId(address.getId());

            userRepository.save(savedUser);

            UserResponsePojo userResponsePojo = getUserResponsePojo(savedUser, address);

            return new Response.ResponseBuilder<>()
                    .message("Saved Successfully")
                    .code(200)
                    .status(true)
                    .data(userResponsePojo)
                    .build();
        } catch (Exception e) {
            return new Response.ResponseBuilder<>()
                    .code(500)
                    .status(false)
                    .message(e.getMessage())
                    .build();
        }


    }

    private Address getAddress(UserRequest userRequest, User savedUser) {
        Address mAddress = new Address();
        mAddress.setDistrict(userRequest.getAddress().getDistrict());
        mAddress.setId(0L);
        mAddress.setHouseAddress(userRequest.getAddress().getHouseAddress());
        mAddress.setIsDefault(true);
        mAddress.setUserId(savedUser.getId());

        return addressRepository.save(mAddress);
    }

    private UserResponsePojo getUserResponsePojo(User savedUser, Address address) {
        UserResponsePojo userResponsePojo = new UserResponsePojo();
        userResponsePojo.setId(savedUser.getId());
        userResponsePojo.setAddress(new ModelMapper().map(address, AddressPojo.class));
        userResponsePojo.setDateOfBirth(savedUser.getDateOfBirth());
        userResponsePojo.setEmail(savedUser.getEmail());
        userResponsePojo.setFirstName(savedUser.getFirstName());
        userResponsePojo.setGender(savedUser.getGender());
        userResponsePojo.setLastName(savedUser.getLastName());
        userResponsePojo.setPhone(savedUser.getPhone());
        return userResponsePojo;
    }

    public Response editUser(UserEditRequest user) {
        Optional<User> savedUser = userRepository.findByEmail(user.getEmail()).map(mUser -> {
            mUser.setFirstName(user.getFirstName());
            mUser.setGender(user.getGender());
            mUser.setLastName(user.getLastName());
            return userRepository.save(mUser);

        });

        Address address = getAddressByUserId(savedUser.get().getId());
        if (address != null) {
            UserResponsePojo userResponsePojo = getUserResponsePojo(savedUser.get(), address);
            return new Response.ResponseBuilder<>()
                    .data(userResponsePojo)
                    .status(true)
                    .message("User Edited Successfully")
                    .code(200)
                    .build();
        } else {
            return new Response.ResponseBuilder<>()
                    .message("Error Occurred")
                    .code(500)
                    .status(false)
                    .build();
        }

    }

    public Response findAllUsers() {
        return new Response.ResponseBuilder<>()
                .code(200)
                .status(true)
                .data(userRepository.findAll())
                .build();
    }

    public Response findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            Address address = getAddressByUserId(user.get().getId());
            UserResponsePojo userResponsePojo = getUserResponsePojo(user.get(), address);
            return new Response.ResponseBuilder<>()
                    .data(userResponsePojo)
                    .code(200)
                    .status(true)
                    .message("Data Pulled Successfully")
                    .build();
        } else {
            return new Response.ResponseBuilder<>()
                    .code(500)
                    .status(false)
                    .message("Error Occurred")
                    .build();
        }
    }

    public Response deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return new Response.ResponseBuilder<>()
                    .code(200)
                    .status(true)
                    .message("User with Id "+userId+" deleted successfully")
                    .build();
        } else {
            return new Response.ResponseBuilder<>()
                    .code(500)
                    .status(false)
                    .message("Error Occurred")
                    .build();
        }
    }

    private Address getAddressByUserId(Long userId) {
        Optional<Address> address = addressRepository.findById(userId);
        if (address.isPresent()) {
            return address.get();
        } else {
            return null;
        }
    }



    private List<Roles> getRoleList() {
        List<Roles> rolesList = new ArrayList<>();
        Optional<Roles> role = rolesRepository.findById(2);
        rolesList.add(role.get());
        return rolesList;
    }

    private boolean checkIfUserExistByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Loading User.....");
        Optional<User> user = userRepository.findByEmail(email);

        List<User> allUsers = userRepository.findAll();

        if (!user.isPresent()) {
            throw new UsernameNotFoundException(email);
        }else {
            return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
                    user.get().getPassword(), true, true, true, true, new ArrayList<>());

        }
    }

}
