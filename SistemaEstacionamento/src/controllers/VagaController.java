package controllers;

import dal.VagaDAO;
import enums.StatusVaga;
import factory.VagaFactory;
import java.io.IOException;
import java.util.List;
import model.Vaga;

public class VagaController {

    private List<Vaga> vagas;

    public VagaController(List<Vaga> vagas) {
        this.vagas = vagas;
    }

    public List<Vaga> criarVagas(int numeroDeVagas) throws IOException, ClassNotFoundException {

        try {
            for (int i = 1; i <= numeroDeVagas; i++) {
                vagas.add(VagaFactory.criarVaga(i, StatusVaga.LIVRE));
            }
        } catch (Exception e) {
            System.err.println("[Controller] Erro ao criar vagas: " + e.getMessage());
            throw new IOException("Falha ao criar vagas.", e);
        }
        return vagas;
    }

    public List<String> listarVagas() throws Exception {
        try {
            return vagas.stream().map(Vaga::toString).toList();
        } catch (Exception e) {
            System.err.println("[Controller] Erro ao listar vagas: " + e.getMessage());
            throw new Exception("Erro ao listar vagas.", e);
        }
    }

    public List<Vaga> getVagas() throws Exception {
        try {
            return vagas;
        } catch (Exception e) {
            System.err.println("[Controller] Erro ao obter vagas: " + e.getMessage());
            throw new Exception("Erro ao obter vagas.", e);
        }

    }

    public Vaga buscarVagaPorNumero(int numeroVaga) throws Exception {
        try {
            for (Vaga vaga : vagas) {
                if (vaga.getNumero() == numeroVaga) {
                    return vaga;
                }
            }
            return null;
        } catch (Exception e) {
            System.err.println("[Controller] Erro ao buscar vaga por número: " + numeroVaga + " - " + e.getMessage());
            throw new Exception("Erro ao buscar vaga por número: " + numeroVaga, e);
        }

    }

    public void removerVaga(int numeroVaga) throws Exception {
        try {
            Vaga vaga = buscarVagaPorNumero(numeroVaga);
            if (vaga != null) {
                vagas.remove(vaga);
            }
        } catch (Exception e) {
            System.err.println("[Controller] Erro ao remover vaga: " + numeroVaga + " - " + e.getMessage());
            throw new Exception("Erro ao remover vaga: " + numeroVaga, e);
        }
    }

    public void setVagas(List<Vaga> vagas) throws Exception {
        try {
            this.vagas = vagas;
        } catch (Exception e) {
            throw new Exception("Erro ao definir vagas: " + e.getMessage(), e);
        }
    }

    public void salvar() throws Exception {
        try {
            VagaDAO.salvar(vagas);
        } catch (Exception e) {
            throw new Exception("Erro ao salvar vagas: " + e.getMessage(), e);
        }

    }

    public List<Vaga> carregar() throws Exception {
        try {
            return VagaDAO.carregar();
        } catch (Exception e) {
            throw new Exception("Erro ao carregar vagas.", e);
        }
    }
}
