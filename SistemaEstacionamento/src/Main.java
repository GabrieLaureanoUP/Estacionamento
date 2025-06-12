//Gabriel Lauraeano Soares da Silva RGM:38525071
//Marcos Vinicius Maues das Neves Brandão RGM:37399144
//Rafael dos Santos Correia RGM: 8838913402
//João Pedro RGM: 39279324

import controllers.EstacionamentoController;
import controllers.PagamentoController;
import controllers.TicketController;
import controllers.VagaController;
import controllers.VagaOcupadaController;
import controllers.VeiculoController;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import model.Pagamento;
import model.Ticket;
import model.VagaOcupada;
import model.Veiculo;
import util.DadosUtil;
import util.Log;
import view.EstacionamentoView;
import view.PagamentoView;
import view.PrecargaView;
import view.TicketView;
import view.VeiculoView;

public class Main {

    public static void main(String[] args) {
        Log.setError();

        Scanner scanner = new Scanner(System.in);
        try {
            List<Pagamento> pagamentos = new ArrayList<>();
            List<Ticket> tickets = new ArrayList<>();
            List<VagaOcupada> vagasOcupadas = new ArrayList<>();
            List<Veiculo> veiculos = new ArrayList<>();
            
            VagaOcupadaController vagaOcupadaController = new VagaOcupadaController(vagasOcupadas);
            EstacionamentoController estacionamentoController = new EstacionamentoController(vagasOcupadas);
            VeiculoController veiculoController = new VeiculoController(veiculos);
            TicketController ticketController = new TicketController(tickets);
            VagaController vagaController = new VagaController(estacionamentoController.estacionamentos.getVagas());
            PagamentoController pagamentoController = new PagamentoController(vagaOcupadaController, pagamentos);

            vagaController.setVagas(estacionamentoController.estacionamentos != null
                    ? estacionamentoController.estacionamentos.getVagas() : null);
            VeiculoView veiculoView = new VeiculoView(veiculoController);
            EstacionamentoView estacionamentoView = new EstacionamentoView(estacionamentoController, veiculoController, vagaOcupadaController);
            TicketView ticketView = new TicketView(ticketController, veiculoController, vagaController);
            PagamentoView pagamentoView = new PagamentoView(pagamentoController, ticketController);
            PrecargaView precargaView = new PrecargaView(estacionamentoController, veiculoController, ticketController, pagamentoController);

            DadosUtil.limparArquivosSerializados();

            precargaView.carregarDadosTeste();

            int opcao = -1;
            do {
                System.out.println("\n=== SISTEMA DE ESTACIONAMENTO ===");
                System.out.println("1. Gerenciar Estacionamento");
                System.out.println("2. Gerenciar Veículos");
                System.out.println("3. Gerenciar Tickets");
                System.out.println("4. Gerenciar Pagamentos");
                System.out.println("5. Gerar fatorial de um numero");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");

                try {
                    opcao = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida! Por favor, digite um número.");
                    System.err.println("Erro de entrada de usuário: " + e.getMessage());
                    e.printStackTrace();
                    scanner.nextLine();
                    opcao = -1;
                    continue;
                } finally {
                    scanner.nextLine();
                }
                switch (opcao) {
                    case 1:
                        estacionamentoView.menuEstacionamento();
                        if (estacionamentoController.estacionamentos != null) {
                            vagaController.setVagas(estacionamentoController.estacionamentos.getVagas());
                        }
                        break;
                    case 2:
                        veiculoView.menuVeiculo();
                        break;
                    case 3:
                        ticketView.menuTicket();
                        break;
                    case 4:
                        pagamentoView.menuPagamento();
                        break;
                    case 5:
                        System.out.print("Digite um número para calcular o fatorial: ");
                        int numero = scanner.nextInt();
                        if (numero < 0) {
                            System.out.println("Fatorial não definido para números negativos.");
                        } else {
                            int fatorial = 1;
                            for (int i = 1; i <= numero; i++) {
                                fatorial *= i;
                            }
                            System.out.println("Fatorial de " + numero + " é: " + fatorial);
                        }
                    break;
                    case 0:
                        System.out.println("Saindo do sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida! Escolha um número entre 0 e 4.");
                }
            } while (opcao != 0);

        } catch (Exception e) {
            System.err.println("Um erro inesperado ocorreu no sistema principal: " + e.getMessage());
            System.err.println("Por favor, verifique o log de erros para mais detalhes.");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
