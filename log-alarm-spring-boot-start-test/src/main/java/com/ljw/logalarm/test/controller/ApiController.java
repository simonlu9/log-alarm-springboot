package com.ljw.logalarm.test.controller;


import com.ljw.logalarm.test.req.FooReq;
import com.ljw.logalarm.test.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {
    @Autowired
    private AsyncService asyncService;
    @GetMapping("/greet")
    public ResponseEntity<String> greet() {
        log.error("greet");
        return ResponseEntity.ok("Hello, World!");
    }
    @GetMapping("/async")
    public ResponseEntity<String> async() {
        asyncService.test();
        return ResponseEntity.ok("async");
    }
    @GetMapping("/error")
    public ResponseEntity<String> error() {
         if(1+2>0){
             throw new RuntimeException("sdf");
         }
        return ResponseEntity.ok("error");
    }
    @GetMapping("/timeout")
    public ResponseEntity<String> timeout() throws InterruptedException {
        Thread.sleep(2000);
        return ResponseEntity.ok("timeout");
    }
    @PostMapping("/json")
    public ResponseEntity<String> json(@RequestBody FooReq req) {
        //log.error("error");
        log.error(req.toString());
        return ResponseEntity.ok("error");
    }
    @GetMapping("/http")
    public ResponseEntity<String> http() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.getForEntity("http://v2.haokan123.icu",String.class);
        log.info(result.toString());
        return ResponseEntity.ok("http");
    }
    @PostMapping("/file")
    public ResponseEntity<String> file(@RequestParam MultipartFile file){
        return ResponseEntity.ok("file");
    }


}