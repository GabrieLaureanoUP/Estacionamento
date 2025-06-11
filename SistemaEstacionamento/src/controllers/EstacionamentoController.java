package controllers;

import factory.EstacionamentoFactory;
import java.util.List;
import model.Carro;
import model.Estacionamento;
import model.Moto;
import model.Vaga;

public class EstacionamentoController {

    public Estacionamento estacionamentos;
    private VagaOcupadaController vagaOcupadaController;

    public EstacionamentoController() {
        this.vagaOcupadaController = new VagaOcupadaController();
    }

    public EstacionamentoController(VagaOcupadaController vagaOcupadaController) {
        this.vagaOcupadaController = vagaOcupadaController;
    }

    public void cadastrarEstacionamento(String nome, int numeroDeVagas, String endereco, String telefone, String email) throws Exception, IllegalStateException {
        if (estacionamentos != null) {
            throw new IllegalStateException("Estacionamento já cadastrado.");
        }

        try {
            estacionamentos = EstacionamentoFactory.criarEstacionamento(nome, numeroDeVagas, endereco, telefone, email);
        } catch (Exception e) {
            throw new Exception("Erro ao cadastrar estacionamento: " + e.getMessage(), e);
        }

    }

    public String alocarCarro(Carro carro) throws Exception {
        Vaga vaga = estacionamentos.getVagas().stream()
                .filter(Vaga::estaDisponivel).findFirst().orElse(null);
        try {
            if (vaga != null) {
                vaga.alterarDisponibilidade(false);
                carro.setIdVaga(vaga.getNumero());
                vagaOcupadaController.adicionarVagaOcupada(vaga, carro);
                return "Carro alocado com sucesso!";
            }
            return "Não há vagas disponíveis para alocar o carro.";
        } catch (Exception e) {
            throw new Exception("Erro ao alocar carro: " + e.getMessage(), e);
        }
    }

    public String alocarMoto(Moto moto) throws Exception {
        try{
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
                vagaOcupadaController.adicionarVagaOcupada(vaga, moto);
                return "Moto alocada com sucesso!";
            }
            return "Não há vagas disponíveis para alocar a moto.";
        } catch (Exception e) {
            throw new Exception("Erro ao alocar moto: " + e.getMessage(), e);
        }
    }

    public List<String> listarVagas() throws Exception {
        try {
            return estacionamentos.getVagas().stream().map(Vaga::toString).toList();
        } catch (Exception e) {
            throw new Exception("Erro ao listar vagas.", e);
        }
    }
}
