# Estação Meteorológica API

Este projeto consiste em uma API para gerenciar dados meteorológicos de estações meteorológicas. A aplicação foi construída utilizando **Spring Boot** e **MongoDB** como banco de dados, com foco em fornecer endpoints para consulta de informações de estações e dados meteorológicos registrados ao longo dos anos.

## Funcionalidades

- **Listar informações de estações meteorológicas** por ano.
- **Consultar dados meteorológicos** específicos de uma estação por código e ano.
- **Paginação** dos dados meteorológicos registrados para uma estação.
- **Resumo de dados meteorológicos** por data, fornecendo métricas como temperatura média, precipitação, umidade, entre outros.

## Estrutura do Projeto

- **Controllers**: Responsáveis por expor os endpoints da API.
- **Services**: Contêm a lógica de negócio para manipular e consultar os dados.
- **Repositories**: Interagem diretamente com o MongoDB para buscar e paginar os dados.

### Endpoints

1. **Obter informações de todas as estações meteorológicas**:
   - `GET /api/estacoes/info`
   - Retorna uma lista de informações sobre as estações disponíveis.

2. **Obter informações de uma estação específica por código**:
   - `GET /api/estacoes/info/{code}`
   - Retorna informações detalhadas de uma estação específica pelo código.

3. **Resumo dos dados meteorológicos de uma estação por data**:
   - `GET /api/estacoes/data/{code}?date=2024-01-01`
   - Retorna um resumo dos dados meteorológicos em uma data específica.

4. **Retorna os dados meteorológicos de uma estação por código, ano e mês**:
   - `GET /api/estacoes/data/{code}/{year}/{month}`
   - Retorna os dados meteorológicos(e as médias) de uma estação específica a partir do ano e mês.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **MongoDB**
- **Maven**

## Configuração do Projeto

### Pré-requisitos

- **Java 17** ou superior
- **Maven** 3.8.1+
- **MongoDB** (local ou em nuvem)

### Passos para execução

1. Clone o repositório:
   ```bash
   git clone https://github.com/dinizhugo/backend-estacao-meteorologica.git
   cd seu-repositorio
   ```

2. Compile o projeto:
   ```bash
   mvn clean install
   ```

3. Execute o projeto:
   ```bash
   mvn spring-boot:run
   ```

4. Acesse a API em: `http://localhost:8080/api/estacoes`.

## Estrutura de Dados

### Station Information

Representa uma estação meteorológica e suas informações básicas.

```json
{
   "id": "Codigo da estação",
   "uf": "Uf",
   "estacao": "Nome da estação",
   "codigo": "Codigo",
   "latitude": "Latitude",
   "longitude": "Longitude",
   "dataFundacao": "DD/MM/YYYY"
}
```

### Data

Contém as leituras meteorológicas de uma estação.

```json
{
     "metrics": [
       {
         "hora": "0000 UTC",
         "precipitacaoTotal": 0.0,
         "pressaoAtmosfericaNivelEstacao": 950.0,
         "pressaoAtmosfericaMax": 950.0,
         "pressaoAtmosfericaMin": 949.4,
         ...
       }
        ...
     ],
     "averages": {
       "mediaPrecipitacaoTotal": 0.0,
       "mediaPressaoAtmosfericaNivelEstacao": 948.8249999999999,
       "mediaPressaoAtmosfericaMax": 949.0833333333334,
       "mediaPressaoAtmosfericaMin": 948.525,
       ...,
     }
}
```
