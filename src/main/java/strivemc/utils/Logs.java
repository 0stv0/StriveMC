package strivemc.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import strivemc.enums.LogLevel;
import strivemc.enums.LogMode;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

public class Logs {

    private static final HttpClient client = HttpClient.newHttpClient();

    private static LogMode MODE   = LogMode.DISABLED;
    private static String WEBHOOK = "";

    public static void setWebhook(String webhook)
    {
        WEBHOOK = webhook;
    }
    public static void setMode(LogMode mode)
    {
        MODE = mode;
    }
    public static void send(LogLevel level, String content)
    {
        if (MODE == LogMode.DISABLED)
            return;
        if (MODE == LogMode.ONLY_MC || MODE == LogMode.BOTH)
            Bukkit.getConsoleSender().sendMessage(level.getTag() + " " + content);
        if (MODE == LogMode.ONLY_DC || MODE == LogMode.BOTH)
            sendDiscord(level, content);
    }
    private static void sendDiscord(LogLevel level, String content)
    {
        JsonObject payload = new JsonObject();
        JsonArray embeds   = new JsonArray();
        JsonObject embed   = new JsonObject();

        embed.addProperty("title", level.getTag().substring(2) + " Server Log");
        embed.addProperty("description", content);
        embed.addProperty("timestamp", Instant.now().toString());
        embeds.add(embed);
        payload.add("embeds", embeds);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(WEBHOOK))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
            .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }
}
