import model.entidades.Animal;
import model.entidades.Jogador;
import model.entidades.Professor;
import model.interacao.CategoriaDialogo;
import model.interacao.Dialogo;
import model.mapa.Cantina;
import model.mapa.Local;
import model.mapa.TipoLocal;
import repository.IRepository;
import repository.Repository;
import service.InteracaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InteracaoServiceTest {

    private Jogador jogador;
    private Animal animalAmigavel;
    private Animal animalAgressivo;
    private Local cantina;
    private IRepository<Dialogo> dialogoRepo;
    private InteracaoService service;
    private Professor npc;

    @BeforeEach
    void setUp() {

        jogador = new Jogador("JoaoGrilo", 50, 50,
                50, 50, 50,
                100.0, null);

        animalAmigavel = new Animal("Rex", 3, 80, "Cachorro", 4);

        animalAgressivo = new Animal("Gato", 3, 20, "Gato", 2);
        npc = new Professor("Maeli", 45, 80, 50);
        cantina = new Cantina("Cantina", "Lugar para comer", 10);
        dialogoRepo = new Repository<>();
        service = new InteracaoService(dialogoRepo);
    }

    // conversar

    @Test
    public void conversarLocalSemDialogos() {
        Dialogo resultado = service.conversar(cantina);
        assertNull(resultado);
    }

    @Test
    public void conversarLocalComDialogo() {
        dialogoRepo.salvar(new Dialogo("HelloWorld", npc, "Olá Estudante!", CategoriaDialogo.SAUDACAO, TipoLocal.CANTINA));
        Dialogo resultado = service.conversar(cantina);
        assertNotNull(resultado);
    }

    @Test
    public void conversarQualquerLugar() {
        dialogoRepo.salvar(new Dialogo("Comida", npc, "Tem comida na Cantina!", CategoriaDialogo.DICA, TipoLocal.QUALQUER_LUGAR));
        Dialogo resultado = service.conversar(cantina);
        assertNotNull(resultado);
    }

    // conversarPorCategoria

    @Test
    public void conversarPorCategoria() {
        dialogoRepo.salvar(new Dialogo("d1", npc, "Dica!", CategoriaDialogo.DICA, TipoLocal.CANTINA));
        dialogoRepo.salvar(new Dialogo("d2", npc, "Bronca!", CategoriaDialogo.BRONCA, TipoLocal.CANTINA));

        Dialogo resultado = service.conversarPorCategoria(cantina, CategoriaDialogo.DICA);

        assertNotNull(resultado);
        assertEquals(CategoriaDialogo.DICA, resultado.getCategoria());
    }

    // interagirComAnimal

    @Test
    public void interagirComAnimalAumentaMotivacao() {
        int motivacaoAntes = jogador.getMotivacao();

        boolean resultado = service.interagirComAnimal(jogador, animalAmigavel);

        assertTrue(resultado);
        assertTrue(jogador.getMotivacao() > motivacaoAntes);
    }

    @Test
    public void interagirComAnimalAgressivoDano() {
        int saudeAntes = jogador.getSaude();
        int energiaAntes = jogador.getEnergia();
        int motivacaoAntes = jogador.getMotivacao();

        boolean resultado = service.interagirComAnimal(jogador, animalAgressivo);

        assertFalse(resultado);
        assertTrue(jogador.getSaude() < saudeAntes);
        assertTrue(jogador.getEnergia() < energiaAntes);
        assertTrue(jogador.getMotivacao() < motivacaoAntes);
    }
}