package com.eskokado.attornatus.api.repositories;

import com.eskokado.attornatus.api.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    @Query("SELECT e FROM Endereco e WHERE e.pessoa.id = :pessoaId AND e.principal = true")
    Endereco findEnderecoPrincipal(@Param("pessoaId") Long pessoaId);
}