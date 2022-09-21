package cybersoft.java18.crm.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter implements JsonSerializer<LocalDate> {
    public final static DateTimeFormatter DATE_FORMATER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(date.format(DATE_FORMATER)); // "yyyy-mm-dd"
    }
}