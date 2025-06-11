package factory;

import java.time.LocalDateTime;
import model.Carro;
import model.Moto;
import model.Veiculo;

public abstract class VeiculoFactory {

    public static Veiculo criarCarro(String placa, String modelo, String cor, LocalDateTime dataHoraEntrada) throws IllegalArgumentException {
        String placaSub;
        String placaTraco;
        int placaNumero;

        try {
            placaSub = placa.substring(0, 2);
            placaTraco = placa.substring(4);
            placaNumero = Integer.parseInt(placa.substring(4, 8));
        } catch (Exception e) {
            throw new IllegalArgumentException("Placa deve conter apenas letras e números válidos.");
        }

        if (modelo == null || modelo.isEmpty()) {
            throw new IllegalArgumentException("Modelo não pode ser nulo ou vazio.");
        }

        if (cor == null || cor.isEmpty()) {
            throw new IllegalArgumentException("Cor não pode ser nula ou vazia.");
        }

        if (dataHoraEntrada == null) {
            throw new IllegalArgumentException("Data e hora de entrada não podem ser nulas.");
        }
        try {
            return new Carro(placa, modelo, cor, dataHoraEntrada);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao criar carro: " + e.getMessage(), e);
        }
    }

    public static Moto criarMoto(String placa, String modelo, String cor, LocalDateTime dataHoraEntrada) throws Exception {
        // Verificar se os parâmetros básicos são válidos
        if (placa == null || placa.isEmpty()) {
            throw new IllegalArgumentException("Placa não pode ser nula ou vazia.");
        }

        if (placa.length() != 8) {
            throw new IllegalArgumentException("Placa deve ter exatamente 8 caracteres (incluindo o hífen).");
        }

        if (placa.charAt(3) != '-') {
            throw new IllegalArgumentException("Placa deve seguir o formato AAA-0000.");
        }

        if (placa == null || placa.isEmpty()) {
            throw new IllegalArgumentException("Placa não pode ser nula ou vazia.");
        }

        if (modelo == null || modelo.isEmpty()) {
            throw new IllegalArgumentException("Modelo não pode ser nulo ou vazio.");
        }

        if (cor == null || cor.isEmpty()) {
            throw new IllegalArgumentException("Cor não pode ser nula ou vazia.");
        }
        if (dataHoraEntrada == null) {
            throw new IllegalArgumentException("Data e hora de entrada não podem ser nulas.");
        }

        if (placa.length() != 8) {
            throw new IllegalArgumentException("Placa deve ter exatamente 8 caracteres (incluindo o hífen).");
        }

        try {
            return new Moto(placa, modelo, cor, dataHoraEntrada);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao criar moto: " + e.getMessage(), e);
        }
    }
}
