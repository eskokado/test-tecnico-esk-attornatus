package com.eskokado.attornatus.api.controllers;

import com.eskokado.attornatus.api.domain.Endereco;
import com.eskokado.attornatus.api.services.EnderecoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EnderecoController.class)
public class EnderecoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnderecoService enderecoService;

    @Test
    public void testCriarEnderecoParaPessoa() throws Exception {
        Long pessoaId = 1L;
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua X");
        endereco.setCep("12345-678");
        endereco.setNumero("42");
        endereco.setCidade("São Paulo");

        Mockito.when(enderecoService.criarEnderecoParaPessoa(pessoaId, endereco)).thenReturn(endereco);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/enderecos/{pessoaId}", pessoaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(endereco)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.logradouro", is("Rua X")));
    }

}
