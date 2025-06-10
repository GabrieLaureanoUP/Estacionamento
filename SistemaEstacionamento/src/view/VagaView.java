package view;

import controllers.VagaController;
import dal.VagaDAO;
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
            lista = VagaDAO.carregar();
        } catch (Exception e) {
            System.err.println("Erro ao carregar a lista " + e.getMessage());
        }

        do {
            System.out.println("\n--- Menu de Vagas ---");
            System.out.println("1. Criar Vagas");
            System.out.println("2. Listar Vagas");
            System.out.println("3. Buscar Vaga por Número");
            System.out.println("4. Remover Vaga");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1 ->
                    criarVagas();
                case 2 ->
                    listarVagas();
                case 3 ->
                    buscarVagaPorNumero();
                case 4 ->
                    removerVaga();
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

    private void criarVagas() throws Exception {
        System.out.print("Quantas vagas deseja criar? ");
        int numeroDeVagas = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        List<Vaga> vagas = vagaController.criarVagas(numeroDeVagas);
        System.out.println("Vagas criadas com sucesso!");
        System.out.println("Total de vagas: " + vagas.size());

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

    private void removerVaga() throws Exception {
        System.out.print("Digite o número da vaga para remover: ");
        int numero = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        vagaController.removerVaga(numero);
        System.out.println("Vaga removida (se existia).");
    }
}
