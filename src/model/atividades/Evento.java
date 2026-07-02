package model.atividades;

import model.Game;
import model.academico.Semestre;
import model.entidades.Jogador;
import model.mapa.Local;
import model.mapa.TipoLocal;

import java.util.List;

public class Evento extends Atividade {

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - -

    private CategoriaEvento categoria;
    private RequisitoEvento requisito;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Evento(String nome, String descricao, TipoLocal localAtividade,
                  CategoriaEvento categoria, RequisitoEvento requisito,
                  String icone, List<Efeito> efeitos) {

        super(nome, descricao, localAtividade, icone, efeitos);
        this.categoria = categoria;
        this.requisito = requisito;
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    // Para evitar a criação de inumeras classes por conta desse método passamos
    // a usar enum's de condição e verificamos elas aqui neste switch case.

    public boolean verificarCondicao(Game g) {

        Jogador j = g.getJogador();
        Semestre s = g.getSemestre();

        switch (this.requisito) {

            case SEMANA4:
                return s.getSemanaAtual() == 4;

            case SEMANA1_SEMESTRE1:
                return s.getNumero() == 1 && s.getSemanaAtual() == 1;

            case SEMANA4_SEMESTRE2:
                return s.getNumero() == 2 && s.getSemanaAtual() == 4;

            case SEMANA3_SEMESTRE3:
                return s.getNumero() == 3 && s.getSemanaAtual() == 3;

            case SEMANA2_SEMESTRE4:
                return s.getNumero() == 4 && s.getSemanaAtual() == 2;

            case SEMANA3_SEMESTRE5:
                return s.getNumero() == 5 && s.getSemanaAtual() == 3;

            case SEMANA1_SEMESTRE6:
                return s.getNumero() == 6 && s.getSemanaAtual() == 1;

            case SEMANA4_SEMESTRE6:
                return s.getNumero() == 6 && s.getSemanaAtual() == 4;

            case NENHUM:
                return true;

            default:
                return true;
        }
    }

    // Getters - - - - - - - - - - - - - - - - - - - - - - - - - -

    public CategoriaEvento getCategoria() {
        return categoria;
    }

    public RequisitoEvento getRequisito() {
        return requisito;
    }

    // Setters - - - - - - - - - - - - - - - - - - - - - - - - - -

    public void setCategoria(CategoriaEvento categoria) {
        this.categoria = categoria;
    }

    public void setRequisito(RequisitoEvento requisito) {
        this.requisito = requisito;
    }
}