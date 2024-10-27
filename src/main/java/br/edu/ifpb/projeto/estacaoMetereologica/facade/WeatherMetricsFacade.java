package br.edu.ifpb.projeto.estacaoMetereologica.facade;

import br.edu.ifpb.projeto.estacaoMetereologica.dtos.WeatherMetricsDTO;
import br.edu.ifpb.projeto.estacaoMetereologica.dtos.WeatherMetricsMonthlyDTO;

public interface WeatherMetricsFacade {
    WeatherMetricsDTO getWeatherMetricsByDate(String code, String year, String date);
    WeatherMetricsMonthlyDTO getWeatherMetricsByMonth(String code, String year, String month);
}
