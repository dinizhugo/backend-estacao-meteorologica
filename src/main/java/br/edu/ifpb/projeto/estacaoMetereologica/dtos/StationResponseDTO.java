package br.edu.ifpb.projeto.estacaoMetereologica.dtos;

public record StationResponseDTO(
        String estacao,
        String cidade,
        String estado,
        String codigo,
        String latitude,
        String longitude,
        String dataFundacao) {
}
