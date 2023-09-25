package com.eskokado.attornatus.api.services;

import com.eskokado.attornatus.api.domain.Pessoa;
import com.eskokado.attornatus.api.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;
    public Pessoa criarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Pessoa editarPessoa(Long id, Pessoa pessoa) {
        Optional<Pessoa> pessoaExistente = pessoaRepository.findById(id);
        if (pessoaExistente.isPresent()) {
            pessoa.setId(id);
            return pessoaRepository.save(pessoa);
        } else {
            return null;
        }
    }
}
