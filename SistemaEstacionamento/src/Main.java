
import controllers.EstacionamentoController;
import controllers.PagamentoController;
import controllers.TicketController;
import controllers.VagaController;
import controllers.VeiculoController;
import java.util.InputMismatchException;
import java.util.Scanner;
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
            EstacionamentoController estacionamentoController = new EstacionamentoController();
            VeiculoController veiculoController = new VeiculoController();
            TicketController ticketController = new TicketController();
            VagaController vagaController = new VagaController();
            PagamentoController pagamentoController = new PagamentoController();
            vagaController.setVagas(estacionamentoController.estacionamentos != null
                    ? estacionamentoController.estacionamentos.getVagas() : null);
            VeiculoView veiculoView = new VeiculoView(veiculoController);
            EstacionamentoView estacionamentoView = new EstacionamentoView(estacionamentoController);
            TicketView ticketView = new TicketView(ticketController, veiculoController, vagaController);
            PagamentoView pagamentoView = new PagamentoView(pagamentoController, ticketController);
            PrecargaView precargaView = new PrecargaView(estacionamentoController, veiculoController, ticketController, pagamentoController);

            // Limpar arquivos serializados antes de executar a pré-carga
            DadosUtil.limparArquivosSerializados();

            // Executar pré-carga de dados automaticamente ao iniciar
            precargaView.carregarDadosTeste();

            int opcao = -1;
            do {
                System.out.println("\n=== SISTEMA DE ESTACIONAMENTO ===");
                System.out.println("1. Gerenciar Estacionamento");
                System.out.println("2. Gerenciar Veículos");
                System.out.println("3. Gerenciar Tickets");
                System.out.println("4. Gerenciar Pagamentos");
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
