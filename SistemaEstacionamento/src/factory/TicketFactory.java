package factory;

import java.time.LocalDateTime;
import model.Ticket;
import model.Vaga;
import model.Veiculo;

public class TicketFactory {

    public static Ticket criarTicket(int id, Veiculo veiculo, Vaga vaga, LocalDateTime dataHoraEntrada, LocalDateTime dataHoraSaida, double valor) {
        return new Ticket(id, veiculo, vaga, dataHoraEntrada, dataHoraSaida, valor);

    }
}
