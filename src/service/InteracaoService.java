package service;

import com.google.gson.reflect.TypeToken;
import model.atividades.ResultadoAcao;
import model.entidades.Jogador;
import model.entidades.Animal;
import model.interacao.CategoriaDialogo;
import model.mapa.Local;
import model.mapa.TipoLocal;
import model.interacao.Dialogo;
import repository.IRepository;
import repository.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InteracaoService {

    // Atributos - - - - - - - - - - - - - - - - - - - - - - - - -

    private static IRepository<Dialogo> dialogoRepository;
    private Random random;

    // Construtor  - - - - - - - - - - - - - - - - - - - - - - - -

    public InteracaoService(IRepository<Dialogo> dialogoRepository) {

        InteracaoService.dialogoRepository = dialogoRepository;
        this.random = new Random();

    }

    // Sobrecarga do construtor com endereço nos repositórios
    public InteracaoService() {

        this.random = new Random();

        if (dialogoRepository == null) {
            dialogoRepository = new Repository<>("dados/dialogos.json", new TypeToken<ArrayList<Dialogo>>(){}.getType());
        }
    }

    // Métodos  - - - - - - - - - - - - - - - - - - - - - - - -

    // Método base para buscar todas as falas de determinado local
    public List<Dialogo> buscarFalasDoLocal(TipoLocal localAtual) {
        TipoLocal tipoAtual = localAtual;
        List<Dialogo> falasDoLocal = new ArrayList<>();

        for (Dialogo d : dialogoRepository.listar()) {
            if (d.getLocalDialogo() == tipoAtual || d.getLocalDialogo() == TipoLocal.QUALQUER_LUGAR) {
                falasDoLocal.add(d);
            }
        }
        return falasDoLocal;
    }

    // Gera diálogos aleatórios sobre aquele local
    public ResultadoAcao conversar(Local localAtual) {

        List<Dialogo> falas = buscarFalasDoLocal(localAtual.getTipo());

        // Se não houver ninguém para conversar
        if (falas.isEmpty()) {
            return new ResultadoAcao("Não há ninguém interessante para conversar aqui no momento.");
        }

        // Sorteando diálogo
        int nAleatorio = random.nextInt(falas.size());
        Dialogo dialogo = falas.get(nAleatorio);

        // Descompactando diálogo e retornando texto no DTO
        ResultadoAcao resultado = new ResultadoAcao("Você puxa assunto... "+ dialogo.getTexto() + ".");
        return resultado;
    }

    // Filtra falas por categoria, isso ajuda a segmentar as falas por contexto
    public ResultadoAcao conversarPorCategoria(Local localAtual, CategoriaDialogo categoria) {
        List<Dialogo> falas = new ArrayList<>();

        for (Dialogo d : buscarFalasDoLocal(localAtual.getTipo())) {
            if (d.getCategoria() == categoria) {
                falas.add(d);
            }
        }
        // Se não houver ninguém para conversar
        if (falas.isEmpty()) {
            return new ResultadoAcao("Ninguém quer falar sobre " + categoria.name() + " agora.");
        }

        // Sorteando diálogo
        int nAleatorio = random.nextInt(falas.size());
        Dialogo dialogo = falas.get(nAleatorio);

        // Descompactando diálogo e retornando texto no DTO
        ResultadoAcao resultado = new ResultadoAcao("Você pergunta sobre: " +
                categoria.name()+ dialogo.getTexto() + ".");

        return resultado;
    }

    // O jogador pode acariciar um animal e se ele estiver raivoso vai ser penalizado
    public ResultadoAcao interagirComAnimal(Jogador jogador, Animal animal) {

        ResultadoAcao resultado;

        // Se não houver nenhum animal
        if (jogador == null || animal == null) {
            return new ResultadoAcao("Não há nenhum animal aqui.");
        }

        // Se o animal está aceitando carinho aumenta motivação
        if (animal.aceitaCarinho()) {

            jogador.aumentarMotivacao(animal.calcularGanhoMotivacao());
            resultado = new ResultadoAcao("Você fez carinho no " + animal.getNome() + ". Sua motivação aumentou.");
            resultado.setTocarAudio("src/resources/atividades/audio/animal-gato--miando.mp3");
            return resultado;
        }

        // Se o animal atacar, dispara uma chuva de reduções a partir do danoBase
        int danoBase = animal.calcularDanoAtaque();
        jogador.decrementarSaude(danoBase);
        jogador.decrementarEnergia(danoBase / 2);
        jogador.decrementarMotivacao(danoBase / 3);

        resultado = new ResultadoAcao("CUIDADO! O " + animal.getNome() + " te atacou! Você perdeu saúde e energia.");
        resultado.setTocarAudio("src/resources/atividades/audio/animal-gato-rosnando.mp3");
        resultado.setTremerTela(true);

        return resultado;
    }
}