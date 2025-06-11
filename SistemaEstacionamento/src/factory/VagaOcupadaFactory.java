package factory;
import model.Vaga;
import model.VagaOcupada;
import model.Veiculo;

public class VagaOcupadaFactory {

    public static VagaOcupada criarVagaOcupada(Vaga vaga, Veiculo veiculo)throws Exception, IllegalStateException {
        if (vaga == null || veiculo == null) {
            throw new IllegalArgumentException("Vaga e Veículo não podem ser nulos.");
        }
        return new VagaOcupada(vaga, veiculo);
    }
}