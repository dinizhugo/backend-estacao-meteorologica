package br.edu.ifpb.projeto.estacaoMetereologica.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "informacoes_estacoes")
public class StationInformation implements Serializable {

    @Id
    private String id;

    @Field("UF")
    private String uf;

    @Field("ESTACAO")
    private String estacao;

    @Field("CODIGO")
    private String codigo;

    @Field("LATITUDE")
    private String latitude;

    @Field("LONGITUDE")
    private String longitude;

    @Field("DATA_FUNDAÇÃO")
    private String dataFundacao;
}
