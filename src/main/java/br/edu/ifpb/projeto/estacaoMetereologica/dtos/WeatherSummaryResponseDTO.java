package br.edu.ifpb.projeto.estacaoMetereologica.dtos;

import br.edu.ifpb.projeto.estacaoMetereologica.domain.Data;

import java.util.List;

public record WeatherSummaryResponseDTO(
        List<Data> data,
        MetricsDTO metrics
) {}