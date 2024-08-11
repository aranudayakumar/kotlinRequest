import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PostRequest {
    public static void main(String[] args) {
        try {
            // Define the URL for the POST request
            URL url = new URL("http://127.0.0.1:8000/chats");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // Set the request method to POST
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            // Create JSON request body
            String jsonInputString = "{\"sender\": \"aran\", \"content\": \"can I plant maize\"}";

            // Send the JSON input string
            try (DataOutputStream out = new DataOutputStream(con.getOutputStream())) {
                out.writeBytes(jsonInputString);
                out.flush();
            }

            // Get the response code
            int status = con.getResponseCode();

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Close the connections
            in.close();
            con.disconnect();

            // Print the response content
            System.out.println(content.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
