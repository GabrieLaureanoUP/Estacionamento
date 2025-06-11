package view;

import controllers.EstacionamentoController;
import controllers.PagamentoController;
import controllers.TicketController;
import controllers.VeiculoController;
import java.time.LocalDateTime;
import java.util.List;
import model.Carro;
import model.Moto;
import model.Ticket;
import model.Vaga;
import model.Veiculo;

public class PrecargaView {

    private final EstacionamentoController estacionamentoController;
    private final VeiculoController veiculoController;
    private final TicketController ticketController;
    private final PagamentoController pagamentoController;

    public PrecargaView(
            EstacionamentoController estacionamentoController,
            VeiculoController veiculoController,
            TicketController ticketController,
            PagamentoController pagamentoController) {

        this.estacionamentoController = estacionamentoController;
        this.veiculoController = veiculoController;
        this.ticketController = ticketController;
        this.pagamentoController = pagamentoController;
    }

    public void carregarDadosTeste() {
        try {

            estacionamentoController.cadastrarEstacionamento(
                    "Estacionamento Teste",
                    20,
                    "Av. Principal, 123",
                    "(11) 98765-4321",
                    "contato@estacionamentoteste.com.br");
            veiculoController.criarCarro("ABC-1234", "Fiat Uno", "Vermelho", LocalDateTime.now());
            veiculoController.criarCarro("DEF-5678", "Honda Civic", "Preto", LocalDateTime.now());
            veiculoController.criarCarro("GHI-9012", "Toyota Corolla", "Prata", LocalDateTime.now());
            veiculoController.criarMoto("JKL-3456", "Honda CG 150", "Azul", LocalDateTime.now());
            veiculoController.criarMoto("MNO-7890", "Yamaha Fazer", "Preta", LocalDateTime.now());

            Veiculo carro1 = veiculoController.buscarVeiculoPorPlaca("ABC-1234");
            Veiculo carro2 = veiculoController.buscarVeiculoPorPlaca("DEF-5678");
            Veiculo moto1 = veiculoController.buscarVeiculoPorPlaca("JKL-3456");

            if (carro1 instanceof Carro && carro2 instanceof Carro && moto1 instanceof Moto) {
                estacionamentoController.alocarCarro((Carro) carro1);
                estacionamentoController.alocarCarro((Carro) carro2);
                estacionamentoController.alocarMoto((Moto) moto1);
            }

            if (carro1.getIdVaga() > 0 && carro2.getIdVaga() > 0 && moto1.getIdVaga() > 0) {
                Vaga vagaCarro1 = null;
                Vaga vagaCarro2 = null;
                Vaga vagaMoto1 = null;

                for (Vaga vaga : estacionamentoController.estacionamentos.getVagas()) {
                    if (vaga.getNumero() == carro1.getIdVaga()) {
                        vagaCarro1 = vaga;
                    }
                    if (vaga.getNumero() == carro2.getIdVaga()) {
                        vagaCarro2 = vaga;
                    }
                    if (vaga.getNumero() == moto1.getIdVaga()) {
                        vagaMoto1 = vaga;
                    }
                }

                if (vagaCarro1 != null) {
                    ticketController.gerarTicket(carro1, vagaCarro1, 10.0);
                }
                if (vagaCarro2 != null) {
                    ticketController.gerarTicket(carro2, vagaCarro2, 10.0);
                }
                if (vagaMoto1 != null) {
                    ticketController.gerarTicket(moto1, vagaMoto1, 5.0);
                }
            }

            try {
                List<Ticket> tickets = ticketController.getTickets();
                if (!tickets.isEmpty()) {
                    Ticket primeiroTicket = tickets.get(0);
                    pagamentoController.pagar(primeiroTicket, 15.0, "DINHEIRO");
                }
            } catch (Exception e) {
                System.err.println("Erro ao processar tickets: " + e.getMessage());
            }

            System.out.println("Pré-carga concluída");

        } catch (Exception e) {
            System.err.println("Erro na pré-carga: " + e.getMessage());
        }
    }
}
