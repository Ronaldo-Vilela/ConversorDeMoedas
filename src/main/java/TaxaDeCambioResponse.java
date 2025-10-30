import java.util.Map;

// Esta classe é um "POJO" (Plain Old Java Object)
// O Gson usará esta classe para guardar a resposta do JSON
public class TaxaDeCambioResponse {

    // O nome da variável DEVE ser igual ao da chave no JSON
    private String base_code; // Moeda base (ex: "BRL")

    // Usamos um Map para guardar as várias taxas de conversão
    private Map<String, Double> conversion_rates;

    // Getters (O Gson precisa deles)
    public String getBase_code() {
        return base_code;
    }

    public Map<String, Double> getConversion_rates() {
        return conversion_rates;
    }
}
