package Model.Mapa;

import Model.Entidades.Jogador;
import Model.Game.Game;

public class Colegiado extends Local {

    // construtor

    public Colegiado (String nome, String descricao) {
        super(nome, descricao);
    }

    // métodos

    public void conversarMaeli(Jogador j){
        j.aumentarMotivacao(5);
    }
    public void pedirAjuda(Jogador j){
        j.aumentarLevelConhecimento(5);
    }

    public int verificarProgresso(Game g){
        return g.calcularProgresso();
    }

    //public void trancarCurso(Game g){} ideia para o futuro

    // evento formatura,
    // se perder o semestre, evento corrida formatura
    /*Evento Formatura — disparado quando calcularProgresso() retorna 100%. É o evento de vitória do jogo.
    Evento Corrida Formatura — disparado quando o jogador reprova e precisa compensar.*/



    public String descreverLocal(){
        return "Você sabia que o colegiado é um espaço de acolhimento para os alunos?";
    }
}
