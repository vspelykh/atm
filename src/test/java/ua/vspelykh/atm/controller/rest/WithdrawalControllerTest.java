package ua.vspelykh.atm.controller.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.vspelykh.atm.controller.utils.WithdrawRequest;
import ua.vspelykh.atm.model.dto.BanknoteDTO;
import ua.vspelykh.atm.service.strategy.StrategyTestData;
import ua.vspelykh.atm.service.strategy.StrategyType;
import ua.vspelykh.atm.util.exception.ServiceException;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@AutoConfigureMockMvc
class WithdrawalControllerTest {

    private Principal mockPrincipal;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockPrincipal = Mockito.mock(Principal.class);
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        Mockito.when(mockPrincipal.getName()).thenReturn("1234567890123456");
    }

    @Test
    void makeWithdrawSuccess() throws Exception {
        WithdrawRequest request = new WithdrawRequest(100, StrategyType.SMALL);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
                .content(new ObjectMapper().writeValueAsString(request));

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        List<BanknoteDTO> expected = StrategyTestData.resDTO1();

        String responseContent = mvcResult.getResponse().getContentAsString();
        List<BanknoteDTO> actualBanknoteList = new ObjectMapper()
                .readValue(responseContent, new TypeReference<>() {
                });
        actualBanknoteList = actualBanknoteList.stream().sorted(Comparator.comparing(BanknoteDTO::getDenomination)).toList();
        assertEquals(expected, actualBanknoteList);
    }

    @Test
    void getPossibleStrategiesSuccess() throws Exception {
        List<StrategyType> result = List.of(StrategyType.BIG, StrategyType.SMALL);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/strategies")
                .param("amount", "100")
                .principal(mockPrincipal)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk()).andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<StrategyType> actualResult = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });

        assertEquals(result, actualResult);
    }

    @Test
    void getPossibleStrategiesFailedZeroBug() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("1234567890123456");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/strategies")
                .param("amount", "0")
                .principal(mockPrincipal)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ServiceException));
    }

    @Test
    void getPossibleStrategiesFailedNullPoint() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("1234567890123456");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/strategies")
//                .param("amount", "q")
                .principal(mockPrincipal)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertNotNull(result.getResolvedException()));
    }
}