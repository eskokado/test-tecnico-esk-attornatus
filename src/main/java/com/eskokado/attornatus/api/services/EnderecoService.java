package com.eskokado.attornatus.api.services;

import com.eskokado.attornatus.api.domain.Endereco;
import com.eskokado.attornatus.api.domain.Pessoa;
import com.eskokado.attornatus.api.repositories.EnderecoRepository;
import com.eskokado.attornatus.api.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    public Endereco criarEnderecoParaPessoa(Long pessoaId, Endereco endereco) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(pessoaId);
        if (pessoa.isPresent()) {
            endereco.setPessoa(pessoa.get());
            return enderecoRepository.save(endereco);
        } else {
            return null;
        }
    }

    public List<Endereco> listarEnderecosDaPessoa(Long pessoaId) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(pessoaId);
        return pessoa.map(value -> enderecoRepository.findByPessoa(value)).orElse(Collections.emptyList());
    }

}
