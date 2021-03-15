package com.demo.wallet.assets.order.event;

import com.demo.wallet.assets.order.Order;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class PostCreateOrderEvent extends ApplicationEvent {

    @Getter
    private final Order order;

    public PostCreateOrderEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }
}
