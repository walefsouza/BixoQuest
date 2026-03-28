package Model.Entidades;

public class Atributo {

    private int atributo;

    public Atributo(int valor) {
        this.atributo = valor;
    }

    public void setAtributo(int valor) {
                this.atributo = valor;
    }

    public int getAtributo() {
        return this.atributo;
    }

    public void aumentarAtributo(int valor) {
        this.atributo = Math.min(100, this.atributo + valor);
    }

    public void decrementarAtributo(int valor) {
        this.atributo = Math.max(0, this.atributo - valor);
    }

}
