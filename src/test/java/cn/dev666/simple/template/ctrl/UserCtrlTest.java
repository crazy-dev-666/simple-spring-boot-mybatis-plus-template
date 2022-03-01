package cn.dev666.simple.template.ctrl;

import cn.dev666.simple.template.ApplicationTests;
import cn.dev666.simple.template.obj.common.Msg;
import cn.dev666.simple.template.obj.common.oto.IdOTO;
import cn.dev666.simple.template.obj.ito.user.UserModifyITO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserCtrlTest extends ApplicationTests {

    private static final String URL_PREFIX = "/api/user/v1";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8).build();
    }

    @Test
    void testAll() throws Exception{

        UserModifyITO user = getUser();

        Long id = add(user);

        getOne(id);

        user.setId(id);
        update(user);

        delete(id);
    }


    Long add(UserModifyITO user) throws Exception {
        MvcResult result = mockMvc.perform(post(URL_PREFIX + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String data = result.getResponse().getContentAsString();
        TypeReference<Msg<IdOTO>> reference = new TypeReference<Msg<IdOTO>>(){};
        Msg<IdOTO> msg = objectMapper.readValue(data, reference);
        return Long.valueOf(msg.getData().getId().toString());
    }

    void getOne(Long id) throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/get/" + id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    void update(UserModifyITO user) throws Exception {
        mockMvc.perform(put(URL_PREFIX + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    void delete(Long id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(URL_PREFIX + "/delete/" + id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private UserModifyITO getUser(){
        UserModifyITO ito = new UserModifyITO();
        ito.setBirthday(LocalDate.of(2022, 1, 1));
        ito.setCreator(1L);
        ito.setDeleteFlag(0);
        ito.setEmail("123@dev666.cn");
        ito.setModifier(1L);
        ito.setPhoneNumber("12345678910");
        ito.setRealName("李四");
        ito.setUserImage("123");
        ito.setUsername("dev666");
        ito.setWorkStartTime(LocalTime.of(9,0));
        ito.setWorkEndTime(LocalTime.of(18,0));
        return ito;
    }
}