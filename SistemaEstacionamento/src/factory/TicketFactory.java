package factory;

import java.time.LocalDateTime;
import model.Ticket;
import model.Vaga;
import model.Veiculo;

public class TicketFactory {
    public static Ticket criarTicket(int id, Veiculo veiculo, Vaga vaga, LocalDateTime dataHoraEntrada, LocalDateTime dataHoraSaida, double valor)throws Exception {
        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo não pode ser nulo");
        }

        if (vaga == null) {
            throw new IllegalArgumentException("Vaga não pode ser nula");
        }

        if (dataHoraEntrada == null) {
            throw new IllegalArgumentException("Data e hora de entrada não podem ser nulas");
        }   

        if (dataHoraSaida == null) {
            throw new IllegalArgumentException("Data e hora de saída não podem ser nulas");
        }

        if (valor <= 0) {
            throw new IllegalArgumentException("Valor do ticket deve ser maior que zero.");
        }

        if (id <= 0) {
            throw new IllegalArgumentException("ID do ticket deve ser maior que zero.");
        }

        if (dataHoraSaida.isBefore(dataHoraEntrada)) {
            throw new IllegalArgumentException("Data e hora de saída não podem ser anteriores à data e hora de entrada.");
        }


        try {
            return new Ticket(id, veiculo, vaga, dataHoraEntrada, dataHoraSaida, valor);
        } catch (Exception e) {
            System.err.println("[Factory] Erro ao criar ticket: " + e.getMessage());
            throw new Exception("Erro ao criar ticket: " + e.getMessage(), e);
        }

    }
}
