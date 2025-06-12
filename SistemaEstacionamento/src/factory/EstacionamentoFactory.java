package factory;

import controllers.VagaController;
import java.util.ArrayList;
import java.util.List;
import model.Estacionamento;
import model.Vaga;

public abstract class EstacionamentoFactory {

    public static Estacionamento criarEstacionamento(String nome, int numeroDeVagas, String endereco, String telefone, String email) throws Exception {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome do estacionamento não pode ser nulo ou vazio.");
        }

        if (numeroDeVagas <= 0) {
            throw new IllegalArgumentException("Número de vagas deve ser maior que zero.");
        }

        if (endereco == null || endereco.isEmpty()) {
            throw new IllegalArgumentException("Endereço do estacionamento não pode ser nulo ou vazio.");
        }

        if (telefone == null || telefone.isEmpty()) {
            throw new IllegalArgumentException("Telefone do estacionamento não pode ser nulo ou vazio.");
        }

        if (email == null || email.isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("Email do estacionamento não pode ser nulo ou vazio.");
        }

        try{
            List<Vaga> vagas = fazerVagas(numeroDeVagas);
            Estacionamento estacionamento = new Estacionamento(nome, endereco, numeroDeVagas, telefone, email, vagas);
            return estacionamento;
        } catch (Exception e) {
            throw new Exception("Erro ao criar estacionamento: " + e.getMessage(), e);
        }
    }

    public static List<Vaga> fazerVagas(int numeroDeVagas) throws Exception,  IllegalArgumentException {
        if (numeroDeVagas <= 0) {
            throw new IllegalArgumentException("Número de vagas deve ser maior que zero.");
        }

        try{
            List<Vaga> vagas = new ArrayList<>();
            VagaController vagaController = new VagaController(vagas);
            List<Vaga> vagasProntas = vagaController.criarVagas(numeroDeVagas);
            return vagasProntas;
        } catch (Exception e) {
            throw new Exception("Erro ao criar vagas: " + e.getMessage(), e);
        }
    }
}
