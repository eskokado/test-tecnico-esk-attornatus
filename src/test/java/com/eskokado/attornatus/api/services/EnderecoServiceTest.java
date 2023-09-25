package com.eskokado.attornatus.api.services;

import com.eskokado.attornatus.api.domain.Endereco;
import com.eskokado.attornatus.api.domain.Pessoa;
import com.eskokado.attornatus.api.repositories.EnderecoRepository;
import com.eskokado.attornatus.api.repositories.PessoaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class EnderecoServiceTest {
    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Test
    public void testCriarEnderecoParaPessoa() {
        Long pessoaId = 1L;
        Pessoa pessoaExistente = new Pessoa();
        pessoaExistente.setId(pessoaId);
        pessoaExistente.setNome("Luis");

        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua ABC");
        endereco.setCep("12345-678");
        endereco.setNumero("123");
        endereco.setCidade("São Paulo");

        Mockito.when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoaExistente));
        Mockito.when(enderecoRepository.save(Mockito.any(Endereco.class))).thenReturn(endereco);

        Endereco enderecoCriado = enderecoService.criarEnderecoParaPessoa(pessoaId, endereco);

        assertNotNull(enderecoCriado);
        assertEquals("Rua ABC", enderecoCriado.getLogradouro());
        assertEquals("12345-678", enderecoCriado.getCep());
        assertEquals("123", enderecoCriado.getNumero());
        assertEquals("São Paulo", enderecoCriado.getCidade());
        assertEquals(pessoaExistente, enderecoCriado.getPessoa());
    }
}
