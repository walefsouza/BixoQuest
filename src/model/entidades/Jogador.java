package model.entidades;

import model.mapa.TipoLocal;

public class Jogador {

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - -

    private String nome;
    private TipoLocal local;
    private int energia;
    private int levelConhecimento;
    private int motivacao;
    private int saude;
    private int desempenhoAcademico;
    private double dinheiro;
    private String aparencia;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Jogador(String nome, int energia, int levelConhecimento, int motivacao, int saude,
                   int desempenhoAcademico, double dinheiro, TipoLocal local, String aparencia) {

        this.nome = nome;
        this.energia = energia;
        this.levelConhecimento = levelConhecimento;
        this.motivacao = motivacao;
        this.saude = saude;
        this.desempenhoAcademico = desempenhoAcademico;
        this.dinheiro = dinheiro;
        this.local = local;
        this.aparencia = aparencia;
    }

    // Nome  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    // Aparência  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setAparencia(String aparecia) {
        this.aparencia = aparencia;
    }

    public String getAparencia(){
        return this.aparencia;
    }

    // Local  - - - - - - - - - - - - - - - - - - - - - - - -

    public void mudarLocal(TipoLocal l){
        this.local = l;
    }

    public TipoLocal getLocal(){
        return this.local;
    }

    // Energia  - - - - - - - - - - - - - - - - - - - - - - - -

    public int getEnergia() {
        return this.energia;
    }

    public void aumentarEnergia(int valor) {
        this.energia = Math.min(100, this.energia + valor);
    }

    public void decrementarEnergia(int valor) {
        this.energia = Math.max(0, this.energia - valor);
    }


    // Level Conhecimento  - - - - - - - - - - - - - - - - - - - - - - - -

    public int getLevelConhecimento() {
        return this.levelConhecimento;
    }

    public void aumentarLevelConhecimento(int valor) {
        this.levelConhecimento = Math.min(100, this.levelConhecimento + valor);
    }

    public void decrementarLevelConhecimento(int valor) {
        this.levelConhecimento = Math.max(0, this.levelConhecimento - valor);
    }

    // Desempenho  - - - - - - - - - - - - - - - - - - - - - - - -

    public int getDesempenhoAcademico() {
        return this.desempenhoAcademico;
    }

    public void aumentarDesempenhoAcademico(int valor) {
        this.desempenhoAcademico = Math.min(100, this.desempenhoAcademico + valor);
    }

    public void decrementarDesempenhoAcademico(int valor) {
        this.desempenhoAcademico = Math.max(0, this.desempenhoAcademico - valor);
    }

    // Saúde - - - - - - - - - - - - - - - - - - - - - - - -

    public int getSaude() {
        return this.saude;
    }

    public void aumentarSaude(int valor) {
        this.saude = Math.min(100, this.saude + valor);
    }

    public void decrementarSaude(int valor) {
        this.saude = Math.max(0, this.saude - valor);
    }

    // Dinheiro - - - - - - - - - - - - - - - - - - - - - - - -

    public double getDinheiro() {
        return this.dinheiro;
    }

    public void aumentarDinheiro(double valor) {
        this.dinheiro = this.dinheiro + valor;
    }

    public void decrementarDinheiro(double valor) {
        this.dinheiro = Math.max(0, this.dinheiro - valor);
    }

    // Motivação - - - - - - - - - - - - - - - - - - - - - - - -

    public int getMotivacao() {
        return this.motivacao;
    }

    public void aumentarMotivacao(int valor) {
        this.motivacao = Math.min(100, this.motivacao + valor);
    }

    public void decrementarMotivacao(int valor) {
        this.motivacao = Math.max(0, this.motivacao - valor);
    }
}


