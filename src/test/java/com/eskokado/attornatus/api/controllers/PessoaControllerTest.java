package com.eskokado.attornatus.api.controllers;

import com.eskokado.attornatus.api.domain.Pessoa;
import com.eskokado.attornatus.api.services.PessoaService;
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

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PessoaController.class)
public class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PessoaService pessoaService;

    @Test
    public void testCriarPessoa() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João");
        pessoa.setDataNascimento(LocalDate.of(1990, 5, 15));

        Mockito.when(pessoaService.criarPessoa(Mockito.any(Pessoa.class))).thenReturn(pessoa);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(pessoa)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is("João")));
    }

    @Test
    public void testConsultarPessoa() throws Exception {
        Long pessoaId = 1L;
        Pessoa pessoaExistente = new Pessoa();
        pessoaExistente.setId(pessoaId);
        pessoaExistente.setNome("Maria");

        Mockito.when(pessoaService.consultarPessoa(pessoaId)).thenReturn(pessoaExistente);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/pessoas/{id}", pessoaId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Maria")));
    }

    @Test
    public void testEditarPessoa() throws Exception {
        Long pessoaId = 1L;
        Pessoa pessoaExistente = new Pessoa();
        pessoaExistente.setId(pessoaId);
        pessoaExistente.setNome("Maria");

        Pessoa pessoaAtualizada = new Pessoa();
        pessoaAtualizada.setNome("Maria Silva");
        pessoaAtualizada.setDataNascimento(LocalDate.of(1985, 10, 21));

        Mockito.when(pessoaService.editarPessoa(pessoaId, pessoaAtualizada)).thenReturn(pessoaAtualizada);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/pessoas/{id}", pessoaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(pessoaAtualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Maria Silva")))
                .andExpect(jsonPath("$.dataNascimento", is("1985-10-21")));
    }
}