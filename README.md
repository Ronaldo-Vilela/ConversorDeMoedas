# 🪙 Conversor de Moedas | Alura Challenge 🪙

![Status](https://img.shields.io/badge/status-concluído-brightgreen)
![Java](https://img.shields.io/badge/Java-11%2B-blue)
![Maven](https://img.shields.io/badge/Maven-3.8%2B-red)

Este projeto é a solução para o **Challenge - Conversor de Moedas** proposto pela [Alura](https://www.alura.com.br/) em parceria com a [Oracle (ONE)](https://www.oracle.com/br/education/oracle-next-education/).

A aplicação funciona via console e permite ao usuário converter valores entre Reais (BRL), as principais moedas fiduciárias internacionais e, como funcionalidade extra, as criptomoedas mais populares, consumindo APIs em tempo real.

---

## 🚀 Funcionalidades

O projeto cumpre todos os requisitos básicos do desafio e vai além, adicionando a conversão de criptomoedas.

### Requisitos Básicos (Concluídos)
* Conversão de Reais (BRL) para:
    * Dólar (USD)
    * Euro (EUR)
    * Libras Esterlinas (GBP)
    * Peso Argentino (ARS)
    * Peso Chileno (CLP)
* Conversão inversa (de todas as moedas acima para BRL).

### Funcionalidades Adicionais (Extras)
* Conversão de Reais (BRL) para Criptomoedas (Bitcoin, Ethereum, Litecoin).
* Conversão inversa (de Criptomoedas para BRL).
* Menu interativo e loop de execução para múltiplas conversões.
* Consumo de duas APIs distintas para cotações em tempo real.

---

## 🛠️ Tecnologias Utilizadas

* **Java (JDK 11+)**: Linguagem principal do projeto.
* **Maven**: Gerenciamento de dependências.
* **`HttpClient` (Java 11)**: Cliente HTTP nativo do Java para realizar as requisições às APIs.
* **Gson**: Biblioteca do Google para "parsear" (converter) as respostas JSON das APIs para objetos Java.
* **APIs Externas**:
    * [**ExchangeRate-API**](https://www.exchangerate-api.com/): Utilizada para obter as cotações das moedas fiduciárias.
    * [**CoinGecko API**](https://www.coingecko.com/api): Utilizada para obter as cotações das criptomoedas.


