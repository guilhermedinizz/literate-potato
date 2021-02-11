package br.com.guilherme.forum.repository;

import br.com.guilherme.forum.modelo.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Page<Topico> findByCurso_Nome(String nomeCurso, Pageable paginacao);
}
