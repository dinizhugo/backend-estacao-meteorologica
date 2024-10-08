package br.edu.ifpb.projeto.estacaoMetereologica.services;

import br.edu.ifpb.projeto.estacaoMetereologica.domain.Data;
import br.edu.ifpb.projeto.estacaoMetereologica.domain.Station;
import br.edu.ifpb.projeto.estacaoMetereologica.dtos.WeatherSummaryResponseDTO;
import br.edu.ifpb.projeto.estacaoMetereologica.dtos.StationResponseDTO;
import br.edu.ifpb.projeto.estacaoMetereologica.repositories.StationRepository;
import br.edu.ifpb.projeto.estacaoMetereologica.services.exceptions.StationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    public List<Station> getAllStationsData(String year){
        return stationRepository.findByYear(year).orElseThrow(StationNotFoundException::new);
    }

    public Station getStationById(String code, String year) {
        return stationRepository.findStationByIdAndYear(code, year).orElseThrow(StationNotFoundException::new);
    }

    public List<StationResponseDTO> getStationsInformation(String year) {
        return getAllStationsData(year).stream()
                .map(this::convertToStationResponseDTO)
                .collect(Collectors.toList());
    }

    public StationResponseDTO getStationsInformationById(String code, String year) {
        Station stationInform = getStationById(code, year);
        return convertToStationResponseDTO(stationInform);
    }

    public Page<Data> getPaginatedDataForStationAndYear(String stationCode, String year, int page, int size) {
        getStationById(stationCode, year);

        return stationRepository.getPaginatedData(stationCode, year, page, size);
    }

    public WeatherSummaryResponseDTO getWeatherDataByDate(String code, LocalDate date) {
        String year = String.valueOf(date.getYear());
        getStationById(code, year);

        return stationRepository.getDataByDate(code, year, date.toString());
    }

    private StationResponseDTO convertToStationResponseDTO(Station stationInform) {
        String stationName = String.format("%s(%s)", stationInform.getEstacao(), stationInform.getCodigo());
        return new StationResponseDTO(
                stationName,
                stationInform.getEstacao(),
                stationInform.getUf(),
                stationInform.getCodigo(),
                stationInform.getLatitude(),
                stationInform.getLongitude(),
                stationInform.getDataFundacao()
        );
    }

}
