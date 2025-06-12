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
            VagaOcupadaController vagaOcupadaController = new VagaOcupadaController();
            EstacionamentoController estacionamentoController = new EstacionamentoController(vagaOcupadaController);
            VeiculoController veiculoController = new VeiculoController();
            TicketController ticketController = new TicketController();
            VagaController vagaController = new VagaController();
            PagamentoController pagamentoController = new PagamentoController(vagaOcupadaController);

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
