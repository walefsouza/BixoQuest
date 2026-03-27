package Model.Entidades;

public class Jogador {

    private String nome;
    //private Local local;
    private int energia;
    private int levelConhecimento;
    private int motivacao;
    private int saude;
    private int desempenhoAcademico;
    private double dinheiro;

    // construtor

    public Jogador(String nome, int energia, int levelConhecimento, int motivacao, int saude, int desempenhoAcademico, double dinheiro) {
        this.nome = nome;
        this.energia = energia;
        this.levelConhecimento = levelConhecimento;
        this.motivacao = motivacao;
        this.saude = saude;
        this.desempenhoAcademico = desempenhoAcademico;
        this.dinheiro = dinheiro;
    }

    // setters

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public void setLevelconhecimento(int levelConhecimento) {
        this.levelConhecimento = levelConhecimento;
    }

    public void setMotivacao(int motivacao){
        this.motivacao = motivacao;
    }

    public void setDesempenhoAcademico(int desempenhoAcademico) {
        this.desempenhoAcademico = desempenhoAcademico;
    }

    public void setSaude(int saude) {
        this.saude = saude;
    }

    public void setDinheiro(double dinheiro) {
        this.dinheiro = dinheiro;
    }

    // getters

    public String getNome() {
        return this.nome;
    }

    public int getEnergia() {
        return this.energia;
    }

    public int getLevelConhecimento(){
        return this.levelConhecimento;
    }

    public int getMotivacao(){
        return this.motivacao;
    }

    public int getDesempenhoAcademico() {
        return this.desempenhoAcademico;
    }

    public int getSaude() {
        return this.saude;
    }

    public double getDinheiro() {
        return this.dinheiro;
    }

}
