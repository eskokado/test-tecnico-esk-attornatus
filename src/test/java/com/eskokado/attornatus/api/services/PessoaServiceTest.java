package com.eskokado.attornatus.api.services;

import com.eskokado.attornatus.api.domain.Pessoa;
import com.eskokado.attornatus.api.repositories.PessoaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Test
    public void testCriarPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João");
        pessoa.setDataNascimento(LocalDate.of(1990, 5, 15));

        Mockito.when(pessoaRepository.save(Mockito.any(Pessoa.class))).thenReturn(pessoa);

        Pessoa pessoaCriada = pessoaService.criarPessoa(pessoa);

        assertEquals("João", pessoaCriada.getNome());
        assertEquals(LocalDate.of(1990, 5, 15), pessoaCriada.getDataNascimento());
    }

    @Test
    public void testEditarPessoa() {
        Long pessoaId = 1L;
        Pessoa pessoaExistente = new Pessoa();
        pessoaExistente.setId(pessoaId);
        pessoaExistente.setNome("Maria");
        pessoaExistente.setDataNascimento(LocalDate.of(1985, 10, 20));

        Pessoa pessoaAtualizada = new Pessoa();
        pessoaAtualizada.setNome("Maria Silva");
        pessoaAtualizada.setDataNascimento(LocalDate.of(1985, 10, 21));

        Mockito.when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoaExistente));
        Mockito.when(pessoaRepository.save(Mockito.any(Pessoa.class))).thenReturn(pessoaAtualizada);

        Pessoa pessoaEditada = pessoaService.editarPessoa(pessoaId, pessoaAtualizada);

        assertEquals("Maria Silva", pessoaEditada.getNome());
        assertEquals(LocalDate.of(1985, 10, 21), pessoaEditada.getDataNascimento());
    }

    @Test
    public void testConsultarPessoa() {
        Long pessoaId = 1L;
        Pessoa pessoaExistente = new Pessoa();
        pessoaExistente.setId(pessoaId);
        pessoaExistente.setNome("Carlos");
        pessoaExistente.setDataNascimento(LocalDate.of(1998, 3, 10));

        Mockito.when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoaExistente));

        Pessoa pessoaConsultada = pessoaService.consultarPessoa(pessoaId);

        assertNotNull(pessoaConsultada);
        assertEquals("Carlos", pessoaConsultada.getNome());
        assertEquals(LocalDate.of(1998, 3, 10), pessoaConsultada.getDataNascimento());
    }
}
