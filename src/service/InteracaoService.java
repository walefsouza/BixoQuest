package service;

import model.entidades.Jogador;
import model.entidades.Animal;
import model.interacao.CategoriaDialogo;
import model.mapa.Local;
import model.mapa.TipoLocal;
import model.interacao.Dialogo;
import repository.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InteracaoService {

    private final IRepository<Dialogo> dialogoRepository;
    private final Random random;

    public InteracaoService(IRepository<Dialogo> dialogoRepository) {
        this.dialogoRepository = dialogoRepository;
        this.random = new Random();
    }

    private List<Dialogo> buscarFalasDoLocal(Local localAtual) {
        TipoLocal tipoAtual = localAtual.getTipo();
        List<Dialogo> falasDoLocal = new ArrayList<>();

        for (Dialogo d : dialogoRepository.listar()) {
            if (d.getLocalDialogo() == tipoAtual || d.getLocalDialogo() == TipoLocal.QUALQUER_LUGAR) {
                falasDoLocal.add(d);
            }
        }
        return falasDoLocal;
    }

    public Dialogo conversar(Local localAtual) {
        List<Dialogo> falas = buscarFalasDoLocal(localAtual);
        if (falas.isEmpty()) return null;

        int nAleatorio = random.nextInt(falas.size());
        return falas.get(nAleatorio);
    }

    public Dialogo conversarPorCategoria(Local localAtual, CategoriaDialogo categoria) {
        List<Dialogo> falas = new ArrayList<>();

        for (Dialogo d : buscarFalasDoLocal(localAtual)) {
            if (d.getCategoria() == categoria) {
                falas.add(d);
            }
        }

        if (falas.isEmpty()) return null;

        int nAleatorio = random.nextInt(falas.size());
        return falas.get(nAleatorio);
    }

    public boolean interagirComAnimal(Jogador jogador, Animal animal) {

        if (jogador == null || animal == null) {
            return false;
        }

        if (animal.aceitaCarinho()) {
            jogador.aumentarMotivacao(animal.calcularGanhoMotivacao());
            return true;
        }

        // Se o animal atacar, dispara uma chuva de reduções a partir do danoBase
        int danoBase = animal.calcularDanoAtaque();
        jogador.decrementarSaude(danoBase);
        jogador.decrementarEnergia(danoBase / 2);
        jogador.decrementarMotivacao(danoBase / 3);

        return false;
    }
}