package com.demo.wallet.quote.listener;

import lombok.extern.log4j.Log4j2;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

@Log4j2
public class CustomCacheEventLogger implements CacheEventListener<Object, Object> {

    @Override
    public void onEvent(CacheEvent cacheEvent) {
        log.info("custom Caching event {} {} {} {} ",
                cacheEvent.getType(),
                cacheEvent.getKey(),
                cacheEvent.getOldValue(),
                cacheEvent.getNewValue()
        );
    }
}