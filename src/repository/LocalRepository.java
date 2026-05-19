package repository;

import model.mapa.*;

import java.util.ArrayList;
import java.util.List;

public class LocalRepository implements IRepository<Local> {

    private List<Local> mapaDaUefs;

    public LocalRepository() {
        this.mapaDaUefs = gerarLocais();
    }

    // Pesquisa os locais na lista ignorando os cases
    @Override
    public Local buscar(String nome) {
        for (Local local : mapaDaUefs) {
            if (local.getNome().equalsIgnoreCase(nome)) {
                return local;
            }
        }
        return null;
    }

    // Retorna nossa lista de locais direto da memória
    @Override
    public List<Local> listar() {
        return this.mapaDaUefs;
    }

    // Nosso mapa é fixo, não poderá ser alterado em execução
    @Override
    public boolean salvar(Local local) {
        return false;
    }

    // Os locais não podem ser deletados do jogo
    @Override
    public boolean remover(String nome) {
        return false;
    }

    public static List<Local> gerarLocais() {

        List<Local> lista = new ArrayList<>();

        // Criando locais  - - - - - - - - - - - - - - - - - - - - - - - -

        Cantina cantina = new Cantina(
                "Cantina",
                "Lanches & Conversa",
                "src/resources/locais/cantina-modulo-3.png",
                "src/resources/locais/audio/musica-tema-cantina.mp3",
                0);

        lista.add(cantina);

        Borogodo borogodo = new Borogodo(
                "Borogodó",
                "Praça de convivência e descanso da galera.",
                "src/resources/locais/audio/musica-tema-borogodo.mp3",
                "src/resources/locais/praca-do-borogodo.jpeg",
                0,
                false
        );
        lista.add(borogodo);



        Colegiado colegiado = new Colegiado(
                "Colegiado",
                "Onde a burocracia do curso acontece.",
                "src/resources/locais/colegiado-uefs.jpeg",
                "src/resources/locais/audio/musica-tema-colegiado.mp3",
                true // tem atendimento
        );
        lista.add(colegiado);

        Laboratorio laboratorio = new Laboratorio(
                "Laboratório",
                "Ar condicionado gelado e muitos PCs.",
                "src/resources/locais/laboratorio-leds.jpeg",
                "src/resources/locais/audio/musica-tema-LEDS.mp3",
                10,  // computadores
                1.5  // Multiplicador de estudo
        );

        lista.add(laboratorio);

        PontoDeOnibus pontoDeOnibus = new PontoDeOnibus(
                "Ponto de Ônibus",
                "A sua rota de fuga e checkpoint semanal.",
                "src/resources/locais/ponto-de-onibus.png",
                "src/resources/locais/audio/ponto-de-onibus.mp3"
        );
        lista.add(pontoDeOnibus);

        SalaDeAula salaDeAula = new SalaDeAula(
                "Sala de Aula",
                "Onde a mágica (e às vezes o sono) acontece.",
                "src/resources/locais/sala-de-aula.png",
                "src/resources/locais/audio/musica-tema-sala-aula.mp3",
                true // aula
        );
        lista.add(salaDeAula);

        return lista;
    }
}


