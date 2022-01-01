package com.shopme.admin.setting.country;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopme.common.entity.Country;
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
public class CountryRestControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CountryRepository repo;

    @Test
    @WithMockUser(authorities = "Admin")
    public void testListCountries() throws Exception {
        String url = "/countries/list";
        MvcResult result = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String jsonString = result.getResponse().getContentAsString();
        Country[] countries = objectMapper.readValue(jsonString, Country[].class);
        assertThat(countries).isNotEmpty();
    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void testCreateCountry() throws Exception {
        String url = "/countries/save";
        Country country = new Country("Test Country", "TC");
        MvcResult result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(country)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        Country savedCountry = objectMapper.readValue(jsonResponse, Country.class);
        System.out.println(savedCountry);
        assertThat(savedCountry.getId()).isNotNull();

    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void testUpdateCountry() throws Exception {
        String url = "/countries/save";
        Country country = new Country(4, "Test Country update", "TCU");
        MvcResult result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(country)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        Country savedCountry = objectMapper.readValue(jsonResponse, Country.class);
        System.out.println(savedCountry);
        assertThat(savedCountry.getCode()).isEqualTo("TCU");

    }

    @Test
    @WithMockUser(authorities = "Admin")
    public void testDeleteCountry() throws Exception {
        long countryId = 4;
        String url = "/countries/delete/" + countryId;
        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk());

        Country country = repo.findById(countryId).orElse(null);
        assertThat(country).isNull();
    }
}
