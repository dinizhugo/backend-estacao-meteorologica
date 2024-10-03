package br.edu.ifpb.projeto.estacaoMetereologica.dtos;

public record MetricsDTO(
        Double mediaPrecipitacaoTotal,
        Double mediaPressaoAtmosfericaNivelEstacao,
        Double mediaPressaoAtmosfericaMax,
        Double mediaPressaoAtmosfericaMin,
        Double mediaTempBulboSeco,
        Double mediaTempPontoOrvalho,
        Double mediaTempMax,
        Double mediaTempMin,
        Double mediaTempOrvalhoMax,
        Double mediaTempOrvalhoMin,
        Double mediaUmidadeRelativaMax,
        Double mediaUmidadeRelativaMin,
        Double mediaUmidadeRelativa,
        Double mediaVentoDirecao,
        Double mediaVentoRajadaMax,
        Double mediaVentoVelocidade
){ }
