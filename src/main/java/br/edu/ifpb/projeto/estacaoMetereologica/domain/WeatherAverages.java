package br.edu.ifpb.projeto.estacaoMetereologica.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherAverages {

    @Field("MEDIA_PRECIPITACAO_TOTAL")
    private Double mediaPrecipitacaoTotal;

    @Field("MEDIA_PRESSAO_ATMOSFERICA_NIVEL_ESTACAO")
    private Double mediaPressaoAtmosfericaNivelEstacao;

    @Field("MEDIA_PRESSAO_ATMOSFERICA_MAX")
    private Double mediaPressaoAtmosfericaMax;

    @Field("MEDIA_PRESSAO_ATMOSFERICA_MIN")
    private Double mediaPressaoAtmosfericaMin;

    @Field("MEDIA_RADIACAO_GLOBAL")
    private Double mediaRadiacaoGlobal;

    @Field("MEDIA_TEMP_BULBO_SECO")
    private Double mediaTempBulboSeco;

    @Field("MEDIA_TEMP_PONTO_ORVALHO")
    private Double mediaTempPontoOrvalho;

    @Field("MEDIA_TEMP_MAX")
    private Double mediaTempMax;

    @Field("MEDIA_TEMP_MIN")
    private Double mediaTempMin;

    @Field("MEDIA_TEMP_ORVALHO_MAX")
    private Double mediaTempOrvalhoMax;

    @Field("MEDIA_TEMP_ORVALHO_MIN")
    private Double mediaTempOrvalhoMin;

    @Field("MEDIA_UMIDADE_RELATIVA_MAX")
    private Double mediaUmidadeRelativaMax;

    @Field("MEDIA_UMIDADE_RELATIVA_MIN")
    private Double mediaUmidadeRelativaMin;

    @Field("MEDIA_UMIDADE_RELATIVA")
    private Double mediaUmidadeRelativa;

    @Field("MEDIA_VENTO_DIRECAO")
    private Double mediaVentoDirecao;

    @Field("MEDIA_VENTO_RAJADA_MAX")
    private Double mediaVentoRajadaMax;

    @Field("MEDIA_VENTO_VELOCIDADE")
    private Double mediaVentoVelocidade;
}
