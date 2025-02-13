import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EnchorQuery {

    static final String url = "https://api.enchor.us/search";

    public String getEnchorLink(final String name, final String artist, final String charter) {
        final String jsonPayload = """
                {
                  "search": "%s",
                  "page": 1,
                  "per_page": 1,
                  "chartIssues": null,
                  "trackIssues": null,
                  "instrument": "guitar",
                  "artist": "%s",
                  "charter": "%s",
                  "sort": {
                    "type": "name",
                    "direction": "asc"
                  }
                }
                """.formatted(name, artist, charter);

        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        String response = "";
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
//            System.out.println("Response Code: " + response.statusCode());
//            System.out.println("Response Body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode rootNode = objectMapper.readTree(response);

            // Extract linkToSong from first data object
            final JsonNode firstDataNode = rootNode.path("data").get(0);
            final String linkToSong = firstDataNode.path("md5").asText();

//            System.out.println("Extracted MD5: " + linkToSong);
            return linkToSong;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
