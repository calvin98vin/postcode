import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class Postcode {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User input
        System.out.print("Enter a UK postcode: ");
        String postcode = scanner.nextLine();
        String apiUrl = "https://api.postcodes.io/postcodes/" + postcode;

        try {
            // Set up HTTP connection
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get response code
            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                // Read response data
                String output;
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();

                while ((output = reader.readLine()) != null) {
                    response.append(output);
                }

                reader.close();

                // Parse JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());

                // Extract relevant information
                String country = jsonResponse.getJSONObject("result").getString("country");
                String territory = jsonResponse.getJSONObject("result").getString("nuts");
                double latitude = jsonResponse.getJSONObject("result").getDouble("latitude");
                double longitude = jsonResponse.getJSONObject("result").getDouble("longitude");

                // Remove comment for complete results
                // System.out.println("JSON Results for " + postcode.toUpperCase() + ":");
                // System.out.println(jsonResponse.getJSONObject("result").toString(4));
                // System.out.println("\n");

                // Display information
                System.out.println("Geographical information for Postcode " + postcode.toUpperCase());
                System.out.println("Country: " + country);
                System.out.println("Territory: " + territory);
                System.out.println("Latitude: " + latitude);
                System.out.println("Longitude: " + longitude);

            } else {
                System.out.println("Sorry, we are unable to retrieve information for Postcode " + postcode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}