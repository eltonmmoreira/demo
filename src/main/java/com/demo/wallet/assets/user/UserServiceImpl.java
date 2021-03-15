package com.demo.wallet.assets.user;

import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NoResultException(String.format("Usuário não encontrado para o e-mail %s", email));
        }
        return user;
    }
}
