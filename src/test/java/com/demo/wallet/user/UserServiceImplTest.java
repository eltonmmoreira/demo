package com.demo.wallet.user;

import com.demo.wallet.assets.user.User;
import com.demo.wallet.assets.user.UserRepository;
import com.demo.wallet.assets.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void saveUser() {
        final String email = "teste@teste.com";
        var user = new User(null, email);
        var userReturn = new User(1L, email);
        Mockito.when(userRepository.save(user)).thenReturn(userReturn);

        User save = userService.save(user);

        assertThat(save.getId(), equalTo(userReturn.getId()));
        assertThat(save.getEmail(), equalTo(userReturn.getEmail()));
    }

}
