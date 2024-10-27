package br.edu.ifpb.projeto.estacaoMetereologica.repositories;

import br.edu.ifpb.projeto.estacaoMetereologica.domain.WeatherAverages;
import br.edu.ifpb.projeto.estacaoMetereologica.domain.WeatherMetrics;
import br.edu.ifpb.projeto.estacaoMetereologica.dtos.WeatherMetricsDTO;
import br.edu.ifpb.projeto.estacaoMetereologica.dtos.WeatherMetricsMonthlyDTO;
import br.edu.ifpb.projeto.estacaoMetereologica.facade.WeatherMetricsFacade;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@Repository
public class WeatherMetricsRepository implements WeatherMetricsFacade {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public WeatherMetricsDTO getWeatherMetricsByDate(String code, String year, String date) {
        String id = code + "_" + date;

        MongoDatabase database = mongoTemplate.getDb();
        MongoCollection<Document> collection = database.getCollection(year);

        Document result = collection.find(
                        Filters.eq("_id", id))
                .projection(Projections.exclude("_id", "CODIGO", "DATA"))
                .first();

        return convertDocumentToWeatherMetricsDTO(result);
    }

    @Override
    public WeatherMetricsMonthlyDTO getWeatherMetricsByMonth(String code, String year, String month) {
        String regex = "^" + code + "_" + year + "-" + month;
        MongoDatabase database = mongoTemplate.getDb();
        MongoCollection<Document> collection = database.getCollection(year);

        List<Document> pipeline = Arrays.asList(
                new Document("$match", new Document("_id", new Document("$regex", regex))),
                new Document("$addFields", new Document("day", new Document("$toInt", new Document("$substr", Arrays.asList("$_id", 13, 2))))),
                new Document("$group", new Document("_id", "$day")
                        .append("mediaPrecipitacaoTotal", new Document("$avg", "$MEDIA_PRECIPITACAO_TOTAL"))
                        .append("mediaPressaoAtmosfericaNivelEstacao", new Document("$avg", "$MEDIA_PRESSAO_ATMOSFERICA_NIVEL_ESTACAO"))
                        .append("mediaPressaoAtmosfericaMax", new Document("$avg", "$MEDIA_PRESSAO_ATMOSFERICA_MAX"))
                        .append("mediaPressaoAtmosfericaMin", new Document("$avg", "$MEDIA_PRESSAO_ATMOSFERICA_MIN"))
                        .append("mediaRadiacaoGlobal", new Document("$avg", "$MEDIA_RADIACAO_GLOBAL"))
                        .append("mediaTempBulboSeco", new Document("$avg", "$MEDIA_TEMP_BULBO_SECO"))
                        .append("mediaTempPontoOrvalho", new Document("$avg", "$MEDIA_TEMP_PONTO_ORVALHO"))
                        .append("mediaTempMax", new Document("$avg", "$MEDIA_TEMP_MAX"))
                        .append("mediaTempMin", new Document("$avg", "$MEDIA_TEMP_MIN"))
                        .append("mediaTempOrvalhoMax", new Document("$avg", "$MEDIA_TEMP_ORVALHO_MAX"))
                        .append("mediaTempOrvalhoMin", new Document("$avg", "$MEDIA_TEMP_ORVALHO_MIN"))
                        .append("mediaUmidadeRelativaMax", new Document("$avg", "$MEDIA_UMIDADE_RELATIVA_MAX"))
                        .append("mediaUmidadeRelativaMin", new Document("$avg", "$MEDIA_UMIDADE_RELATIVA_MIN"))
                        .append("mediaUmidadeRelativa", new Document("$avg", "$MEDIA_UMIDADE_RELATIVA"))
                        .append("mediaVentoDirecao", new Document("$avg", "$MEDIA_VENTO_DIRECAO"))
                        .append("mediaVentoRajadaMax", new Document("$avg", "$MEDIA_VENTO_RAJADA_MAX"))
                        .append("mediaVentoVelocidade", new Document("$avg", "$MEDIA_VENTO_VELOCIDADE"))
                ),

                new Document("$group", new Document("_id", null)
                        .append("dailyAverage", new Document("$push", new Document("day", "$_id")
                                .append("MEDIA_PRECIPITACAO_TOTAL", "$mediaPrecipitacaoTotal")
                                .append("MEDIA_PRESSAO_ATMOSFERICA_NIVEL_ESTACAO", "$mediaPressaoAtmosfericaNivelEstacao")
                                .append("MEDIA_PRESSAO_ATMOSFERICA_MAX", "$mediaPressaoAtmosfericaMax")
                                .append("MEDIA_PRESSAO_ATMOSFERICA_MIN", "$mediaPressaoAtmosfericaMin")
                                .append("MEDIA_RADIACAO_GLOBAL", "$mediaRadiacaoGlobal")
                                .append("MEDIA_TEMP_BULBO_SECO", "$mediaTempBulboSeco")
                                .append("MEDIA_TEMP_PONTO_ORVALHO", "$mediaTempPontoOrvalho")
                                .append("MEDIA_TEMP_MAX", "$mediaTempMax")
                                .append("MEDIA_TEMP_MIN", "$mediaTempMin")
                                .append("MEDIA_TEMP_ORVALHO_MAX", "$mediaTempOrvalhoMax")
                                .append("MEDIA_TEMP_ORVALHO_MIN", "$mediaTempOrvalhoMin")
                                .append("MEDIA_UMIDADE_RELATIVA_MAX", "$mediaUmidadeRelativaMax")
                                .append("MEDIA_UMIDADE_RELATIVA_MIN", "$mediaUmidadeRelativaMin")
                                .append("MEDIA_UMIDADE_RELATIVA", "$mediaUmidadeRelativa")
                                .append("MEDIA_VENTO_DIRECAO", "$mediaVentoDirecao")
                                .append("MEDIA_VENTO_RAJADA_MAX", "$mediaVentoRajadaMax")
                                .append("MEDIA_VENTO_VELOCIDADE", "$mediaVentoVelocidade")
                        ))
                ),

                new Document("$project", new Document("dailyAverage", "$dailyAverage")
                        .append("monthlyAverage", new Document("MEDIA_PRECIPITACAO_TOTAL", new Document("$avg", "$dailyAverage.MEDIA_PRECIPITACAO_TOTAL"))
                                .append("MEDIA_PRESSAO_ATMOSFERICA_NIVEL_ESTACAO", new Document("$avg", "$dailyAverage.MEDIA_PRESSAO_ATMOSFERICA_NIVEL_ESTACAO"))
                                .append("MEDIA_PRESSAO_ATMOSFERICA_MAX", new Document("$avg", "$dailyAverage.MEDIA_PRESSAO_ATMOSFERICA_MAX"))
                                .append("MEDIA_PRESSAO_ATMOSFERICA_MIN", new Document("$avg", "$dailyAverage.MEDIA_PRESSAO_ATMOSFERICA_MIN"))
                                .append("MEDIA_RADIACAO_GLOBAL", new Document("$avg", "$dailyAverage.MEDIA_RADIACAO_GLOBAL"))
                                .append("MEDIA_TEMP_BULBO_SECO", new Document("$avg", "$dailyAverage.MEDIA_TEMP_BULBO_SECO"))
                                .append("MEDIA_TEMP_PONTO_ORVALHO", new Document("$avg", "$dailyAverage.MEDIA_TEMP_PONTO_ORVALHO"))
                                .append("MEDIA_TEMP_MAX", new Document("$avg", "$dailyAverage.MEDIA_TEMP_MAX"))
                                .append("MEDIA_TEMP_MIN", new Document("$avg", "$dailyAverage.MEDIA_TEMP_MIN"))
                                .append("MEDIA_TEMP_ORVALHO_MAX", new Document("$avg", "$dailyAverage.MEDIA_TEMP_ORVALHO_MAX"))
                                .append("MEDIA_TEMP_ORVALHO_MIN", new Document("$avg", "$dailyAverage.MEDIA_TEMP_ORVALHO_MIN"))
                                .append("MEDIA_UMIDADE_RELATIVA_MAX", new Document("$avg", "$dailyAverage.MEDIA_UMIDADE_RELATIVA_MAX"))
                                .append("MEDIA_UMIDADE_RELATIVA_MIN", new Document("$avg", "$dailyAverage.MEDIA_UMIDADE_RELATIVA_MIN"))
                                .append("MEDIA_UMIDADE_RELATIVA", new Document("$avg", "$dailyAverage.MEDIA_UMIDADE_RELATIVA"))
                                .append("MEDIA_VENTO_DIRECAO", new Document("$avg", "$dailyAverage.MEDIA_VENTO_DIRECAO"))
                                .append("MEDIA_VENTO_RAJADA_MAX", new Document("$avg", "$dailyAverage.MEDIA_VENTO_RAJADA_MAX"))
                                .append("MEDIA_VENTO_VELOCIDADE", new Document("$avg", "$dailyAverage.MEDIA_VENTO_VELOCIDADE"))
                        )
                )
        );

        AggregateIterable<Document> result = collection.aggregate(pipeline);

        if (result.iterator().hasNext()) {
            Document doc = result.iterator().next();
            List<Document> dailyAverages = doc.getList("dailyAverage", Document.class);
            WeatherAverages monthlyAverages = convertDocumentToWeatherAverages(doc.get("monthlyAverage", Document.class));

            HashMap<String, WeatherAverages> dailyMetricsMap = new HashMap<>();

            dailyAverages.forEach(daily -> {
                String dayKey = "day_" + String.format("%02d", daily.getInteger("day"));
                WeatherAverages weatherAverages = convertDocumentToWeatherAverages(daily);
                dailyMetricsMap.put(dayKey, weatherAverages);
            });

            return new WeatherMetricsMonthlyDTO(dailyMetricsMap, monthlyAverages);
        }

        return null;
    }

