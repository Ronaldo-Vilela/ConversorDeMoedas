import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiCliente {

    // Coloque sua API Key aqui
    private static final String API_KEY = "4caa8ce565ba17e2032992a1";

    public TaxaDeCambioResponse buscarTaxas(String moedaBase) throws IOException, InterruptedException {
        // Monta a URL da requisição
        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + moedaBase;

        // Cria o cliente HTTP e a requisição
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        // Envia a requisição e obtém a resposta como String
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        // Pega o corpo da resposta (o JSON)
        String jsonBody = response.body();

        // Converte o JSON para nossa classe Java usando o Gson
        Gson gson = new Gson();
        TaxaDeCambioResponse taxaResponse = gson.fromJson(jsonBody, TaxaDeCambioResponse.class);

        return taxaResponse;
    }
}