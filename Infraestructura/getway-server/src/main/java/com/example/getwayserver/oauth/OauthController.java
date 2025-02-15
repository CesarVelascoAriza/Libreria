package com.example.getwayserver.oauth;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController("/api/security/")
public class OauthController {

    @GetMapping("/authorize")
    public ResponseEntity<?> getMethodName(@RequestParam String code) {
         Map<String, String> map = new HashMap<>();
        map.put("code", code);
        return ResponseEntity.ok().body(map);
    }
    

}