    private WeatherMetricsDTO convertDocumentToWeatherMetricsDTO(Document doc) {
        if (doc == null) { return null; }

        List<WeatherMetrics> metrics = doc.getList("MEDICOES", Document.class).stream()
                .map(this::convertDocumentToWeatherMetrics)
                .toList();

        WeatherAverages averages = convertDocumentToWeatherAverages(doc);

        return new WeatherMetricsDTO(metrics, averages);
    }

    private WeatherMetrics convertDocumentToWeatherMetrics(Document doc) {
        return new WeatherMetrics(
                doc.getString("HORA"),
                doc.getDouble("PRECIPITACAO_TOTAL"),
                doc.getDouble("PRESSAO_ATMOSFERICA_NIVEL_ESTACAO"),
                doc.getDouble("PRESSAO_ATMOSFERICA_MAX"),
                doc.getDouble("PRESSAO_ATMOSFERICA_MIN"),
                doc.getDouble("RADIACAO_GLOBAL"),
                doc.getDouble("TEMP_BULBO_SECO"),
                doc.getDouble("TEMP_PONTO_ORVALHO"),
                doc.getDouble("TEMP_MAX"),
                doc.getDouble("TEMP_MIN"),
                doc.getDouble("TEMP_ORVALHO_MAX"),
                doc.getDouble("TEMP_ORVALHO_MIN"),
                doc.getDouble("UMIDADE_RELATIVA_MAX"),
                doc.getDouble("UMIDADE_RELATIVA_MIN"),
                doc.getDouble("UMIDADE_RELATIVA"),
                doc.getDouble("VENTO_DIRECAO"),
                doc.getDouble("VENTO_RAJADA_MAX"),
                doc.getDouble("VENTO_VELOCIDADE")
        );
    }

