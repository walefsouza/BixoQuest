package Model.Mapa;
import Model.Entidades.Jogador;

public class PontoDeOnibus extends Local{

    public PontoDeOnibus(String nome, String descricao) {
        super(nome, descricao);
    }
    public void restart(Jogador j) {
        j.setEnergia(100);
        // acrescentar uma semana vencida no semestre;
    }

    public String descreverLocal(){
        return "Boatos dizem que nos pontos de ônibus acontecem os piores derramamentos de lágrimas";

        // Pensar num sistema para randomizar a descrição do local, como se fosse a tela inicial
        // minecraft. Mas estou em dúvida se isso será enquanto estiver no local ou na troca de local.
    }
}
