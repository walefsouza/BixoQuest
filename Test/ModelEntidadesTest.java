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
    void setUp() {

        jogador = new Jogador("João", 50, 50, 50,
                50, 50, 100.0, null);

        professor = new Professor("JulioCesar", 40, 80, 50);
        colega = new Colega("Creuzo", 20, 80, 4, 3);
        animal = new Animal("Rex", 3, 70, "Cachorro", 4);
    }


    // Entidade — humor compartilhado por todas as entidades

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

    // Colega — métodos especialistas

    @Test
    public void calcularBonusDeEstudoNivelInteligencia() {
        assertEquals(8, colega.calcularBonusDeEstudo());
    }

    @Test
    public void calcularImpactoConversaHumorColega() {

        // humor alto mais motivação
        colega.setHumor(80);
        assertTrue(colega.calcularImpactoConversa() > 0);

        // humor baixo diminui motivação

        colega.setHumor(20);
        assertEquals(-10, colega.calcularImpactoConversa());
    }

    // Animal — métodos especialistas

    @Test
    public void aceitaCarinhoDoJogadorHumor() {

        // humor bom aceita carinho

        animal.setHumor(60);
        assertTrue(animal.aceitaCarinho());

        //humor ruim não aceita carinho

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