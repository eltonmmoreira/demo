package com.demo.wallet.user.api;

import com.demo.wallet.assets.user.User;
import com.demo.wallet.assets.user.UserServiceImpl;
import com.demo.wallet.assets.user.api.UserApi;
import com.demo.wallet.assets.user.api.UserTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.demo.wallet.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserApiTest {

    private static final String URI_PATH = "/user";

    @InjectMocks
    private UserApi userApi;

    @Mock
    private UserServiceImpl userService;

    private MockMvc mockMvc;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userApi).build();
    }

    @Test
    public void userIsCreatedAndReturnSatus201() throws Exception {
        var email = "teste@teste.com";
        var userTo = new UserTo(null, email);
        var userCreated = new User(1L, email);

        Mockito.when(userService.save(any())).thenReturn(userCreated);

        mockMvc.perform(post(URI_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userTo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())));
    }

}
