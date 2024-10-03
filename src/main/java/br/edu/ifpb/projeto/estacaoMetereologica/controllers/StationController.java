package br.edu.ifpb.projeto.estacaoMetereologica.controllers;

import br.edu.ifpb.projeto.estacaoMetereologica.domain.Data;
import br.edu.ifpb.projeto.estacaoMetereologica.domain.Station;
import br.edu.ifpb.projeto.estacaoMetereologica.dtos.WeatherSummaryResponseDTO;
import br.edu.ifpb.projeto.estacaoMetereologica.dtos.StationResponseDTO;
import br.edu.ifpb.projeto.estacaoMetereologica.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/estacoes")
public class StationController {

    @Autowired
    private StationService service;

    @GetMapping("/info/{year}")
    public ResponseEntity<List<StationResponseDTO>> getStationsInfo(@PathVariable String year) {
        return ResponseEntity.ok(service.getStationsInformation(year));
    }

    @GetMapping("/info/{year}/{code}")
    public ResponseEntity<StationResponseDTO> getStationsInfoById(@PathVariable String year, @PathVariable String code) {
        return ResponseEntity.ok(service.getStationsInformationById(code, year));
    }

    @GetMapping("/year/{id}")
    public ResponseEntity<List<Station>> getStationsByYear(@PathVariable String id) {
        return ResponseEntity.ok(service.getAllStationsData(id));
    }

    @GetMapping("/data/{year}/{code}")
    public ResponseEntity<Page<Data>> getStationDataById(@PathVariable String year,
                                                     @PathVariable String code,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "24") int size) {
        return ResponseEntity.ok(service.getPaginatedDataForStationAndYear(code, year, page, size));
    }

    @GetMapping("/data/{code}")
    public ResponseEntity<WeatherSummaryResponseDTO> getStatioDataByDate(@PathVariable String code, @RequestParam LocalDate date) {
        return ResponseEntity.ok(service.getWeatherDataByDate(code, date));
    }
}
