package jp.mikunika.SpringBootInsurance;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.mikunika.SpringBootInsurance.controller.InsuranceClientController;
import jp.mikunika.SpringBootInsurance.dto.InsuranceClientDto;
import jp.mikunika.SpringBootInsurance.model.InsuranceClient;
import jp.mikunika.SpringBootInsurance.service.InsuranceClientService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(InsuranceClientController.class)
public class InsuranceClientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    InsuranceClientService clientService;

    InsuranceClient CLIENT_1 = InsuranceClient.builder().id(1L).name("First One").birth(2019).build();
    InsuranceClient CLIENT_2 = InsuranceClient.builder().id(2L).name("Second One").birth(2020).build();
    InsuranceClient CLIENT_3 = InsuranceClient.builder().id(3L).name("Third One").birth(2021).build();

    @Test
    public void getAll_ok() throws Exception {
        List<InsuranceClient> clients = new ArrayList<>(Arrays.asList(CLIENT_1, CLIENT_2, CLIENT_3));

        Mockito.when(clientService.findAll()).thenReturn(clients);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/clients")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("Third One")))
                .andExpect(jsonPath("$[2].birth", is(2021)))
                .andExpect(jsonPath("$[0].name", is("First One")))
                .andExpect(jsonPath("$[0].birth", is(2019)));
    }

    @Test
    public void getOne_ok() throws Exception {
        Mockito.when(clientService.findOne(CLIENT_1.getId())).thenReturn(CLIENT_1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/clients/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("First One")))
                .andExpect(jsonPath("$.birth", is(2019)));
    }

    @Test
    public void getOne_bad() throws Exception {
        Mockito.doThrow(new EntityNotFoundException()).when(clientService).findOne(100L);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/clients/100")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is("Entity does not found!")));
    }

    @Test
    public void createRecord_ok() throws Exception {
        InsuranceClient newClient = InsuranceClient.builder().name("Fourth One").birth(2021).build();

        Mockito.when(clientService.save(ArgumentMatchers.any(InsuranceClient.class))).thenReturn(newClient);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(InsuranceClientDto.from(newClient)));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Fourth One")));
    }

    @Test
    public void updateRecord_ok() throws Exception {
        InsuranceClient newClient = InsuranceClient.builder().id(1L).name("Fourth One").birth(2021).build();

        Mockito.when(clientService.findOne(CLIENT_1.getId())).thenReturn(CLIENT_1);
        Mockito.when(clientService.update(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(InsuranceClient.class))).thenReturn(newClient);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/clients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(InsuranceClientDto.from(newClient)));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Fourth One")));
    }

    @Test
    public void deletePatientById_ok() throws Exception {
        Mockito.when(clientService.findOne(CLIENT_2.getId())).thenReturn(CLIENT_2);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/clients/2")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void deletePatientById_bad() throws Exception {
        Mockito.doThrow(new EmptyResultDataAccessException(100)).when(clientService).delete(100L);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/clients/100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is("Entity does not exist!")));
    }
}
