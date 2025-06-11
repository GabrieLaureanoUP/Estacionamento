package view;

import controllers.TicketController;
import controllers.VagaController;
import controllers.VeiculoController;
import dal.TicketDAO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Ticket;
import model.Vaga;
import model.Veiculo;

public class TicketView {

    private TicketController ticketController;
    private VeiculoController veiculoController;
    private VagaController vagaController;
    private Scanner scanner;

    public TicketView(TicketController ticketController, VeiculoController veiculoController, VagaController vagaController) {
        this.ticketController = ticketController;
        this.veiculoController = veiculoController;
        this.vagaController = vagaController;
        this.scanner = new Scanner(System.in);
    }

    public void menuTicket() throws Exception {
        int opcao;
        List<Ticket> lista = new ArrayList<>();
        try {
            lista = TicketDAO.carregar();
        } catch (Exception e) {
            System.err.println("Erro ao carregar a lista " + e.getMessage());
        }

        try {
            ticketController.setTickets(lista);
        } catch (Exception e) {
            System.err.println("Erro ao definir tickets: " + e.getMessage());
        }

        do {
            System.out.println("\n--- Menu de Tickets ---");
            System.out.println("1. Gerar Ticket");
            System.out.println("2. Listar Tickets");
            System.out.println("3. Atualizar Ticket");
            System.out.println("4. Remover Ticket");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1 ->
                    gerarTicket();
                case 2 ->
                    listarTickets();
                case 3 ->
                    atualizarTicket();
                case 4 ->
                    removerTicket();
                case 0 -> {
                    try {
                        ticketController.salvar();
                    } catch (Exception e) {
                        System.err.println("Erro ao salvar lista. " + e.getMessage());
                    } finally {
                        System.out.println("Voltando ao menu principal...");
                    }
                }
                default ->
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void gerarTicket() {
        try {
            System.out.print("Placa do veículo: ");
            String placa = scanner.nextLine();
            Veiculo veiculo = VeiculoController.buscarVeiculoPorPlaca(placa);

            if (veiculo == null) {
                System.out.println("Veículo não encontrado!");
                return;
            }

            System.out.print("Número da vaga: ");
            int numeroVaga = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            Vaga vaga = vagaController.buscarVagaPorNumero(numeroVaga);
            if (vaga == null) {
                System.out.println("Vaga não encontrada!");
                return;
            }

            System.out.print("Valor da tarifa/hora: ");
            double valor = scanner.nextDouble();
            scanner.nextLine(); // Limpar buffer

            var ticket = ticketController.gerarTicket(veiculo, vaga, valor);
            System.out.println("Ticket gerado com sucesso: " + ticket);
        } catch (Exception e) {
            System.out.println("Erro ao gerar ticket: " + e.getMessage());
        }
    }

    private void listarTickets() throws Exception {
        System.out.println("\nLista de Tickets:");
        ticketController.getTickets().forEach(System.out::println);
    }

    private void atualizarTicket() {
        try {
            System.out.print("ID do ticket para atualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            System.out.print("Placa do veículo: ");
            String placa = scanner.nextLine();
            Veiculo veiculo = VeiculoController.buscarVeiculoPorPlaca(placa);

            if (veiculo == null) {
                System.out.println("Veículo não encontrado!");
                return;
            }

            System.out.print("Número da vaga: ");
            int numeroVaga = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            Vaga vaga = vagaController.buscarVagaPorNumero(numeroVaga);
            if (vaga == null) {
                System.out.println("Vaga não encontrada!");
                return;
            }

            LocalDateTime dataHoraEntrada = LocalDateTime.now().minusHours(1); // Exemplo: entrada há 1 hora
            LocalDateTime dataHoraSaida = LocalDateTime.now();

            System.out.print("Valor do ticket: ");
            double valor = scanner.nextDouble();
            scanner.nextLine(); // Limpar buffer

            boolean sucesso = ticketController.atualizarTicket(id, veiculo, vaga, dataHoraEntrada, dataHoraSaida, valor);
            if (sucesso) {
                System.out.println("Ticket atualizado com sucesso!");
            } else {
                System.out.println("Não foi possível atualizar o ticket. Verifique o ID.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar ticket: " + e.getMessage());
        }
    }

    private void removerTicket() {
        try {
            System.out.print("ID do ticket para remover: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            boolean sucesso = ticketController.removerTicket(id);
            if (sucesso) {
                System.out.println("Ticket removido com sucesso!");
            } else {
                System.out.println("Não foi possível remover o ticket. Verifique o ID.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao remover ticket: " + e.getMessage());
        }
    }
}
