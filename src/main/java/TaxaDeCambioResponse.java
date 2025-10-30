import java.util.Map;


public class TaxaDeCambioResponse {


    private String base_code; // Moeda base (ex: "BRL")


    private Map<String, Double> conversion_rates;


    public String getBase_code() {
        return base_code;
    }

    public Map<String, Double> getConversion_rates() {
        return conversion_rates;
    }
}
