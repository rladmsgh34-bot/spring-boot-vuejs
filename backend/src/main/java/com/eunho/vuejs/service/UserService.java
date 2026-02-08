package com.eunho.vuejs.service;

import com.eunho.vuejs.dto.ResponseUser;

public interface UserService {
    ResponseUser getUser(Long id);
}
