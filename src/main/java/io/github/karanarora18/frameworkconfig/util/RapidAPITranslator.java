package io.github.karanarora18.frameworkconfig.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.github.karanarora18.frameworkconfig.ConfigManager;
import okhttp3.*;

import java.io.IOException;

public class RapidAPITranslator {
    public static String translate(String from_language, String to_language, String text){
        String RAPID_KEY=ConfigManager.getConfigValue("RAPID_KEY");
//        System.out.println(RAPID_KEY);
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "" +
                "{\"from\":\""+from_language+"\"," +
                "\"to\":\""+to_language+"\"," +
                "\"q\":\""+text+"\"}");
        Request request = new Request.Builder()
                .url("https://rapid-translate-multi-traduction.p.rapidapi.com/t")
                .post(body)
                .addHeader("x-rapidapi-key", RAPID_KEY)
                .addHeader("x-rapidapi-host", ConfigManager.getConfigValue("RAPID_HOST"))
                .addHeader("Content-Type", "application/json")
                .build();
        Response response;
        try {
             response = client.newCall(request).execute();
             String response_body = response.body().string();
            JsonElement jsonResponse = JsonParser.parseString(response_body);

            if (jsonResponse.isJsonArray()) {
                JsonArray jsonArray = jsonResponse.getAsJsonArray();
                if (!jsonArray.isEmpty()) {
                    return jsonArray.get(0).getAsString();
                }
            } else if (jsonResponse.isJsonObject() && jsonResponse.getAsJsonObject().has("result")) {
                return jsonResponse.getAsJsonObject().get("result").getAsString();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.toString();
    }
}


