package controllers;

import factory.EstacionamentoFactory;
import model.Carro;
import model.Estacionamento;
import model.Moto;
import model.Vaga;

public class EstacionamentoController {

    public Estacionamento estacionamentos;

    public void cadastrarEstacionamento(String nome, int numeroDeVagas, String endereco, String telefone, String email) throws Exception, IllegalStateException {
        if (estacionamentos != null) {
            throw new IllegalStateException("Estacionamento já cadastrado.");
        }

        try {
            estacionamentos = EstacionamentoFactory.criarEstacionamento(nome, numeroDeVagas, endereco, telefone, email);
        } catch (Exception e) {
            System.err.println("[Controller] Erro inesperado ao cadastrar estacionamento: " + e.getMessage());
            throw new Exception("Erro ao cadastrar estacionamento: " + e.getMessage(), e);
        }

    }

    public String alocarCarro(Carro carro) throws Exception {
        Vaga vaga = estacionamentos.getVagas().stream()
                .filter(Vaga::estaDisponivel).findFirst().orElse(null);

        if (vaga != null) {
            vaga.alterarDisponibilidade(false);
            carro.setIdVaga(vaga.getNumero());
            return "Carro alocado com sucesso!";

        } else {
            System.err.println("[Controller] Erro inesperado sem vagas: ");
            throw new Exception("Sem vagas disponiveis");
        }
    }

    public String alocarMoto(Moto moto) throws Exception {
        Vaga vaga = estacionamentos.getVagas().stream()
                .filter(Vaga::estaDisponivelParaMoto)
                .findFirst()
                .orElse(null);
        if (vaga != null) {
            vaga.alterarDisponibilidadeMoto(false);
        }
        if (vaga == null) {
            vaga = estacionamentos.getVagas().stream()
                    .filter(Vaga::estaDisponivel).findFirst().orElse(null);
            vaga.alterarDisponibilidadeMoto(true);
            vaga.alterarDisponibilidade(false);
        }
        if (vaga != null) {
            return "Moto alocada com sucesso!";
        } else {
            System.err.println("[Controller] Erro inesperado sem vagas disponiveis: ");
            throw new Exception("Sem vagas disponíveis.");
        }
    }
}
