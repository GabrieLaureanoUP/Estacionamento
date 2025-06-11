package controllers;

import model.Vaga;
import model.VagaOcupada;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import factory.VagaOcupadaFactory;


public class VagaOcupadaController {

    private static List<VagaOcupada> vagasOcupadas;

    public static VagaOcupadaController() {
        this.vagasOcupadas = new ArrayList<>();
    }

    public static String adicionarVagaOcupada(Vaga vaga, Veiculo veiculo) throws IOException {
        return VagaOcupadaFactory.criarVagaOcupada(vaga, veiculo);
    }

    public static List<VagaOcupada> listarVagasOcupadas() {
        return vagasOcupadas;
    }

    public static VagaOcupada buscarVagaOcupadaPorNumero(int numeroVaga) {
        for (VagaOcupada vagaOcupada : vagasOcupadas) {
            if (vagaOcupada.getVaga().getNumero() == numeroVaga) {
                return vagaOcupada;
            }
        }
        return null;
    }
}
