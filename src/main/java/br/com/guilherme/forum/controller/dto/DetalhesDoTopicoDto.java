package br.com.guilherme.forum.controller.dto;

import br.com.guilherme.forum.modelo.StatusTopico;
import br.com.guilherme.forum.modelo.Topico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DetalhesDoTopicoDto {

    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String nomeAutor;
    private StatusTopico status;
    private List<RespostaDto> repostas;

    public DetalhesDoTopicoDto(Topico topico){
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
        this.nomeAutor = topico.getAutor().getNome();
        this.status = topico.getStatus();
        this.repostas = new ArrayList<>();
        this.repostas.addAll(topico.getRespostas().stream().map(RespostaDto::new).collect(Collectors.toList()));
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public StatusTopico getStatus() {
        return status;
    }

    public List<RespostaDto> getRepostas() {
        return repostas;
    }
}
