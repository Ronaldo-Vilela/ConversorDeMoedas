import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConversorGrafico {


    private ApiCliente apiClienteFiat;
    private ApiClienteCripto apiClienteCripto;


    private JFrame janela;
    private JTabbedPane abas;
    private JPanel painelResultado;
    private JTextField campoValor;
    private JButton botaoConverter;
    private JLabel labelResultado;


    private JPanel painelFiat;
    private JComboBox<String> comboMoedaDe;
    private JComboBox<String> comboMoedaPara;
    private final String[] moedasFiat = {"BRL", "USD", "EUR", "GBP", "ARS", "CLP"};


    private JPanel painelCripto;
    private JComboBox<String> comboTipoConversaoCripto;
    private JComboBox<String> comboCripto;

    private final String[] criptosDisponiveis = {"Bitcoin", "Ethereum", "Litecoin"};

    private final String[] criptoIds = {"bitcoin", "ethereum", "litecoin"};


    public ConversorGrafico() {

        apiClienteFiat = new ApiCliente();
        apiClienteCripto = new ApiClienteCripto();


        janela = new JFrame("Conversor Global 🌎 (Fiat & Cripto)");
        janela.setSize(450, 300);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLayout(new BorderLayout());


        campoValor = new JTextField(10);
        botaoConverter = new JButton("Converter");
        labelResultado = new JLabel("Resultado:");
        labelResultado.setFont(new Font("Arial", Font.BOLD, 16));
        labelResultado.setHorizontalAlignment(SwingConstants.CENTER);


        painelFiat = new JPanel(new GridLayout(3, 2, 10, 10));
        painelFiat.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        comboMoedaDe = new JComboBox<>(moedasFiat);
        comboMoedaPara = new JComboBox<>(moedasFiat);

        painelFiat.add(new JLabel("Valor:"));
        painelFiat.add(campoValor);
        painelFiat.add(new JLabel("Converter de:"));
        painelFiat.add(comboMoedaDe);
        painelFiat.add(new JLabel("Para:"));
        painelFiat.add(comboMoedaPara);


        painelCripto = new JPanel(new GridLayout(3, 2, 10, 10));
        painelCripto.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] tiposConversao = {"Real (BRL) para Cripto", "Cripto para Real (BRL)"};
        comboTipoConversaoCripto = new JComboBox<>(tiposConversao);
        comboCripto = new JComboBox<>(criptosDisponiveis);


        painelCripto.add(new JLabel("Tipo de Conversão:"));
        painelCripto.add(comboTipoConversaoCripto);
        painelCripto.add(new JLabel("Criptomoeda:"));
        painelCripto.add(comboCripto);
        painelCripto.add(new JLabel("(O valor é pego da aba 'Moedas')"));


        abas = new JTabbedPane();
        abas.addTab("Moedas Fiduciárias", painelFiat);
        abas.addTab("Criptomoedas", painelCripto);


        painelResultado = new JPanel(new GridLayout(2, 1, 10, 10));
        painelResultado.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelResultado.add(botaoConverter);
        painelResultado.add(labelResultado);


        janela.add(abas, BorderLayout.CENTER);
        janela.add(painelResultado, BorderLayout.SOUTH);


        botaoConverter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int abaSelecionada = abas.getSelectedIndex();

                if (abaSelecionada == 0) {

                    converterMoedaFiat();
                } else {

                    converterCripto();
                }
            }
        });


        janela.setVisible(true);
        janela.setLocationRelativeTo(null);
    }


    private void converterMoedaFiat() {
        try {
            String valorTexto = campoValor.getText();
            String moedaOrigem = (String) comboMoedaDe.getSelectedItem();
            String moedaDestino = (String) comboMoedaPara.getSelectedItem();

            if (valorTexto.isEmpty()) {
                JOptionPane.showMessageDialog(janela, "Por favor, digite um valor.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double valor = Double.parseDouble(valorTexto);
            TaxaDeCambioResponse taxas = apiClienteFiat.buscarTaxas(moedaOrigem);
            double taxaConversao = taxas.getConversion_rates().get(moedaDestino);
            double resultado = valor * taxaConversao;

            labelResultado.setText(String.format("%.2f %s = %.2f %s", valor, moedaOrigem, resultado, moedaDestino));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(janela, "Por favor, digite um valor numérico válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(janela, "Erro ao buscar cotação (Fiat): " + ex.getMessage(), "Erro de API", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(janela, "Ocorreu um erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void converterCripto() {
        try {
            String valorTexto = campoValor.getText();
            int tipoConversaoIndex = comboTipoConversaoCripto.getSelectedIndex();
            int criptoIndex = comboCripto.getSelectedIndex();

            String criptoId = criptoIds[criptoIndex];
            String criptoNome = criptosDisponiveis[criptoIndex];

            if (valorTexto.isEmpty()) {
                JOptionPane.showMessageDialog(janela, "Por favor, digite um valor (na aba 'Moedas').", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double valor = Double.parseDouble(valorTexto);


            double precoCriptoEmBRL = apiClienteCripto.buscarPrecoCripto(criptoId, "brl");

            if (tipoConversaoIndex == 0) {

                double resultado = valor / precoCriptoEmBRL;
                labelResultado.setText(String.format("R$ %.2f = %f %s", valor, resultado, criptoNome));
            } else {

                double resultado = valor * precoCriptoEmBRL;
                labelResultado.setText(String.format("%f %s = R$ %.2f", valor, criptoNome, resultado));
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(janela, "Por favor, digite um valor numérico válido (na aba 'Moedas').", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(janela, "Erro ao buscar cotação (Cripto): " + ex.getMessage(), "Erro de API", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(janela, "Ocorreu um erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConversorGrafico();
            }
        });
    }
}
