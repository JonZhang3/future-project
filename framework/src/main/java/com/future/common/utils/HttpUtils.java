package com.future.common.utils;

import org.springframework.web.client.RestTemplate;

public final class HttpUtils {

    private HttpUtils() {
    }
    
    private static RestTemplate restTemplate = new RestTemplate();

}
