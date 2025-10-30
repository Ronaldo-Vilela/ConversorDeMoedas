import java.io.IOException;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ApiCliente apiCliente = new ApiCliente();

        // Moedas alvo conforme o desafio
        String[] moedasAlvo = {"USD", "EUR", "GBP", "ARS", "CLP"};

        try {
            while (true) {
                System.out.println("**************************************************");
                System.out.println("Bem-vindo ao Conversor de Moedas!");
                System.out.println("1) Converter de Real (BRL) para outra moeda");
                System.out.println("2) Converter de outra moeda para Real (BRL)");
                System.out.println("0) Sair");
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
                    case 1: // BRL -> Outra Moeda
                        System.out.println("Escolha a moeda de destino:");
                        for (int i = 0; i < moedasAlvo.length; i++) {
                            System.out.println((i + 1) + ") " + moedasAlvo[i]);
                        }
                        int escolhaMoeda = scanner.nextInt();
                        String moedaDestino = moedasAlvo[escolhaMoeda - 1];

                        // Busca as taxas partindo do BRL
                        TaxaDeCambioResponse taxasBRL = apiCliente.buscarTaxas("BRL");
                        double taxaConversao = taxasBRL.getConversion_rates().get(moedaDestino);

                        double resultado = valor * taxaConversao;
                        System.out.printf("R$ %.2f (BRL) equivalem a %.2f (%s)%n", valor, resultado, moedaDestino);
                        break;

                    case 2: // Outra Moeda -> BRL
                        System.out.println("Escolha a moeda de origem:");
                        for (int i = 0; i < moedasAlvo.length; i++) {
                            System.out.println((i + 1) + ") " + moedasAlvo[i]);
                        }
                        int escolhaMoedaOrigem = scanner.nextInt();
                        String moedaOrigem = moedasAlvo[escolhaMoedaOrigem - 1];

                        // Busca as taxas partindo da Moeda de Origem
                        TaxaDeCambioResponse taxasOrigem = apiCliente.buscarTaxas(moedaOrigem);
                        double taxaConversaoBRL = taxasOrigem.getConversion_rates().get("BRL");

                        double resultadoBRL = valor * taxaConversaoBRL;
                        System.out.printf("%.2f (%s) equivalem a R$ %.2f (BRL)%n", valor, moedaOrigem, resultadoBRL);
                        break;

                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao buscar dados da API: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
