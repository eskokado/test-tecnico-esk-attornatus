package com.eskokado.attornatus.api.services;

import com.eskokado.attornatus.api.domain.Pessoa;
import com.eskokado.attornatus.api.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;
    public Pessoa criarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }
}
