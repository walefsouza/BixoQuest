import model.entidades.Animal;
import model.entidades.Colega;
import model.entidades.Jogador;
import model.entidades.Professor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModelEntidadesTest {

    private Jogador jogador;
    private Professor professor;
    private Colega colega;
    private Animal animal;

    @BeforeEach
    public void setUp() {
        // Adicionando o parâmetro 'aparencia' exigido pelo novo construtor
        jogador = new Jogador("João", 50, 50, 50,
                50, 50, 100.0, null, "img_jogador.png");

        // Adicionando 'aparencia' nas entidades que herdam da classe abstrata Entidade
        professor = new Professor("JulioCesar", 40, 80, "img_prof.png", 50);
        colega = new Colega("Creuzo", 20, 80, "img_colega.png", 4, 3);
        animal = new Animal("Rex", 3, 70, "img_animal.png", "Cachorro", 4);
    }

    // Entidade - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void aumentarHumorEntidadeMaiorMAx() {
        professor.aumentarHumor(200);
        assertEquals(100, professor.getHumor());
    }

    @Test
    public void decrementarHumorEntidadeMenorMinimo() {
        professor.decrementarHumor(200);
        assertEquals(0, professor.getHumor());
    }

    // Colega - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void calcularBonusDeEstudoNivelInteligencia() {
        assertEquals(8, colega.calcularBonusDeEstudo());
    }

    @Test
    public void calcularImpactoConversaHumorColega() {
        // Humor alto aumenta motivação (Carisma 3 * 3 = 9 > 0)
        colega.setHumor(80);
        assertTrue(colega.calcularImpactoConversa() > 0);

        // Humor baixo (menor que 40) diminui motivação em -10
        colega.setHumor(20);
        assertEquals(-10, colega.calcularImpactoConversa());
    }

    // Animal - - - - - - - - - - - - - - - - - - - - - - -

    @Test
    public void aceitaCarinhoDoJogadorHumor() {
        // Humor bom aceita carinho
        animal.setHumor(60);
        assertTrue(animal.aceitaCarinho());

        // Humor ruim não aceita carinho
        animal.setHumor(30);
        assertFalse(animal.aceitaCarinho());
    }

    @Test
    public void calcularGanhoMotivacao() {
        assertEquals(8, animal.calcularGanhoMotivacao());
    }

    @Test
    public void calcularDanoAtaque() {
        assertEquals(30, animal.calcularDanoAtaque());
    }
}