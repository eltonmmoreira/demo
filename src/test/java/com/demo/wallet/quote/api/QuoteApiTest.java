package com.demo.wallet.quote.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class QuoteApiTest {

    private static final String URI_PATH = "/quote";

    @InjectMocks
    private QuoteApi quoteApi;

    @Mock
    private QuoteClient quoteClient;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(quoteApi).build();
    }

    @Test
    public void returnedOkStatusWhenGetIsCalled() throws Exception {
        final String BBAS3 = "BBAS3";
        final String ITSA4 = "ITSA4";

        var quoteWrapper = new QuoteWrapper(List.of(
                Quote.builder()
                        .symbol(BBAS3)
                        .build(),
                Quote.builder()
                        .symbol(ITSA4)
                        .build()
        ));

        Mockito.when(quoteClient.findAll()).thenReturn(quoteWrapper);

        mockMvc.perform(get(URI_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].symbol", is(equalTo(BBAS3))))
                .andExpect(jsonPath("$[1].symbol", is(equalTo(ITSA4))));
    }
}
