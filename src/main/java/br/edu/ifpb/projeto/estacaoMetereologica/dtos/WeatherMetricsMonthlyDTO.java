package br.edu.ifpb.projeto.estacaoMetereologica.dtos;

import br.edu.ifpb.projeto.estacaoMetereologica.domain.WeatherAverages;

import java.util.HashMap;

public record WeatherMetricsMonthlyDTO(
        HashMap<String, WeatherAverages> dailyAverage,
        WeatherAverages monthlyAverage
) {
}
