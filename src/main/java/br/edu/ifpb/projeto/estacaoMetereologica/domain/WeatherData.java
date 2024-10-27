package br.edu.ifpb.projeto.estacaoMetereologica.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherData {

    @Id
    private String id;

    @Field("CODIGO")
    private String codigo;

    @Field("DATA")
    private String data;

    @Field("MEDICOES")
    private List<WeatherMetrics> metrics;

    private WeatherAverages weatherAverages;

}
