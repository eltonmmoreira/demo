package com.demo.wallet.assets.api;

import com.demo.wallet.assets.user.User;
import com.demo.wallet.assets.user.UserServiceImpl;
import com.demo.wallet.assets.wallet.Wallet;
import com.demo.wallet.assets.wallet.WalletRepository;
import com.demo.wallet.assets.wallet.api.WalletApi;
import com.demo.wallet.assets.wallet.api.WalletTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static com.demo.wallet.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class WalletApiTest {

    private static final String URI_PATH = "/wallet";

    @InjectMocks
    private WalletApi walletApi;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private WalletRepository walletRepository;

    private MockMvc mockMvc;

    private final ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(walletApi).build();
    }

    @Test
    public void whenPostIsCalledThenAWalletIsCreated() throws Exception {
        var email = "teste@teste.com";
        var walletTo = new WalletTo(null, "My Wallet", email);
        var user = new User(1L, email);

        var wallet = modelMapper.map(walletTo, Wallet.class);
        wallet.setId(1L);
        wallet.setUser(user);

        when(userService.findByEmail(email)).thenReturn(user);
        when(walletRepository.save(any())).thenReturn(wallet);

        mockMvc.perform(post(URI_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(walletTo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", is(walletTo.getDescription())))
                .andExpect(jsonPath("$.email", is(walletTo.getEmail())));
    }

    @Test
    public void whenPostIsCalledThenAWalletIsNoCreatedRequiredEmail() throws Exception {
        var walletTo = new WalletTo(null, "My Wallet", null);

        mockMvc.perform(post(URI_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(walletTo)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }
}
