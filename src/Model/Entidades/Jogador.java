package Model.Entidades;

public class Jogador {

    private String nome;
    //private Local local;
    private Atributo energia;
    private Atributo levelConhecimento;
    private Atributo motivacao;
    private Atributo saude;
    private Atributo desempenhoAcademico;
    private Atributo dinheiro;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Jogador(String nome, int energia, int levelConhecimento, int motivacao, int saude, int desempenhoAcademico, int dinheiro) {
        this.nome = nome;
        this.energia = new Atributo(energia);
        this.levelConhecimento = new Atributo(levelConhecimento);
        this.motivacao = new Atributo(motivacao);
        this.saude = new Atributo(saude);
        this.desempenhoAcademico = new Atributo(desempenhoAcademico);
        this.dinheiro = new Atributo(dinheiro);
        /*this.energia.setAtributo(energia);
        this.levelConhecimento.setAtributo(levelConhecimento);
        this.motivacao.setAtributo(motivacao);
        this.saude.setAtributo(saude);
        this.desempenhoAcademico.setAtributo(desempenhoAcademico);
        this.dinheiro.setAtributo(dinheiro);*/
    }

    // Nome  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

}
/*


    private String nome;
    //private Local local;
    private int energia;
    private int levelConhecimento;
    private int motivacao;
    private int saude;
    private int desempenhoAcademico;
    private double dinheiro;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public Jogador(String nome, int energia, int levelConhecimento, int motivacao, int saude, int desempenhoAcademico, double dinheiro) {
        this.nome = nome;
        this.energia = energia;
        this.levelConhecimento = levelConhecimento;
        this.motivacao = motivacao;
        this.saude = saude;
        this.desempenhoAcademico = desempenhoAcademico;
        this.dinheiro = dinheiro;
    }
    // Energia  - - - - - - - - - - - - - - - - - - - - - - - -

    public void setEnergia(int energia) {
        this.energia = energia;
    }

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

    public void setLevelconhecimento(int levelConhecimento) {
        this.levelConhecimento = levelConhecimento;
    }

    public int getLevelConhecimento(){
        return this.levelConhecimento;
    }

    public void aumentarLevelConhecimento(int valor) {
        this.levelConhecimento = Math.min(100, this.levelConhecimento + valor);
    }

    public void decrementarLevelConhecimento(int valor) {
        this.levelConhecimento = Math.max(0, this.levelConhecimento - valor);
    }


    // Desempenho  - - - - - - - - - - - - - - - - - - - - - - - -


    public void setDesempenhoAcademico(int desempenhoAcademico) {
        this.desempenhoAcademico = desempenhoAcademico;
    }

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

    public void setSaude(int saude) {
        this.saude = saude;
    }

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


    public void setDinheiro(double dinheiro) {
        this.dinheiro = dinheiro;
    }

    public double getDinheiro() {
        return this.dinheiro;
    }

    public void aumentarDinheiro(double valor) {
        this.dinheiro = Math.min(100, this.dinheiro + valor);
    }

    public void decrementarDinheiro(double valor) {
        this.dinheiro = Math.max(0, this.dinheiro - valor);
    }

    // Motivação - - - - - - - - - - - - - - - - - - - - - - - -


    public void setMotivacao(int motivacao) {
        this.motivacao = motivacao;
        }

    public int getMotivacao(){
        return this.motivacao;
    }

    public void aumentarMotivacao(int valor) {
        this.motivacao = Math.min(100, this.motivacao + valor);
    }

    public void decrementarMotivacao(int valor) {
        this.motivacao = Math.max(0, this.motivacao - valor);
    }
 */


