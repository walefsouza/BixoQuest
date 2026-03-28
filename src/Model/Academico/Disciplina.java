package Model.Academico;
import Model.Atividades.Atividade;
import Model.Entidades.Professor;
import Model.Mapa.Local;

import java.util.List;

public class Disciplina {

    private String nome;
    private Professor professor;
    private int frequencia;
    //private SalaDeAula local;
    private double mediaFinal;
    private List<Atividade> atividades;

    public Disciplina(String nome, Professor professor, int frequencia,
                      double mediaFinal, List<Atividade> atividades) {

        this.nome = nome;
        this.professor = professor;
        this.frequencia = frequencia;
        this.mediaFinal = mediaFinal;
        this.atividades = atividades;
    }

}
