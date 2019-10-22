package by.sivko.cashsaving.controllers;

import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

class ControllerUtils {
    static Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String, String> map = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            String errorFieldName = fieldError.getField() + "Error";
            if (!map.containsKey(errorFieldName)) {
                map.put(errorFieldName, fieldError.getDefaultMessage());
            } else {
                map.put(errorFieldName, map.get(errorFieldName) + "\n" + fieldError.getDefaultMessage());
            }
        });
        return map;
    }
}
