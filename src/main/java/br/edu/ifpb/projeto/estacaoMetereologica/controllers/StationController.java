package br.edu.ifpb.projeto.estacaoMetereologica.controllers;

import br.edu.ifpb.projeto.estacaoMetereologica.domain.StationInformation;
import br.edu.ifpb.projeto.estacaoMetereologica.dtos.WeatherMetricsDTO;
import br.edu.ifpb.projeto.estacaoMetereologica.dtos.WeatherMetricsMonthlyDTO;
import br.edu.ifpb.projeto.estacaoMetereologica.services.StationInformationService;
import br.edu.ifpb.projeto.estacaoMetereologica.services.WeatherStationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/estacoes")
public class StationController {

    @Autowired
    private WeatherStationDataService weatherStationDataService;

    @Autowired
    private StationInformationService stationInformationService;

    @GetMapping("/info")
    public ResponseEntity<List<StationInformation>> getStationsInfo() {
        return ResponseEntity.ok(stationInformationService.getAllStationsInformation());
    }

    @GetMapping("/info/{code}")
    public ResponseEntity<StationInformation> getStationsInfoById(@PathVariable String code) {
        return ResponseEntity.ok(stationInformationService.findStationInformationById(code));
    }

    @GetMapping("/data/{code}")
    public ResponseEntity<WeatherMetricsDTO> getStatioDataByDate(@PathVariable String code, @RequestParam LocalDate date) {
        return ResponseEntity.ok(weatherStationDataService.getWeatherDataByDate(code, date));
    }

    @GetMapping("/data/{code}/{year}/{month}")
    public ResponseEntity<WeatherMetricsMonthlyDTO> getDataByMonth(
                                                         @PathVariable String year,
                                                         @PathVariable String code,
                                                         @PathVariable int month) {
        return ResponseEntity.ok(weatherStationDataService.getPaginatedDataByMonth(code, year, month));
    }
}
