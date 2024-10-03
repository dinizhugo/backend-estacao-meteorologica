package br.edu.ifpb.projeto.estacaoMetereologica.repositories;

import br.edu.ifpb.projeto.estacaoMetereologica.domain.Data;
import br.edu.ifpb.projeto.estacaoMetereologica.domain.Station;
import br.edu.ifpb.projeto.estacaoMetereologica.dtos.MetricsDTO;
import br.edu.ifpb.projeto.estacaoMetereologica.dtos.WeatherSummaryResponseDTO;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class StationRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public Optional<List<Station>> findByYear(String year) {
        return Optional.of(mongoTemplate.findAll(Station.class, year));
    }

    public Optional<Station> findStationByIdAndYear(String code, String year) {
        return Optional.ofNullable(mongoTemplate.findById(code, Station.class, year));
    }

    public Page<Data> getPaginatedData(String code, String year, int page, int size) {
        Station station = getStation(code, year);

        if (station != null && station.getDados() != null) {
            Pageable pageable = PageRequest.of(page, size);
            List<Data> paginatedData = station.getDados().stream().
                    skip(pageable.getOffset()).
                    limit(pageable.getPageSize()).
                    toList();

            return new PageImpl<>(paginatedData, pageable, station.getDados().size());
        }

        return Page.empty();
    }

    public WeatherSummaryResponseDTO getDataByDate(String code, String year, String date) {
        List<AggregationOperation> dataOperations = getDataOperations(code, date);
        List<AggregationOperation> metricsOperation = getMetricsOperation(code, date);

        Aggregation dataAggregation = Aggregation.newAggregation(dataOperations);
        Aggregation metricsAggregation = Aggregation.newAggregation(metricsOperation);

        List<Document> data = mongoTemplate.aggregate(dataAggregation, year, Document.class).getMappedResults();
        Document metrics = mongoTemplate.aggregate(metricsAggregation, year, Document.class).getUniqueMappedResult();

        return new WeatherSummaryResponseDTO(getDataList(data), getMetricsList(metrics));
    }

    private List<AggregationOperation> getMetricsOperation(String code, String date) {
        return Arrays.asList(
          Aggregation.unwind("DADOS"),
          Aggregation.match(Criteria.where("_id").is(code).and("DADOS.DATA").is(date)),
          Aggregation.group()
                  .avg("DADOS.PRECIPITACAO_TOTAL").as("mediaPrecipitacaoTotal")
                  .avg("DADOS.PRESSAO_ATMOSFERICA_NIVEL_ESTACAO").as("mediaPressaoAtmosfericaNivelEstacao")
                  .avg("DADOS.PRESSAO_ATMOSFERICA_MAX").as("mediaPressaoAtmosfericaMax")
                  .avg("DADOS.PRESSAO_ATMOSFERICA_MIN").as("mediaPressaoAtmosfericaMin")
                  .avg("DADOS.TEMP_BULBO_SECO").as("mediaTempBulboSeco")
                  .avg("DADOS.TEMP_PONTO_ORVALHO").as("mediaTempPontoOrvalho")
                  .avg("DADOS.TEMP_MAX").as("mediaTempMax")
                  .avg("DADOS.TEMP_MIN").as("mediaTempMin")
                  .avg("DADOS.TEMP_ORVALHO_MAX").as("mediaTempOrvalhoMax")
                  .avg("DADOS.TEMP_ORVALHO_MIN").as("mediaTempOrvalhoMin")
                  .avg("DADOS.UMIDADE_RELATIVA_MAX").as("mediaUmidadeRelativaMax")
                  .avg("DADOS.UMIDADE_RELATIVA_MIN").as("mediaUmidadeRelativaMin")
                  .avg("DADOS.UMIDADE_RELATIVA").as("mediaUmidadeRelativa")
                  .avg("DADOS.VENTO_DIRECAO").as("mediaVentoDirecao")
                  .avg("DADOS.VENTO_RAJADA_MAX").as("mediaVentoRajadaMax")
                  .avg("DADOS.VENTO_VELOCIDADE").as("mediaVentoVelocidade")
        );
    }

    private static List<AggregationOperation> getDataOperations(String code, String date) {
        return Arrays.asList(
                Aggregation.unwind("DADOS"),
                Aggregation.match(Criteria.where("_id").is(code).and("DADOS.DATA").is(date)), // Filtra pelo ID e pela DATA
                Aggregation.project()
                        .andExclude("_id") // Exclui o campo _id
                        .and("DADOS.DATA").as("data") // Mapeia o campo DATA
                        .and("DADOS.HORA").as("hora") // Mapeia o campo HORA
                        .and("DADOS.PRECIPITACAO_TOTAL").as("precipitacaoTotal") // Mapeia PRECIPITACAO_TOTAL
                        .and("DADOS.PRESSAO_ATMOSFERICA_NIVEL_ESTACAO").as("pressaoAtmosfericaNivelEstacao") // Mapeia PRESSAO_ATMOSFERICA_NIVEL_ESTACAO
                        .and("DADOS.PRESSAO_ATMOSFERICA_MAX").as("pressaoAtmosfericaMax") // Mapeia PRESSAO_ATMOSFERICA_MAX
                        .and("DADOS.PRESSAO_ATMOSFERICA_MIN").as("pressaoAtmosfericaMin") // Mapeia PRESSAO_ATMOSFERICA_MIN
                        .and("DADOS.RADIACAO_GLOBAL").as("radiacaoGlobal") // Mapeia RADIACAO_GLOBAL
                        .and("DADOS.TEMP_BULBO_SECO").as("tempBulboSeco") // Mapeia TEMP_BULBO_SECO
                        .and("DADOS.TEMP_PONTO_ORVALHO").as("tempPontoOrvalho") // Mapeia TEMP_PONTO_ORVALHO
                        .and("DADOS.TEMP_MAX").as("tempMax") // Mapeia TEMP_MAX
                        .and("DADOS.TEMP_MIN").as("tempMin") // Mapeia TEMP_MIN
                        .and("DADOS.TEMP_ORVALHO_MAX").as("tempOrvalhoMax") // Mapeia TEMP_ORVALHO_MAX
                        .and("DADOS.TEMP_ORVALHO_MIN").as("tempOrvalhoMin") // Mapeia TEMP_ORVALHO_MIN
                        .and("DADOS.UMIDADE_RELATIVA_MAX").as("umidadeRelativaMax") // Mapeia UMIDADE_RELATIVA_MAX
                        .and("DADOS.UMIDADE_RELATIVA_MIN").as("umidadeRelativaMin") // Mapeia UMIDADE_RELATIVA_MIN
                        .and("DADOS.UMIDADE_RELATIVA").as("umidadeRelativa") // Mapeia UMIDADE_RELATIVA
                        .and("DADOS.VENTO_DIRECAO").as("ventoDirecao") // Mapeia VENTO_DIRECAO
                        .and("DADOS.VENTO_RAJADA_MAX").as("ventoRajadaMax") // Mapeia VENTO_RAJADA_MAX
                        .and("DADOS.VENTO_VELOCIDADE").as("ventoVelocidade") // Mapeia VENTO_VELOCIDADE
        );
    }

    private static List<Data> getDataList(List<Document> result) {
        return result.stream().map(doc -> new Data(
                doc.getString("data"),
                doc.getString("hora"),
                doc.getDouble("precipitacaoTotal"),
                doc.getDouble("pressaoAtmosfericaNivelEstacao"),
                doc.getDouble("pressaoAtmosfericaMax"),
                doc.getDouble("pressaoAtmosfericaMin"),
                doc.getDouble("radiacaoGlobal"),
                doc.getDouble("tempBulboSeco"),
                doc.getDouble("tempPontoOrvalho"),
                doc.getDouble("tempMax"),
                doc.getDouble("tempMin"),
                doc.getDouble("tempOrvalhoMax"),
                doc.getDouble("tempOrvalhoMin"),
                doc.getDouble("umidadeRelativaMax"),
                doc.getDouble("umidadeRelativaMin"),
                doc.getDouble("umidadeRelativa"),
                doc.getDouble("ventoDirecao"),
                doc.getDouble("ventoRajadaMax"),
                doc.getDouble("ventoVelocidade")
        )).toList();
    }

    private static MetricsDTO getMetricsList(Document doc) {
        return doc != null ? new MetricsDTO(
                doc.getDouble("mediaPrecipitacaoTotal"),
                doc.getDouble("mediaPressaoAtmosfericaNivelEstacao"),
                doc.getDouble("mediaPressaoAtmosfericaMax"),
                doc.getDouble("mediaPressaoAtmosfericaMin"),
                doc.getDouble("mediaTempBulboSeco"),
                doc.getDouble("mediaTempPontoOrvalho"),
                doc.getDouble("mediaTempMax"),
                doc.getDouble("mediaTempMin"),
                doc.getDouble("mediaTempOrvalhoMax"),
                doc.getDouble("mediaTempOrvalhoMin"),
                doc.getDouble("mediaUmidadeRelativaMax"),
                doc.getDouble("mediaUmidadeRelativaMin"),
                doc.getDouble("mediaUmidadeRelativa"),
                doc.getDouble("mediaVentoDirecao"),
                doc.getDouble("mediaVentoRajadaMax"),
                doc.getDouble("mediaVentoVelocidade")
        ) : new MetricsDTO(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    private Station getStation(String code, String year) {
        return mongoTemplate.findOne(
                Query.query(Criteria.where("_id").is(code)),
                Station.class,
                year
        );
    }

}
