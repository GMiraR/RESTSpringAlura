package br.com.zup.forum.repository;


import br.com.zup.forum.modelo.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {


    List<Topico> findByCursoNome(String nomeCurso);
}
