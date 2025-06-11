package factory;
import model.Vaga;
import model.Veiculo;

class VagaOcupadaFactory {

    public static VagaOcupada criarVagaOcupada(Vaga vaga, Veiculo veiculo) {
        if (vaga == null || veiculo == null) {
            throw new IllegalArgumentException("Vaga e Veículo não podem ser nulos.");
        }
        return new VagaOcupada(vaga, veiculo);
    }
}