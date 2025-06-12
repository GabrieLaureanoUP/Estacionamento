package controllers;

import dal.TicketDAO;
import factory.TicketFactory;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import model.Ticket;
import model.Vaga;
import model.Veiculo;

public class TicketController {

    private List<Ticket> tickets;

    public TicketController(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Ticket gerarTicket(Veiculo veiculo, Vaga vaga) throws Exception {
        int id = 1;
        try {
            if (!tickets.isEmpty()) {
                id = tickets.get(tickets.size() - 1).getId() + 1;
            }
            LocalDateTime dataHoraEntrada = veiculo.getDataHoraEntrada();
            LocalDateTime dataHoraSaida = LocalDateTime.now();
            int horasTotais = Math.abs(dataHoraSaida.getHour() - dataHoraEntrada.getHour());
            if (horasTotais == 0) {
                horasTotais = 1;
            }
            double valorPorHora = veiculo.getValorPorHoras();
            double valorTotal = valorPorHora * horasTotais;
            Ticket ticket = TicketFactory.criarTicket(id, veiculo, vaga, dataHoraEntrada, dataHoraSaida, valorTotal);
            tickets.add(ticket);
            return ticket;
        } catch (Exception e) {
            throw new Exception("Erro ao gerar ticket: " + e.getMessage(), e);
        }
    }

    public List<Ticket> listarTickets(Vaga vaga) throws Exception {
        try {
            return tickets;
        } catch (Exception e) {
            throw new Exception("Erro ao listar tickets: " + e.getMessage(), e);
        }
    }

    public List<Ticket> getTickets() throws Exception {
        try {
            return tickets;
        } catch (Exception e) {
            throw new Exception("Erro ao obter tickets: " + e.getMessage(), e);
        }
    }

    public Ticket getTicketById(int id) throws Exception {
        try {
            for (Ticket ticket : tickets) {
                if (ticket.getId() == id) {
                    return ticket;
                }
            }
            return null;
        } catch (Exception e) {
            throw new Exception("Erro ao buscar ticket por ID: " + e.getMessage());
        }
    }

    public boolean atualizarTicket(int id, Veiculo veiculo, Vaga vaga, LocalDateTime dataHoraEntrada, LocalDateTime dataHoraSaida) throws Exception {
        try {
            Ticket ticket = getTicketById(id);
            if (ticket != null) {
                ticket.setVeiculo(veiculo);
                ticket.setVaga(vaga);
                ticket.setDataHoraEntrada(dataHoraEntrada);
                ticket.setDataHoraSaida(dataHoraSaida);

                int horasTotais = Math.abs(dataHoraSaida.getHour() - dataHoraEntrada.getHour());
                if (horasTotais < 0) {
                    horasTotais = 1;
                }
                double valorPorHora = veiculo.getValorPorHoras();
                double valorTotal = valorPorHora * horasTotais;
                ticket.setValor(valorTotal);

                return true;
            }
            return false;
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar ticket: " + e.getMessage(), e);
        }
    }

    public boolean removerTicket(int id) throws Exception {
        try {
            return tickets.removeIf(ticket -> ticket.getId() == id);
        } catch (Exception e) {
            throw new Exception("Erro ao remover ticket: " + e.getMessage(), e);
        }
    }

    public void setTickets(List<Ticket> tickets) throws Exception {
        try {
            this.tickets = tickets;
        } catch (Exception e) {
            throw new Exception("Erro ao definir tickets: " + e.getMessage(), e);
        }
    }

    public void salvar() throws IOException, ClassNotFoundException {
        try {
            TicketDAO.salvar(tickets);
        } catch (Exception e) {
            throw new IOException("Erro ao salvar tickets no arquivo: " + e.getMessage(), e);
        }
    }

    public List<Ticket> carregar() throws Exception {
        try {
            return TicketDAO.carregar();
        } catch (Exception e) {
            throw new Exception("Erro ao carregar tickets.", e);
        }
    }
}
