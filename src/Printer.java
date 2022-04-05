import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Printer {
    private static int pageLength = 5;
    private static int totalPages;

    static void setPageLength(int page_length) {
        pageLength = page_length;
    }

    public static int getTotalPages() {
        return totalPages;
    }
    

    public static void print(ArrayList<String> toPrint, int currentPage) {

        totalPages = (int) Math.ceil((double) toPrint.size() / pageLength);

        for (int i = currentPage * pageLength; i < currentPage * pageLength + pageLength; i++) {
            if (i >= toPrint.size()) {
                break;
            }
            System.out.println(toPrint.get(i));
        }

        System.out.println("---PAGE " + (currentPage + 1) + " OF " + totalPages + "---");
    }

    public static ArrayList<String> printNewAlbums(JsonArray albums) {
        ArrayList<String> toPrint = new ArrayList<>();

        for (int i = 0; i < albums.size(); i++) {
            JsonObject album = albums.get(i).getAsJsonObject();
            // album title
            StringBuilder albumSB = new StringBuilder();

            albumSB.append(album.get("name").getAsString() + "\n");

            // artists
            StringBuilder artists = new StringBuilder("[");
            JsonArray artistsArr = album.get("artists").getAsJsonArray();
            for (int j = 0; j < artistsArr.size(); j++) {
                artists.append(artistsArr.get(j)
                        .getAsJsonObject().get("name").getAsString());
                if (j < artistsArr.size() - 1) {
                    artists.append(", ");
                }
            }
            artists.append("]");

            albumSB.append(artists.toString() + "\n");

            // spotify link
            albumSB.append(album.get("external_urls")
                    .getAsJsonObject().get("spotify").toString().replace("\"", "") + "\n");

            toPrint.add(albumSB.toString());
        }

        return toPrint;
    }

    public static ArrayList<String> printFeaturedPlaylists(JsonArray playlists) {
        ArrayList<String> toPrint = new ArrayList<>();

        for (int i = 0; i < playlists.size(); i++) {
            StringBuilder playlistSB = new StringBuilder();

            playlistSB.append(playlists.get(i)
                    .getAsJsonObject().get("name").getAsString() + "\n");
            playlistSB.append(playlists.get(i).getAsJsonObject().get("external_urls").
                    getAsJsonObject().get("spotify").getAsString() + "\n");
            toPrint.add(playlistSB.toString());
        }
        return toPrint;
    }

    public static ArrayList<String> printCategories(JsonArray categories) {
        ArrayList<String> toPrint = new ArrayList<>();

        for (int i = 0; i < categories.size(); i++) {
            toPrint.add(categories.get(i).getAsJsonObject().get("name").getAsString());
        }
        return toPrint;
    }
}
