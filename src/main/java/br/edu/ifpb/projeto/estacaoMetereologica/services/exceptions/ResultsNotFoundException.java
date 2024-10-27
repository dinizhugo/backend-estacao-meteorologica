package br.edu.ifpb.projeto.estacaoMetereologica.services.exceptions;

public class ResultsNotFoundException extends RuntimeException {
    public ResultsNotFoundException() {
        super("Nenhum resultado encontrado.");
    }
}
