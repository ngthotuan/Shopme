package com.shopme.admin.setting.state;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.State;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StateRestControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private StateRepository repo;

    @Test
    @WithMockUser(authorities = "Admin")
    public void testListByCountry() throws Exception {
        long countryId = 242;
        String url = "/states/list?countryId=" + countryId;
        MvcResult result = mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        System.out.println(jsonResult);
        State[] states = objectMapper.readValue(jsonResult, State[].class);
        assertThat(states.length).isGreaterThan(0);
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void testCreateState() throws Exception {
        long countryId = 242;
        Country country = new Country(countryId);
        State state = new State("D.NAI", country);
        String url = "/states/save";
        System.out.println("JSON: " + objectMapper.writeValueAsString(state));
        MvcResult result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(state)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        System.out.println(jsonResult);

        State stateResult = objectMapper.readValue(jsonResult, State.class);
        assertThat(stateResult.getId()).isNotNull();
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void testUpdateState() throws Exception {
        long stateId = 307;
        State state = repo.findById(stateId).get();
        state.setName("D.NAI - UPDATED");
        String url = "/states/save";
        MvcResult result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(state)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        System.out.println(jsonResult);
        State stateResult = objectMapper.readValue(jsonResult, State.class);
        assertThat(stateResult.getName()).isEqualTo("D.NAI - UPDATED");
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void testDeleteState() throws Exception {
        long stateId = 307;
        String url = "/states/delete/" + stateId;
        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(repo.findById(stateId)).isEmpty();
    }
}
