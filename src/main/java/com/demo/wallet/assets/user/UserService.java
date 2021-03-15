package com.demo.wallet.assets.user;

public interface UserService {

    User save(User user);

    void delete(Long id);

    User findByEmail(String email);

}
