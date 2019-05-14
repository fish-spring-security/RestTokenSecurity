package com.github.fish56.resttokensecurity.web;

import com.github.fish56.resttokensecurity.RestTokenSecurityApplicationTests;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;

public class RootControllerTest extends RestTokenSecurityApplicationTests {

    private String adminToken = "t1001";
    private String vipToken = "t2001";
    private String normalToken = "t3001";

    @Test
    public void index() throws Exception{
        ResultMatcher isOk = MockMvcResultMatchers.status().is(200);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/");

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(isOk);
    }

    /**
     * 未登录用户无权访问
     * @throws Exception
     */
    @Test
    public void home() throws Exception{
        ResultMatcher is403 = MockMvcResultMatchers.status().is(403);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/home");

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(is403);
    }

    @Test
    public void vip() throws Exception{
        ResultMatcher isOk = MockMvcResultMatchers.status().is(200);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/vip")
                        .header("Authorization", "bearer " + vipToken);

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(isOk);
    }

    /**
     * 非vip用户禁止访问
     * @throws Exception
     */
    @Test
    public void nonVip() throws Exception{
        ResultMatcher is403 = MockMvcResultMatchers.status().is(403);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/vip")
                        .header("Authorization", "bearer " + normalToken);

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(is403);
    }

    @Test
    public void admin() throws Exception{
        ResultMatcher isOk = MockMvcResultMatchers.status().is(200);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/admin")
                        .header("Authorization", "bearer " + adminToken);

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(isOk);
    }

    /**
     * 非admin用户禁止访问
     * @throws Exception
     */
    @Test
    public void nonAdmin() throws Exception{
        ResultMatcher is403 = MockMvcResultMatchers.status().is(403);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/admin")
                        .header("Authorization", "bearer " + vipToken);

        mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(is403);
    }
}