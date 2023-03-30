package com.ll.gramgram.boundedContext.member.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // 스프링부트 관련 컴포넌트 테스트할 때 붙여야 함, Ioc 컨테이너 작동시킴
@AutoConfigureMockMvc // http 요청, 응답 테스트
@Transactional
@ActiveProfiles("test")
public class MemberControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("회원가입 폼")
    void t001() throws Exception {
        ResultActions resultActions = mvc
                .perform(get("/member/join"))
                .andDo(print());

        resultActions
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("showJoin"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("회원가입")
    void t002() throws Exception {
        ResultActions resultActions = mvc
                .perform(post("/member/join")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .param("username", "user10")
                        .param("password", "1234"))
                .andDo(print());

        resultActions
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("join"))
                .andExpect(status().is2xxSuccessful());
    }
}
