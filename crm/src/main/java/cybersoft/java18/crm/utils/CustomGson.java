package cybersoft.java18.crm.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class CustomGson {
    public final static Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                @Override
                public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    return LocalDate.parse(json.getAsJsonPrimitive().getAsString(), LocalDateAdapter.DATE_FORMATER);
                }
            })
            .create();
}
