/**
This program uses Apache HTTP Client 4.2.4, HTTP Core 4.2.1, org.json and apache commons logging libraries
*/
import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class ImageAnalyzer
{
    // Replace the subscriptionKey string value with a valid subscription key.
    public static final String subscriptionKey = "da93971fbcd94567924f73e84fa4df3d";
    // Replace the rest end point specific to the region you have selected,
    // This endpoint can be found in your service overview page
    // Don't forget to append /analyze in the end if not available in your overview page
    public static final String uriBase = "https://westeurope.api.cognitive.microsoft.com/vision/v2.0/analyze";

    public static void main(String[] args)
    {
        HttpClient httpClient = new DefaultHttpClient();
        try
        {
            URIBuilder uriBuilder = new URIBuilder(uriBase);
            // Set request parameters. These are optional params.
            uriBuilder.setParameter("visualFeatures", "Categories,Description,Color");
            uriBuilder.setParameter("language", "en");
            // Prepare the URI for the REST API call.
            URI uri = uriBuilder.build();
            HttpPost request = new HttpPost(uri);
            // Request headers.
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
            // Request body.
            StringEntity reqEntity = new StringEntity("{\"url\":\"https://upload.wikimedia.org/wikipedia/commons/1/12/Broadway_and_Times_Square_by_night.jpg\"}");
            request.setEntity(reqEntity);
            // Execute the REST API call and get the response entity.
            HttpResponse httpResponse = httpClient.execute(request);
            HttpEntity httpEntity = httpResponse.getEntity();

            if (httpEntity != null)
            {
                // Format and display the JSON response.
                String jsonString = EntityUtils.toString(httpEntity);
                JSONObject json = new JSONObject(jsonString);
                System.out.println("Azure Computer Vision REST API Response:\n");
                System.out.println(json.toString(2));
            }
        }
        catch (Exception e)
        {
            // Display error message.
            System.out.println(e.getMessage());
        }
    }
}