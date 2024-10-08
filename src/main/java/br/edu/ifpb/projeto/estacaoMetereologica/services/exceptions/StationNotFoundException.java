package br.edu.ifpb.projeto.estacaoMetereologica.services.exceptions;

public class StationNotFoundException extends RuntimeException {
    public StationNotFoundException() {
        super("Não foi possível encontrar uma estação nesse ano ou nesse código.");
    }
}
