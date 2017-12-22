package com.ramannada.springdemo.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BaseController {
    protected final Locale ID = new Locale("id");

    protected Map<String, Object> convertModel(Object data, Object status) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("status", status);
        return response;
    }
}
