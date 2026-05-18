package model.atividades;

import model.entidades.Jogador;
import model.mapa.Local;
import model.Game; // Assumindo que você mudou para receber Game
import model.mapa.TipoLocal;

import java.util.List;

public class EventoAvaliacao extends Evento {

    private List<Pergunta> perguntas;
    private int notaMaxima;
    private int notaObtida;
    private boolean realizada;

    // Construtor - - - - - - - - - - - - - - - - - - - - - - - -
    public EventoAvaliacao(String nome, String descricao, TipoLocal localAtividade,
                           CategoriaEvento categoria, RequisitoEvento requisito,
                           String icon, List<Efeito> efeitos,
                           List<Pergunta> perguntas, int notaMaxima) {

        super(nome, descricao, localAtividade, categoria, requisito, icon, efeitos);
        this.perguntas = perguntas;
        this.notaMaxima = notaMaxima;
        this.notaObtida = 0;
        this.realizada = false;
    }

    // Setters  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setPerguntas(List<Pergunta> perguntas) {
        this.perguntas = perguntas;
    }

    public void setNotaMaxima(int notaMaxima) {
        this.notaMaxima = notaMaxima;
    }

    public void setNotaObtida(int notaObtida) {
        this.notaObtida = notaObtida;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }

    // Getters  - - - - - - - - - - - - - - - - - - - - - - - -

    public List<Pergunta> getPerguntas() {
        return this.perguntas;
    }

    public int getNotaMaxima() {
        return this.notaMaxima;
    }

    public int getNotaObtida() {
        return this.notaObtida;
    }

    public boolean getRealizada() {
        return this.realizada;
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    // Correção das respostas das avaliações
    public int corrigirResposta(int indicePergunta, int resposta) {
        if (indicePergunta < 0 || indicePergunta >= perguntas.size()) {
            return getNotaObtida();
        }

        Pergunta p = perguntas.get(indicePergunta);

        if (p.verificarResposta(resposta)) {
            setNotaObtida(getNotaObtida() + 1);
        }

        return getNotaObtida();
    }

    // Sobrescrevendo executar para setar avaliação como realizada
    @Override
    public ResultadoAcao executar(Game g) {
        this.setRealizada(true);
        return super.executar(g);
    }
}