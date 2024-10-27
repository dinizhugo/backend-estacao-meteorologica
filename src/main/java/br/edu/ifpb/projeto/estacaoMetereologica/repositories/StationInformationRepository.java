package br.edu.ifpb.projeto.estacaoMetereologica.repositories;

import br.edu.ifpb.projeto.estacaoMetereologica.domain.StationInformation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationInformationRepository extends MongoRepository<StationInformation, String> {
}
