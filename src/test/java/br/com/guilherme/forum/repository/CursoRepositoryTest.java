package br.com.guilherme.forum.repository;

import br.com.guilherme.forum.modelo.Curso;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class CursoRepositoryTest {

    @Autowired
    private CursoRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    public void deveriaCarregarUmCursoAoBuscarPeloNome(){
        String nomeCurso = "HTML 5";
        Curso curso = repository.findByNome(nomeCurso);
        assertNotNull(curso);
        assertEquals(nomeCurso, curso.getNome());
    }

    @Test
    public void deveriaDevolverNullo(){
        String nomeCurso = "JAVA";
        Curso curso = repository.findByNome(nomeCurso);
        assertNull(curso);
    }
}