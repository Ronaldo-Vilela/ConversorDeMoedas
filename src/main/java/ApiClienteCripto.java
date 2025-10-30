import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class ApiClienteCripto {

    private static final String API_URL = "https://api.coingecko.com/api/v3/simple/price";


    public double buscarPrecoCripto(String criptoId, String moedaFiat) throws IOException, InterruptedException {

        String url = API_URL + "?ids=" + criptoId + "&vs_currencies=" + moedaFiat;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        String jsonBody = response.body();



        Gson gson = new Gson();
        Type tipoMapa = new TypeToken<Map<String, Map<String, Double>>>() {}.getType();
        Map<String, Map<String, Double>> resposta = gson.fromJson(jsonBody, tipoMapa);


        if (resposta.containsKey(criptoId) && resposta.get(criptoId).containsKey(moedaFiat)) {
            return resposta.get(criptoId).get(moedaFiat);
        } else {
            throw new IOException("Não foi possível encontrar a cotação para " + criptoId + " em " + moedaFiat);
        }
    }
}