import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

fun main() {
    try {
        // Define the URL for the POST request
        val url = URL("http://127.0.0.1:8000/chats")
        val con = url.openConnection() as HttpURLConnection

        // Set the request method to POST
        con.requestMethod = "POST"
        con.setRequestProperty("Content-Type", "application/json; utf-8")
        con.setRequestProperty("Accept", "application/json")
        con.doOutput = true

        // Create JSON request body
        val jsonInputString = """{"sender": "aran", "content": "can I plant maize"}"""

        // Send the JSON input string
        DataOutputStream(con.outputStream).use { out ->
            out.writeBytes(jsonInputString)
            out.flush()
        }

        // Get the response code
        val status = con.responseCode

        // Read the response
        BufferedReader(InputStreamReader(con.inputStream, StandardCharsets.UTF_8)).use { reader ->
            val content = StringBuilder()
            var inputLine: String?
            while (reader.readLine().also { inputLine = it } != null) {
                content.append(inputLine)
            }

            // Print the response content
            println(content.toString())
        }

        // Close the connection
        con.disconnect()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
