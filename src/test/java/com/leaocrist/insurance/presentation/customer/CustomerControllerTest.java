package com.leaocrist.insurance.presentation.customer;

import com.leaocrist.insurance.application.customer.CustomerNotFoundException;
import com.leaocrist.insurance.application.customer.CustomerService;
import com.leaocrist.insurance.application.customer.dto.CustomerRequest;
import com.leaocrist.insurance.application.customer.dto.CustomerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Test
    void shouldUpdateCustomer() throws Exception {

        CustomerResponse response = new CustomerResponse(
                1L,
                "Leandro Updated",
                "1234567890",
                "correto@test.com"
        );
        when(customerService.updateCustomer(
                eq(1L),
                any(CustomerRequest.class)
        )).thenReturn(response);

        mockMvc.perform(
                put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Leandro Updated",
                                    "phone": "1234567890",
                                    "email": "correto@test.com"
                                }
                                """)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Leandro Updated"))
                .andExpect(jsonPath("$.phone").value(1234567890))
                .andExpect(jsonPath("$.email").value("correto@test.com"));
    }

    @Test
    void shouldReturnNotFoundWhenCustomerDoesNotExist() throws Exception {

        when(customerService.updateCustomer(
                eq(99L),
                any(CustomerRequest.class)
        )).thenThrow(new CustomerNotFoundException(99L));

        mockMvc.perform(
                put("/customers/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                         "name": "Leandro",
                                         "phone": "1234567890",
                                         "email": "leandro@correo.com"
                                    }
                                """)
        ).andExpect(status().isNotFound());
    }
}
