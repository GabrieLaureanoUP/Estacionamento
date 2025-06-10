package factory;

import model.Carro;
import model.Moto;
import model.Veiculo;

import java.time.LocalDateTime;

public abstract class VeiculoFactory {

    public static Veiculo criarCarro(String placa, String modelo, String cor, LocalDateTime dataHoraEntrada) {
        return new Carro(placa, modelo, cor, dataHoraEntrada);
    }

    public static Moto criarMoto(String placa, String modelo, String cor, LocalDateTime dataHoraEntrada) {
        return new Moto(placa, modelo, cor, dataHoraEntrada);
    }
}