    private WeatherAverages convertDocumentToWeatherAverages(Document doc) {
        return new WeatherAverages(
                doc.getDouble("MEDIA_PRECIPITACAO_TOTAL"),
                doc.getDouble("MEDIA_PRESSAO_ATMOSFERICA_NIVEL_ESTACAO"),
                doc.getDouble("MEDIA_PRESSAO_ATMOSFERICA_MAX"),
                doc.getDouble("MEDIA_PRESSAO_ATMOSFERICA_MIN"),
                doc.getDouble("MEDIA_RADIACAO_GLOBAL"),
                doc.getDouble("MEDIA_TEMP_BULBO_SECO"),
                doc.getDouble("MEDIA_TEMP_PONTO_ORVALHO"),
                doc.getDouble("MEDIA_TEMP_MAX"),
                doc.getDouble("MEDIA_TEMP_MIN"),
                doc.getDouble("MEDIA_TEMP_ORVALHO_MAX"),
                doc.getDouble("MEDIA_TEMP_ORVALHO_MIN"),
                doc.getDouble("MEDIA_UMIDADE_RELATIVA_MAX"),
                doc.getDouble("MEDIA_UMIDADE_RELATIVA_MIN"),
                doc.getDouble("MEDIA_UMIDADE_RELATIVA"),
                doc.getDouble("MEDIA_VENTO_DIRECAO"),
                doc.getDouble("MEDIA_VENTO_RAJADA_MAX"),
                doc.getDouble("MEDIA_VENTO_VELOCIDADE")
        );
    }

}