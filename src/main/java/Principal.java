import java.io.IOException;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        ApiCliente apiClienteFiat = new ApiCliente(); // Renomeei para clareza
        ApiClienteCripto apiClienteCripto = new ApiClienteCripto();


        String[] moedasFiatAlvo = {"USD", "EUR", "GBP", "ARS", "CLP"};


        String[] criptosDisponiveis = {"Bitcoin", "Ethereum", "Litecoin"};

        String[] criptoIds = {"bitcoin", "ethereum", "litecoin"};

        try {
            while (true) {
                System.out.println("**************************************************");
                System.out.println("Bem-vindo ao Conversor Global (Moedas e Criptos)!");
                System.out.println("\n--- Conversor de Moedas (Fiat) ---");
                System.out.println("1) Converter de Real (BRL) para Moeda Estrangeira");
                System.out.println("2) Converter de Moeda Estrangeira para Real (BRL)");
                System.out.println("\n--- Conversor de Criptomoedas ---");
                System.out.println("3) Converter de Real (BRL) para Criptomoeda");
                System.out.println("4) Converter de Criptomoeda para Real (BRL)");
                System.out.println("\n0) Sair");
                System.out.println("**************************************************");
                System.out.print("Escolha uma opção: ");

                int escolhaMenu = scanner.nextInt();
                if (escolhaMenu == 0) {
                    System.out.println("Saindo... Obrigado por usar o conversor!");
                    break;
                }


                System.out.print("Digite o valor a ser convertido: ");
                double valor = scanner.nextDouble();

                switch (escolhaMenu) {

                    case 1:
                        System.out.println("Escolha a moeda de destino:");
                        for (int i = 0; i < moedasFiatAlvo.length; i++) {
                            System.out.println((i + 1) + ") " + moedasFiatAlvo[i]);
                        }
                        int escolhaMoeda = scanner.nextInt();
                        String moedaDestino = moedasFiatAlvo[escolhaMoeda - 1];

                        TaxaDeCambioResponse taxasBRL = apiClienteFiat.buscarTaxas("BRL");
                        double taxaConversao = taxasBRL.getConversion_rates().get(moedaDestino);

                        double resultado = valor * taxaConversao;
                        System.out.printf("R$ %.2f (BRL) equivalem a %.2f (%s)%n", valor, resultado, moedaDestino);
                        break;

                    case 2: // Outra Moeda Fiat -> BRLm.out.println("Escolha a moeda de origem:");
                        for (int i = 0; i < moedasFiatAlvo.length; i++) {
                            System.out.println((i + 1) + ") " + moedasFiatAlvo[i]);
                        }
                        int escolhaMoedaOrigem = scanner.nextInt();
                        String moedaOrigem = moedasFiatAlvo[escolhaMoedaOrigem - 1];

                        TaxaDeCambioResponse taxasOrigem = apiClienteFiat.buscarTaxas(moedaOrigem);
                        double taxaConversaoBRL = taxasOrigem.getConversion_rates().get("BRL");

                        double resultadoBRL = valor * taxaConversaoBRL;
                        System.out.printf("%.2f (%s) equivalem a R$ %.2f (BRL)%n", valor, moedaOrigem, resultadoBRL);
                        break;


                    case 3:
                        System.out.println("Escolha a Criptomoeda de destino:");
                        for (int i = 0; i < criptosDisponiveis.length; i++) {
                            System.out.println((i + 1) + ") " + criptosDisponiveis[i]);
                        }
                        int escolhaCripto = scanner.nextInt();
                        String criptoIdDestino = criptoIds[escolhaCripto - 1];
                        String nomeCripto = criptosDisponiveis[escolhaCripto - 1];


                        double precoCriptoEmBRL = apiClienteCripto.buscarPrecoCripto(criptoIdDestino, "brl");


                        double valorEmCripto = valor / precoCriptoEmBRL;

                        System.out.printf("R$ %.2f (BRL) equivalem a %f (%s)%n", valor, valorEmCripto, nomeCripto);
                        System.out.printf("(Cotação: 1 %s = R$ %.2f)%n", nomeCripto, precoCriptoEmBRL);
                        break;

                    case 4:
                        System.out.println("Escolha a Criptomoeda de origem:");
                        for (int i = 0; i < criptosDisponiveis.length; i++) {
                            System.out.println((i + 1) + ") " + criptosDisponiveis[i]);
                        }
                        int escolhaCriptoOrigem = scanner.nextInt();
                        String criptoIdOrigem = criptoIds[escolhaCriptoOrigem - 1];
                        String nomeCriptoOrigem = criptosDisponiveis[escolhaCriptoOrigem - 1];

                        double precoCriptoOrigemEmBRL = apiClienteCripto.buscarPrecoCripto(criptoIdOrigem, "brl");


                        double valorConvertidoEmBRL = valor * precoCriptoOrigemEmBRL;

                        System.out.printf("%f (%s) equivalem a R$ %.2f (BRL)%n", valor, nomeCriptoOrigem, valorConvertidoEmBRL);
                        System.out.printf("(Cotação: 1 %s = R$ %.2f)%n", nomeCriptoOrigem, precoCriptoOrigemEmBRL);
                        break;

                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao buscar dados da API. Verifique sua conexão ou API Key.");
            System.err.println("Detalhe: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());

        } finally {
            scanner.close();
        }
    }
}