package pl.wroclaw.asi.sesjalinuksowa.utils;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.wroclaw.asi.sesjalinuksowa.models.Lecture;
import pl.wroclaw.asi.sesjalinuksowa.models.Organizer;
import pl.wroclaw.asi.sesjalinuksowa.models.Partner;
import pl.wroclaw.asi.sesjalinuksowa.models.Speaker;

/**
 * Created by fares on 09.04.15.
 */
public class UtilParser {

    private static boolean isJsonFormat(String json){
        try {
            new JSONObject(json);
        } catch (JSONException ex) {
            return false;
        }
        return true;
    }

    public static List<Speaker> parseJsonSpeakers(@NonNull String json){
        Gson gson = new Gson();
        Speaker[] speakers = gson.fromJson(json, Speaker[].class);
        return new ArrayList<>(Arrays.asList(speakers));
    }

    public static List<Lecture> parseJsonLectures(@NonNull String json){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Lecture[].class, new JsonDeserializerLectures());
        Gson gson = builder.create();

        Lecture[] lectures = gson.fromJson(json, Lecture[].class);
        return new ArrayList<>(Arrays.asList(lectures));
    }

    public static List<Partner> parseJsonPartners(@NonNull String json){
        Gson gson = new Gson();
        Partner[] partners = gson.fromJson(json, Partner[].class);
        return new ArrayList<>(Arrays.asList(partners));
    }

    public static List<Organizer> parseJsonOrganizers(@NonNull String json){
        Gson gson = new Gson();
        Organizer[] organizers = gson.fromJson(json, Organizer[].class);
        return new ArrayList<>(Arrays.asList(organizers));
    }

    private static class JsonDeserializerLectures implements JsonDeserializer<Lecture[]>{
        @Override
        public Lecture[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            final JsonArray jsonLectures = json.getAsJsonArray();  // get array of lectures
            Lecture[] lectures = new Lecture[jsonLectures.size()];
            for (int i = 0; i < jsonLectures.size(); i++){
                final JsonElement jsonLecture = jsonLectures.get(i);
                Lecture newLecture = new Gson().fromJson(jsonLecture, Lecture.class);
                final JsonArray jsonSpeakersIds = jsonLectures.get(i).getAsJsonObject().get("speakers").getAsJsonArray();

                List<Integer> speakersIds = new ArrayList<>();
                for (int k = 0; k < jsonSpeakersIds.size(); k++) {
                    JsonObject idObj = jsonSpeakersIds.get(k).getAsJsonObject();
                    speakersIds.add(idObj.get("id").getAsInt());
                }
                newLecture.setSpeakersIds(speakersIds);

                lectures[i] = newLecture;
            }
            return lectures;
        }
    }

}
