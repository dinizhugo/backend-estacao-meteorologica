package br.edu.ifpb.projeto.estacaoMetereologica.services;

import br.edu.ifpb.projeto.estacaoMetereologica.domain.StationInformation;
import br.edu.ifpb.projeto.estacaoMetereologica.repositories.StationInformationRepository;
import br.edu.ifpb.projeto.estacaoMetereologica.services.exceptions.StationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationInformationService {

    @Autowired
    private StationInformationRepository repository;

    public StationInformation findStationInformationById(String id) {
        return repository.findById(id).orElseThrow(StationNotFoundException::new);
    }

    public List<StationInformation> getAllStationsInformation() {
        return repository.findAll();
    }
}
