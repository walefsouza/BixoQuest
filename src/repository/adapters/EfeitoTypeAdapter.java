package repository.adapters;

import com.google.gson.*;
import model.atividades.Efeito;
import model.atividades.EfeitoAtributos;
import model.atividades.EfeitoMidia;
import java.lang.reflect.Type;

public class EfeitoTypeAdapter implements JsonDeserializer<Efeito> {

    @Override
    public Efeito deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject obj = json.getAsJsonObject();

        // Se tem campo de atributos, é EfeitoAtributos
        if (obj.has("impactoEnergia") || obj.has("impactoConhecimento") ||
                obj.has("impactoMotivacao") || obj.has("impactoSaude") ||
                obj.has("impactoDesempenho") || obj.has("impactoDinheiro")) {
            return context.deserialize(obj, EfeitoAtributos.class);
        }

        // Se tem campos de mídia, é EfeitoMidia
        if (obj.has("arquivoAudio") || obj.has("tremerTela") ||
                obj.has("escurecerTela") || obj.has("desfocarTela") ||
                obj.has("mudarImagemFundo") || obj.has("iconeSobreposicao")) {
            return context.deserialize(obj, EfeitoMidia.class);
        }

        throw new JsonParseException("Não foi possível determinar o tipo de Efeito: " + obj);
    }
}