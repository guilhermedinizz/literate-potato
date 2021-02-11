package br.com.guilherme.forum.controller;

import br.com.guilherme.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.guilherme.forum.controller.dto.TopicoDto;
import br.com.guilherme.forum.controller.form.AtualizacaoTopicoForm;
import br.com.guilherme.forum.controller.form.TopicoForm;
import br.com.guilherme.forum.modelo.Topico;
import br.com.guilherme.forum.repository.CursoRepository;
import br.com.guilherme.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    @Cacheable(value = "ListaDeTopicos")
    public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso,
                                 @PageableDefault(page = 0, size = 10, sort = "titulo", direction = Sort.Direction.ASC) Pageable paginacao) {

        if (nomeCurso == null) {
            Page<Topico> topicos = topicoRepository.findAll(paginacao);
            return TopicoDto.converter(topicos);
        } else {
            Page<Topico> topicos = topicoRepository.findByCurso_Nome(nomeCurso, paginacao);
            return TopicoDto.converter(topicos);
        }
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "ListaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {
        Topico topico = topicoForm.converte(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "ListaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @Valid @RequestBody AtualizacaoTopicoForm form) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            Topico topico = form.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "ListaDeTopicos", allEntries = true)
    public ResponseEntity remover(@PathVariable Long id) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
