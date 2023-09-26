package com.eskokado.attornatus.api.services;

import com.eskokado.attornatus.api.domain.Endereco;
import com.eskokado.attornatus.api.domain.Pessoa;
import com.eskokado.attornatus.api.exceptions.NotFoundException;
import com.eskokado.attornatus.api.repositories.EnderecoRepository;
import com.eskokado.attornatus.api.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (pessoa.isPresent()) {
            return enderecoRepository.findByPessoa(pessoa.get());
        } else {
            throw new NotFoundException("Pessoa não encontrada com o ID: " + pessoaId);
        }
    }

    public Endereco definirEnderecoPrincipal(Long pessoaId, Long enderecoId) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(pessoaId);
        Optional<Endereco> endereco = enderecoRepository.findById(enderecoId);

        if (pessoa.isPresent() && endereco.isPresent()) {
            Endereco enderecoAtualizado = endereco.get();
            enderecoAtualizado.setPrincipal(true);

            List<Endereco> enderecosDaPessoa = pessoa.get().getEnderecos();
            for (Endereco e : enderecosDaPessoa) {
                if (!e.equals(enderecoAtualizado)) {
                    e.setPrincipal(false);
                }
            }

            return enderecoRepository.save(enderecoAtualizado);
        } else {
            return null;
        }
    }

}
