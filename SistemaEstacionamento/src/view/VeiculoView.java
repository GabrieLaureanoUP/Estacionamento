package view;

import controllers.VeiculoController;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Carro;
import model.Veiculo;

public class VeiculoView {

    private final VeiculoController veiculoController;
    private final Scanner scanner;

    public VeiculoView(VeiculoController veiculoController) {
        this.veiculoController = veiculoController;
        this.scanner = new Scanner(System.in);
    }

    public void menuVeiculo() {
        int opcao;
        List<Veiculo> lista = new ArrayList<>();
        try {
            lista = veiculoController.carregar();
        } catch (Exception e) {
            System.err.println("Erro ao carregar a lista " + e.getMessage());
        }

        try {
            veiculoController.setVeiculos(lista);
        } catch (Exception e) {
            System.err.println("Erro ao definir veículos: " + e.getMessage());
        }

        do {
            exibirMenuVeiculo();
            opcao = lerOpcao();

            try {
                executarOpcao(opcao);
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private void exibirMenuVeiculo() {
        System.out.println("\n=== Menu de Veículos ===");
        System.out.println("1. Listar Veículos");
        System.out.println("2. Cadastrar Carro");
        System.out.println("3. Cadastrar Moto");
        System.out.println("4. Buscar Veículo por Placa");
        System.out.println("5. Atualizar Veículo");
        System.out.println("6. Remover Veículo");
        System.out.println("0. Voltar");
    }

    private int lerOpcao() {
        System.out.print("Escolha uma opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }

    private void executarOpcao(int opcao) throws Exception {
        switch (opcao) {
            case 1 ->
                listarVeiculos();
            case 2 ->
                cadastrarCarro();
            case 3 ->
                cadastrarMoto();
            case 4 ->
                buscarVeiculoPorPlaca();
            case 5 ->
                atualizarVeiculo();
            case 6 ->
                removerVeiculo();
            case 0 -> {
                try {
                    veiculoController.salvar();
                } catch (Exception e) {
                    System.err.println("Erro ao salvar lista. " + e.getMessage());
                } finally {
                    System.out.println("Voltando ao menu principal...");
                }
            }
            default ->
                System.out.println("Opção inválida!");
        }
    }

    private void listarVeiculos() throws Exception {
        System.out.println("\n=== Lista de Veículos ===");
        List<String> veiculos = veiculoController.listarVeiculos();

        if (veiculos.isEmpty()) {
            System.out.println("Não há veículos cadastrados.");
            return;
        }

        veiculos.forEach(veiculo -> {
            System.out.println("-------------------------");
            System.out.println(veiculo);
        });
    }

    private void cadastrarCarro() throws Exception {
        System.out.println("\n=== Cadastro de Carro ===");
        System.out.print("Placa (XXX-0000): ");
        String placa = scanner.nextLine().toUpperCase();

        if (veiculoController.buscarVeiculoPorPlaca(placa) != null) {
            System.out.println("Erro: Já existe um veículo com esta placa!");
            return;
        }

        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        System.out.print("Cor: ");
        String cor = scanner.nextLine();
        veiculoController.criarCarro(placa, modelo, cor, LocalDateTime.now());
        System.out.println("Carro cadastrado com sucesso!");
    }

    private void cadastrarMoto() {
        try {
            System.out.println("\n=== Cadastro de Moto ===");
            System.out.print("Placa (XXX-0000): ");
            String placa = scanner.nextLine().toUpperCase();

            if (veiculoController.buscarVeiculoPorPlaca(placa) != null) {
                System.out.println("Erro: Já existe um veículo com esta placa!");
                return;
            }

            System.out.print("Modelo: ");
            String modelo = scanner.nextLine();
            System.out.print("Cor: ");
            String cor = scanner.nextLine();
            veiculoController.criarMoto(placa, modelo, cor, LocalDateTime.now());
            System.out.println("Moto cadastrada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar moto: " + e.getMessage());
        }
    }

    private void buscarVeiculoPorPlaca() throws Exception {
        System.out.println("\n=== Busca de Veículo ===");
        System.out.print("Digite a placa do veículo: ");
        String placa = scanner.nextLine().toUpperCase();

        Veiculo veiculo = veiculoController.buscarVeiculoPorPlaca(placa);
        if (veiculo != null) {
            System.out.println("\nVeículo encontrado:");
            System.out.println("-------------------------");
            System.out.println("Tipo: " + (veiculo instanceof Carro ? "Carro" : "Moto"));
            System.out.println("Placa: " + veiculo.getPlaca());
            System.out.println("Modelo: " + veiculo.getModelo());
            System.out.println("Cor: " + veiculo.getCor());
            if (veiculo.getIdVaga() != 0) {
                System.out.println("Vaga atual: " + veiculo.getIdVaga());
            }
        } else {
            System.out.println("Veículo não encontrado!");
        }
    }

    private void atualizarVeiculo() throws Exception {
        System.out.println("\n=== Atualização de Veículo ===");
        System.out.print("Placa do veículo: ");
        String placa = scanner.nextLine().toUpperCase();
        Veiculo veiculoExistente = veiculoController.buscarVeiculoPorPlaca(placa);
        if (veiculoExistente == null) {
            System.out.println("Veículo não encontrado!");
            return;
        }

        System.out.println("\nDados atuais:");
        System.out.println("Modelo: " + veiculoExistente.getModelo());
        System.out.println("Cor: " + veiculoExistente.getCor());

        System.out.print("\nNovo modelo (Enter para manter): ");
        String modelo = scanner.nextLine();
        if (modelo.isEmpty()) {
            modelo = veiculoExistente.getModelo();
        }

        System.out.print("Nova cor (Enter para manter): ");
        String cor = scanner.nextLine();
        if (cor.isEmpty()) {
            cor = veiculoExistente.getCor();
        }
        Veiculo veiculoAtualizado = veiculoController.atualizarVeiculo(placa, modelo, cor);
        System.out.println("Veículo atualizado com sucesso!");
        System.out.println(veiculoAtualizado);
    }

    private void removerVeiculo() throws Exception {
        System.out.println("\n=== Remoção de Veículo ===");
        System.out.print("Placa do veículo: ");
        String placa = scanner.nextLine().toUpperCase();
        Veiculo veiculo = veiculoController.buscarVeiculoPorPlaca(placa);
        if (veiculo == null) {
            System.out.println("Veículo não encontrado!");
            return;
        }

        if (veiculo.getIdVaga() != 0) {
            System.out.println("Não é possível remover um veículo que está estacionado!");
            return;
        }
        veiculoController.removerVeiculo(placa);
        System.out.println("Veículo removido com sucesso!");
    }
}
