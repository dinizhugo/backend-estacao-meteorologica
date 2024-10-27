package br.edu.ifpb.projeto.estacaoMetereologica.services;

import br.edu.ifpb.projeto.estacaoMetereologica.dtos.WeatherMetricsDTO;
import br.edu.ifpb.projeto.estacaoMetereologica.dtos.WeatherMetricsMonthlyDTO;
import br.edu.ifpb.projeto.estacaoMetereologica.facade.WeatherMetricsFacade;
import br.edu.ifpb.projeto.estacaoMetereologica.services.exceptions.InvalidMonthException;
import br.edu.ifpb.projeto.estacaoMetereologica.services.exceptions.ResultsNotFoundException;
import br.edu.ifpb.projeto.estacaoMetereologica.services.exceptions.StationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WeatherStationDataService {

    @Autowired
    private WeatherMetricsFacade repository;

    public WeatherMetricsDTO getWeatherDataByDate(String code, LocalDate date) {
        String year = String.valueOf(date.getYear());
        WeatherMetricsDTO weatherMetrics = repository.getWeatherMetricsByDate(code, year, date.toString());

        if (weatherMetrics == null) { throw new StationNotFoundException(); }

        return weatherMetrics;
    }

    public WeatherMetricsMonthlyDTO getPaginatedDataByMonth(String code, String year, int month) {
        if (month <= 0 || month >= 13) {throw new InvalidMonthException();}

        WeatherMetricsMonthlyDTO weatherMetricsMonthly = repository.getWeatherMetricsByMonth(code, year, String.format("%02d", month));

        if (weatherMetricsMonthly == null) { throw new ResultsNotFoundException(); }

        return weatherMetricsMonthly;
    }

}
