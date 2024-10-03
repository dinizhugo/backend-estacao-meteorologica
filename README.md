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

1. **Obter informações de estações meteorológicas por ano**:
   - `GET /api/estacoes/info/{year}`
   - Retorna uma lista de informações sobre as estações disponíveis para o ano especificado.

2. **Obter informações de uma estação específica por código e ano**:
   - `GET /api/estacoes/info/{year}/{code}`
   - Retorna informações detalhadas de uma estação específica pelo código e ano.

3. **Obter todos os dados meteorológicos de uma estação por ano**:
   - `GET /api/estacoes/year/{id}`
   - Retorna todos os dados meteorológicos de uma estação por ano.

4. **Paginação dos dados meteorológicos de uma estação por código e ano**:
   - `GET /api/estacoes/data/{year}/{code}`
   - Retorna uma página com os dados meteorológicos de uma estação específica.

5. **Resumo dos dados meteorológicos de uma estação por data**:
   - `GET /api/estacoes/data/{code}`
   - Retorna um resumo dos dados meteorológicos em uma data específica.

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

### Station

Representa uma estação meteorológica e suas informações básicas.

```json
{
  "estacao": "Nome da Estação",
  "uf": "Estado",
  "codigo": "Código da Estação",
  "latitude": -7.12345,
  "longitude": -35.67890,
  "dataFundacao": "dd/mm/yy",
  "dados": []
}
```

### Data

Contém as leituras meteorológicas de uma estação.

```json
{
  "data": "yyyy-mm-dd",
  "hora": "hhhh UTC",
  "precipitacaoTotal": 12.34,
  "pressaoAtmosfericaNivelEstacao": 1013.25,
  ...
}
```
