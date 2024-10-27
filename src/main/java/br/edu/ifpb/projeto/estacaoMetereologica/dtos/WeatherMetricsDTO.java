package br.edu.ifpb.projeto.estacaoMetereologica.dtos;

import br.edu.ifpb.projeto.estacaoMetereologica.domain.WeatherAverages;
import br.edu.ifpb.projeto.estacaoMetereologica.domain.WeatherMetrics;

import java.util.List;

public record WeatherMetricsDTO(
        List<WeatherMetrics> metrics,
        WeatherAverages averages
) { }
