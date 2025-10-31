import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiCliente {


    private static final String API_KEY = "SUA_API_KEY_AQUI";

    public TaxaDeCambioResponse buscarTaxas(String moedaBase) throws IOException, InterruptedException {

        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + moedaBase;


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());


        String jsonBody = response.body();


        Gson gson = new Gson();
        TaxaDeCambioResponse taxaResponse = gson.fromJson(jsonBody, TaxaDeCambioResponse.class);

        return taxaResponse;
    }
}
