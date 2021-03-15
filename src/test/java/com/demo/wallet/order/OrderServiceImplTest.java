package com.demo.wallet.order;

import com.demo.wallet.assets.order.Order;
import com.demo.wallet.assets.order.OrderRepository;
import com.demo.wallet.assets.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    public void saveAndPublishPostCreatedEvent() {
        var order = Order.builder()
                .build();

        var savedOrder = Order.builder()
                .id(1L)
                .build();

        Mockito.when(orderRepository.save(any())).thenReturn(savedOrder);

        savedOrder = orderService.save(order);

        verify(applicationEventPublisher).publishEvent(any());
    }

    @Test
    public void deleteThrowExceptionWhenNotIdExists() {
        doThrow(new EmptyResultDataAccessException(1))
                .when(orderRepository).deleteById(1L);

        assertThrows(NoResultException.class, () -> orderService.delete(1L));
    }
}
