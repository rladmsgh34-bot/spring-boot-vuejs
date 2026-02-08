package com.eunho.vuejs.controller.rest;

import com.eunho.vuejs.dto.ResponseUser;
import com.eunho.vuejs.dto.UserDTO;
import com.eunho.vuejs.entity.User;
import com.eunho.vuejs.repository.UserRepository;
import com.eunho.vuejs.service.N8nService;
import com.eunho.vuejs.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/hello")
    public Response sayHello() {
        log.info("GET called on /hello resource");
        return new Response(HELLO_TEXT);
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User addNewUser (@RequestBody UserDTO request) {
        User user = new User(request.getFirstName(), request.getLastName());
        User saveUser = userRepository.save(user);

        log.info(saveUser.toString() + " successfully saved into DB");

        return saveUser;
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") long id) {
        log.info("Reading user with id " + id + " from database.");
        return userRepository.findById(id).get();
    }

    @GetMapping("/mapping/user")
    public ResponseUser getUser() {
        log.info("controller get User ");
        return userService.getUser(1L);
    }

    //inner class
    static class Response {
        private String data;

        public Response() {
        }

        public Response(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
