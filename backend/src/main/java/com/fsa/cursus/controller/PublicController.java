package com.fsa.cursus.controller;

import com.fsa.cursus.model.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @GetMapping
    public ResponseEntity<ApiResponse> showData() {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("dataCode", "200");
        metadata.put("dataMessage", new Date().toString());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(metadata);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}

