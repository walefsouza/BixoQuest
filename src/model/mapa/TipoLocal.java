package model.mapa;

public enum TipoLocal {

    // Tipos Locais - - - - - - - - - - - - - - - - - - - - - - -
    CANTINA("Cantina"),
    LABORATORIO("Laboratório"),
    SALA_DE_AULA("Sala de Aula"),
    PONTO_DE_ONIBUS("Ponto de Ônibus"),
    BOROGODO("Praça do Borogodó"),
    COLEGIADO("Colegiado"),
    MAPA("UEFS MAPA"),
    QUALQUER_LUGAR("qualquer lugar");

    // Atributo - - - - - - - - - - - - - - - - - - - - - - - - -

    private String local;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    TipoLocal(String local){
        this.local = local;
    }

    public String getLocalNome(){
        return this.local;
    }



}