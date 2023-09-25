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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testListarEnderecosDaPessoa() {
        Long pessoaId = 1L;
        Pessoa pessoaExistente = new Pessoa();
        pessoaExistente.setId(pessoaId);
        pessoaExistente.setNome("Maria");

        Endereco endereco1 = new Endereco();
        endereco1.setId(1L);
        endereco1.setLogradouro("Rua X");
        endereco1.setCep("54321-987");
        endereco1.setNumero("42");
        endereco1.setCidade("Rio de Janeiro");
        endereco1.setPessoa(pessoaExistente);

        Endereco endereco2 = new Endereco();
        endereco2.setId(2L);
        endereco2.setLogradouro("Avenida Y");
        endereco2.setCep("98765-432");
        endereco2.setNumero("18");
        endereco2.setCidade("São Paulo");
        endereco2.setPessoa(pessoaExistente);

        Mockito.when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoaExistente));
        Mockito.when(enderecoRepository.findByPessoa(pessoaExistente)).thenReturn(Arrays.asList(endereco1, endereco2));

        List<Endereco> enderecos = enderecoService.listarEnderecosDaPessoa(pessoaId);

        assertEquals(2, enderecos.size());
        assertEquals("Rua X", enderecos.get(0).getLogradouro());
        assertEquals("Avenida Y", enderecos.get(1).getLogradouro());
    }

    @Test
    public void testDefinirEnderecoPrincipal() {
        Long pessoaId = 1L;
        Long enderecoId = 1L;

        Pessoa pessoaExistente = new Pessoa();
        pessoaExistente.setId(pessoaId);
        pessoaExistente.setNome("José");

        Endereco enderecoExistente = new Endereco();
        enderecoExistente.setId(enderecoId);
        enderecoExistente.setLogradouro("Rua Principal");
        enderecoExistente.setCep("12345-678");
        enderecoExistente.setNumero("10");
        enderecoExistente.setCidade("São Paulo");
        enderecoExistente.setPrincipal(false);
        enderecoExistente.setPessoa(pessoaExistente);

        Mockito.when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.of(pessoaExistente));
        Mockito.when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.of(enderecoExistente));
        Mockito.when(enderecoRepository.save(Mockito.any(Endereco.class))).thenReturn(enderecoExistente);

        Endereco enderecoPrincipal = enderecoService.definirEnderecoPrincipal(pessoaId, enderecoId);

        assertNotNull(enderecoPrincipal);
        assertTrue(enderecoPrincipal.isPrincipal());
        assertEquals("Rua Principal", enderecoPrincipal.getLogradouro());
    }
}
