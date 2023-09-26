package com.eskokado.attornatus.api.controllers;

import com.eskokado.attornatus.api.domain.Endereco;
import com.eskokado.attornatus.api.exceptions.NotFoundException;
import com.eskokado.attornatus.api.services.EnderecoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EnderecoController.class)
public class EnderecoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnderecoService enderecoService;

    @InjectMocks
    private EnderecoController enderecoController;

    @Test
    public void testCriarEnderecoParaPessoa() throws Exception {
        Long pessoaId = 1L;
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua X");
        endereco.setCep("12345-678");
        endereco.setNumero("42");
        endereco.setCidade("São Paulo");

        when(enderecoService.criarEnderecoParaPessoa(pessoaId, endereco)).thenReturn(endereco);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/enderecos/{pessoaId}", pessoaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(endereco)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.logradouro", is("Rua X")));
    }

    @Test
    public void testListarEnderecosDaPessoaQuandoNaoExistemEnderecos() throws Exception {
        Long pessoaId = 1L;

        // Simula o lançamento da exceção NotFoundException pelo serviço
        when(enderecoService.listarEnderecosDaPessoa(pessoaId)).thenThrow(new NotFoundException("Pessoa não encontrada com o ID: " + pessoaId));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/enderecos/pessoa/{pessoaId}", pessoaId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Pessoa não encontrada com o ID: " + pessoaId)));
    }

    @Test
    public void testListarEnderecosDaPessoa() throws Exception {
        Long pessoaId = 1L;
        Endereco endereco1 = new Endereco();
        endereco1.setId(1L);
        endereco1.setLogradouro("Rua A");
        endereco1.setCep("54321-987");
        endereco1.setNumero("10");
        endereco1.setCidade("Rio de Janeiro");

        Endereco endereco2 = new Endereco();
        endereco2.setId(2L);
        endereco2.setLogradouro("Avenida B");
        endereco2.setCep("98765-432");
        endereco2.setNumero("18");
        endereco2.setCidade("São Paulo");

        List<Endereco> enderecos = Arrays.asList(endereco1, endereco2);

        // Simula o comportamento do serviço
        when(enderecoService.listarEnderecosDaPessoa(pessoaId)).thenReturn(enderecos);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/enderecos/pessoa/{pessoaId}", pessoaId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].logradouro", is("Rua A")))
                .andExpect(jsonPath("$[1].logradouro", is("Avenida B")));
    }
}
