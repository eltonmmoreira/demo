package com.demo.wallet.quote.api;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Quote implements Serializable {
    private Double change;
    private Double closingPrice;
    private Double eps;
    private Double high;
    private Double lastPrice;
    private Double lastYearHigh;
    private Double lastYearLow;
    private Double low;
    private Long marketCap;
    private String name;
    private Double priceOpen;
    private Long shares;
    private String symbol;
    private Long volume;
    private Long volumeAvg;
    private String sector;
    private String subSector;
    private String segment;

    @Override
    public String toString() {
        return "Quote{" +
                "  name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", change=" + change +
                ", lastPrice=" + lastPrice +
                '}';
    }
}
