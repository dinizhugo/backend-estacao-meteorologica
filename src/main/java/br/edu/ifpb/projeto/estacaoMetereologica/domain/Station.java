package br.edu.ifpb.projeto.estacaoMetereologica.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Station implements Serializable {

    @EqualsAndHashCode.Include
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

    @Field("DADOS")
    private Set<Data> dados;
}
