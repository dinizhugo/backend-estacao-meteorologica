package br.edu.ifpb.projeto.estacaoMetereologica.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Data {

    @EqualsAndHashCode.Include
    @Field("DATA")
    private String data;

    @EqualsAndHashCode.Include
    @Field("HORA")
    private String hora;

    @Field("PRECIPITACAO_TOTAL")
    private Double precipitacaoTotal;

    @Field("PRESSAO_ATMOSFERICA_NIVEL_ESTACAO")
    private Double pressaoAtmosfericaNivelEstacao;

    @Field("PRESSAO_ATMOSFERICA_MAX")
    private Double pressaoAtmosfericaMax;

    @Field("PRESSAO_ATMOSFERICA_MIN")
    private Double pressaoAtmosfericaMin;

    @Field("RADIACAO_GLOBAL")
    private Double radiacaoGlobal;

    @Field("TEMP_BULBO_SECO")
    private Double tempBulboSeco;

    @Field("TEMP_PONTO_ORVALHO")
    private Double tempPontoOrvalho;

    @Field("TEMP_MAX")
    private Double tempMax;

    @Field("TEMP_MIN")
    private Double tempMin;

    @Field("TEMP_ORVALHO_MAX")
    private Double tempOrvalhoMax;

    @Field("TEMP_ORVALHO_MIN")
    private Double tempOrvalhoMin;

    @Field("UMIDADE_RELATIVA_MAX")
    private Double umidadeRelativaMax;

    @Field("UMIDADE_RELATIVA_MIN")
    private Double umidadeRelativaMin;

    @Field("UMIDADE_RELATIVA")
    private Double umidadeRelativa;

    @Field("VENTO_DIRECAO")
    private Double ventoDirecao;

    @Field("VENTO_RAJADA_MAX")
    private Double ventoRajadaMax;

    @Field("VENTO_VELOCIDADE")
    private Double ventoVelocidade;

}
