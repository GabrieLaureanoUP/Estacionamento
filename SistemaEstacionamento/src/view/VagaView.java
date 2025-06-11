package view;

import controllers.VagaController;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Vaga;

public class VagaView {

    private VagaController vagaController;
    private Scanner scanner;

    public VagaView(VagaController vagaController) {
        this.vagaController = vagaController;
        this.scanner = new Scanner(System.in);
    }

    public void menuVaga() throws Exception {
        int opcao;
        List<Vaga> lista = new ArrayList<>();
        try {
            lista = vagaController.carregar();
        } catch (Exception e) {
            System.err.println("Erro ao carregar a lista " + e.getMessage());
        }

        vagaController.setVagas(lista);

        do {
            System.out.println("\n--- Menu de Vagas ---");
            System.out.println("1. Listar Vagas");
            System.out.println("2. Buscar Vaga por Número");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 ->
                    listarVagas();
                case 2 ->
                    buscarVagaPorNumero();
                case 0 -> {
                    try {
                        vagaController.salvar();
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

    private void listarVagas() throws Exception {
        System.out.println("\nLista de Vagas:");
        List<String> vagas = vagaController.listarVagas();

        if (vagas.isEmpty()) {
            System.out.println("Não há vagas cadastradas.");
        } else {
            vagas.forEach(System.out::println);
        }
    }

    private void buscarVagaPorNumero() throws Exception {
        System.out.print("Digite o número da vaga: ");
        int numero = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        Vaga vaga = vagaController.buscarVagaPorNumero(numero);
        if (vaga != null) {
            System.out.println("Vaga encontrada: " + vaga);
        } else {
            System.out.println("Vaga não encontrada!");
        }
    }
}
