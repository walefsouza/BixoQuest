import model.atividades.ResultadoAcao;
import model.entidades.Animal;
import model.entidades.Jogador;
import model.entidades.TipoEntidade;
import model.interacao.CategoriaDialogo;
import model.interacao.Dialogo;
import model.mapa.Cantina;
import model.mapa.Local;
import model.mapa.TipoLocal;
import repository.IRepository;
import service.InteracaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InteracaoServiceTest {

    private Jogador jogador;
    private Animal animalAmigavel;
    private Animal animalAgressivo;
    private Local cantina;

    // Repositório Fake
    private IRepository<Dialogo> dialogoRepoFake;
    private InteracaoService service;

    @BeforeEach
    public void setUp() {

        jogador = new Jogador("JoaoGrilo", 50, 50,
                50, 50, 50,
                100.0, null, "img");

        animalAmigavel = new Animal("Rex", 3, 80, "img", "Cachorro", 4);

        animalAgressivo = new Animal("Gato", 3, 20, "img", "Gato", 2);

        cantina = new Cantina("Cantina", "Lugar para comer", "img", "audio", 10);

        dialogoRepoFake = mock(IRepository.class);
        service = new InteracaoService(dialogoRepoFake);
    }

    // Conversar Geral - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void conversarLocalSemDialogos() {

        when(dialogoRepoFake.listar()).thenReturn(new ArrayList<>());

        ResultadoAcao resultado = service.conversar(cantina);

        // conversar retorna um DTO com texto padrão de erro, por isso não é null
        assertNotNull(resultado);
        assertEquals("Não há ninguém interessante para conversar aqui no momento.", resultado.getTextoNarrativo());
    }

    @Test
    public void conversarLocalComDialogo() {
        Dialogo fala = new Dialogo("HelloWorld", TipoEntidade.PROFESSOR, "Olá Estudante!", CategoriaDialogo.SAUDACAO, TipoLocal.CANTINA);
        when(dialogoRepoFake.listar()).thenReturn(Arrays.asList(fala));

        ResultadoAcao resultado = service.conversar(cantina);

        assertNotNull(resultado);
        // Verifica se a frase do diálogo está no texto narrativo do DTO
        assertTrue(resultado.getTextoNarrativo().contains("Olá Estudante!"));
    }

    @Test
    public void conversarQualquerLugar() {
        // Um diálogo definido como QUALQUER_LUGAR deve aparecer independente do local
        Dialogo fala = new Dialogo("Comida", TipoEntidade.PROFESSOR, "Tem comida na Cantina!", CategoriaDialogo.DICA, TipoLocal.QUALQUER_LUGAR);
        when(dialogoRepoFake.listar()).thenReturn(Arrays.asList(fala));

        ResultadoAcao resultado = service.conversar(cantina);

        assertNotNull(resultado);
        assertTrue(resultado.getTextoNarrativo().contains("Tem comida na Cantina!"));
    }

    // Conversar por Categoria - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void conversarPorCategoriaEspecifica() {
        Dialogo d1 = new Dialogo("d1", TipoEntidade.PROFESSOR, "Dica!", CategoriaDialogo.DICA, TipoLocal.CANTINA);
        Dialogo d2 = new Dialogo("d2", TipoEntidade.PROFESSOR, "Bronca!", CategoriaDialogo.BRONCA, TipoLocal.CANTINA);

        when(dialogoRepoFake.listar()).thenReturn(Arrays.asList(d1, d2));

        ResultadoAcao resultado = service.conversarPorCategoria(cantina, CategoriaDialogo.DICA);

        assertNotNull(resultado);

        assertTrue(resultado.getTextoNarrativo().contains("Dica!")); // É uma dica
        assertFalse(resultado.getTextoNarrativo().contains("Bronca!")); // Não é uma dica
    }

    // Interagir com Animais - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void interagirComAnimalAumentaMotivacao() {
        int motivacaoAntes = jogador.getMotivacao();

        ResultadoAcao resultado = service.interagirComAnimal(jogador, animalAmigavel);

        assertNotNull(resultado);
        assertTrue(jogador.getMotivacao() > motivacaoAntes);
    }

    @Test
    public void interagirComAnimalAgressivoDano() {

        int saudeAntes = jogador.getSaude();
        int energiaAntes = jogador.getEnergia();
        int motivacaoAntes = jogador.getMotivacao();

        ResultadoAcao resultado = service.interagirComAnimal(jogador, animalAgressivo);

        assertNotNull(resultado);

        // Verifica se os danos foram aplicados para cada atributo do jogador
        assertTrue(jogador.getSaude() < saudeAntes);
        assertTrue(jogador.getEnergia() < energiaAntes);
        assertTrue(jogador.getMotivacao() < motivacaoAntes);
    }
}