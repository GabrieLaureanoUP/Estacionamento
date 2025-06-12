package controllers;

import dal.VagaOcupadaDAO;
import factory.VagaOcupadaFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Vaga;
import model.VagaOcupada;
import model.Veiculo;

public class VagaOcupadaController {

    private List<VagaOcupada> vagasOcupadas;

    public VagaOcupadaController() {
        vagasOcupadas = new ArrayList<>();
    }

    public String adicionarVagaOcupada(Vaga vaga, Veiculo veiculo) throws Exception, IllegalStateException {
        try {
            vagasOcupadas.add(VagaOcupadaFactory.criarVagaOcupada(vaga, veiculo));
            return "Vaga ocupada adicionada com sucesso!";
        } catch (Exception e) {
            throw new Exception("Erro ao adicionar vaga ocupada: " + e.getMessage(), e);
        }
    }

    public List<VagaOcupada> listarVagasOcupadas() {
        return vagasOcupadas;
    }

    public VagaOcupada buscarVagaOcupadaPorNumero(int numeroVaga) {
        for (VagaOcupada vagaOcupada : vagasOcupadas) {
            if (vagaOcupada.getVaga().getNumero() == numeroVaga) {
                return vagaOcupada;
            }
        }
        return null;
    }

    public boolean removerVagaOcupada(int numeroVaga) {
        VagaOcupada vagaOcupada = buscarVagaOcupadaPorNumero(numeroVaga);
        if (vagaOcupada != null) {
            vagasOcupadas.remove(vagaOcupada);
            return true;
        }
        return false;
    }

    public void salvar() throws IOException, ClassNotFoundException {
        try {
            VagaOcupadaDAO.salvar(vagasOcupadas);
        } catch (IOException e) {
            throw new IOException("Erro ao salvar vagas ocupadas.", e);
        }
    }

    public List<VagaOcupada> carregar() throws Exception {
        try {
            return VagaOcupadaDAO.carregar();
        } catch (Exception e) {
            throw new Exception("Erro ao carregar vagas ocupadas.", e);
        }
    }
}
