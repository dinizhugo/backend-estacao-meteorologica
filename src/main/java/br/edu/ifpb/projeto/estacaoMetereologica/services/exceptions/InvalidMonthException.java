package br.edu.ifpb.projeto.estacaoMetereologica.services.exceptions;

public class InvalidMonthException extends RuntimeException {
    public InvalidMonthException() {
        super("O més não pode ser menor do que 1 e nem maior do que 12.");
    }
}
