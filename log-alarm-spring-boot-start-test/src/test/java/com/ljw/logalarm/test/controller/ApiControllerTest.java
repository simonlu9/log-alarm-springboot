package com.ljw.logalarm.test.controller;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ljw.logalarm.autoconfigure.LogAlarmAutoConfiguration;
import com.ljw.logalarm.test.service.AsyncService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author lujianwen@wsyxmall.com
 * @since 2025-01-03 16:32
 */
@WebMvcTest(value = ApiController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
//@SpringBootTest(classes = {ApiController.class})
//@AutoConfigureMockMvc
@Import(value = {LogAlarmAutoConfiguration.class})
@ActiveProfiles("local")
class ApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    AsyncService asyncService;

    @Test
    void greet() throws Exception {
        mockMvc.perform(get("/api/greet"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, World!"));
        System.in.read();
    }
    @Test
    void timeout() throws Exception {
        mockMvc.perform(get("/api/timeout"))
                .andExpect(status().isOk())
                .andExpect(content().string("timeout"));
        System.in.read();
    }

    @Test
    void async() {
    }

    @Test
    void error() throws Exception {
        mockMvc.perform(get("/api/error")
                       // .contentType(MediaType.APPLICATION_JSON)
                       // .characterEncoding(Charset.defaultCharset())
                        //.content("{\"name\": \"test\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, World!"));
        System.in.read();
    }

    @Test
    void json() throws Exception {
        mockMvc.perform(post("/api/json")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.defaultCharset())
                        .content("{\"name\": \"test\"}") )
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, World!"));
        System.in.read();
    }
    @Test
    void form() throws Exception {
        mockMvc.perform(post("/api/form")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .characterEncoding(Charset.defaultCharset())
                        .param("name", "John")
                        .param("age", "253454")
                        )
                .andExpect(status().isOk())
                .andExpect(content().string("Form data received!"));
        System.in.read();
    }
    @Test
    void login() throws Exception {
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .characterEncoding(Charset.defaultCharset())
                        .param("username", "John")
                        .param("password", "253454")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Form data received!"));
        System.in.read();
    }

    @Test
    void http() {
    }

    @Test
    void file() {
    }
}