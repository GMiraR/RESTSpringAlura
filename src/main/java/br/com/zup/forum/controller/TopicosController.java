package br.com.zup.forum.controller;

import br.com.zup.forum.controller.dto.DetalhesDoTopicoDTO;
import br.com.zup.forum.controller.dto.TopicoDTO;
import br.com.zup.forum.controller.form.AtualizacaoTopicoForm;
import br.com.zup.forum.controller.form.TopicoForm;
import br.com.zup.forum.modelo.Curso;
import br.com.zup.forum.modelo.Topico;
import br.com.zup.forum.repository.CursoRepository;
import br.com.zup.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    CursoRepository cursoRepository;

    @GetMapping
    @Transactional
    public List<TopicoDTO> lista(String nomeCurso){
        if(nomeCurso == null) {
            List<Topico> topicos = topicoRepository.findAll();
            return TopicoDTO.converter(topicos);
        } else {
            List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
            return TopicoDTO.converter(topicos);
        }
    }

    @PostMapping
    public ResponseEntity<TopicoDTO> cadastrar(@RequestBody TopicoForm topicoForm,
                                               UriComponentsBuilder uriBuilder){
        Topico topico = topicoForm.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDTO(topico));
    }

    @GetMapping("/{id}")
    @Transactional
    public DetalhesDoTopicoDTO detalhar(@PathVariable Long id){

        Topico topico = topicoRepository.getById(id);
        return new DetalhesDoTopicoDTO(topico);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id,
                                               @RequestBody AtualizacaoTopicoForm topicoForm){

        Topico topico = topicoForm.atualizar(id, topicoRepository);

        return ResponseEntity.ok(new TopicoDTO(topico));
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id){

        topicoRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
