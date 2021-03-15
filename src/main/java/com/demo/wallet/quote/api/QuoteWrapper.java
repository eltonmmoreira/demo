package com.demo.wallet.quote.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuoteWrapper {

    @JsonProperty("stocks")
    private List<Quote> allQuote = new ArrayList<>();

}
