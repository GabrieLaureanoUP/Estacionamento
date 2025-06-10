package view;

import controllers.EstacionamentoController;
import controllers.VeiculoController;
import dal.EstacionamentoDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Carro;
import model.Estacionamento;
import model.Moto;
import model.Veiculo;

public class EstacionamentoView {

    private EstacionamentoController estacionamentoController;
    private VeiculoController veiculoController;
    private Scanner scanner;

    public EstacionamentoView(EstacionamentoController estacionamentoController, VeiculoController veiculoController) {
        this.estacionamentoController = estacionamentoController;
        this.veiculoController = veiculoController;
        this.scanner = new Scanner(System.in);
    }

    public void menuEstacionamento() throws Exception {
        int opcao;
        List<Estacionamento> lista = new ArrayList<>();

        try {
            lista = EstacionamentoDAO.carregar();
        } catch (Exception e) {
            System.err.println("Erro ao carregar a lista " + e.getMessage());
        }

        do {
            System.out.println("\n--- Menu do Estacionamento ---");
            System.out.println("1. Cadastrar Estacionamento");
            System.out.println("2. Alocar Carro");
            System.out.println("3. Alocar Moto");
            System.out.println("0. Voltar");

            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1 ->
                    cadastrarEstacionamento();
                case 2 ->
                    alocarCarro();
                case 3 ->
                    alocarMoto();
                case 0 -> {
                    System.out.println("Voltando ao menu principal...");
                    try {
                        EstacionamentoDAO.salvar(lista);
                    } catch (Exception e) {
                        System.err.println("Erro ao salvar a lista " + e.getMessage());
                    }
                }

                default ->
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void cadastrarEstacionamento() throws Exception {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Número de vagas: ");
        int vagas = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        estacionamentoController.cadastrarEstacionamento(nome, vagas, endereco, telefone, email);
        System.out.println("Estacionamento cadastrado com sucesso!");
    }

    private void alocarCarro() {
        try {
            if (estacionamentoController.estacionamentos == null) {
                System.out.println("Estacionamento não cadastrado! Cadastre um estacionamento primeiro.");
                return;
            }

            System.out.print("Placa do carro: ");
            String placa = scanner.nextLine();

            Veiculo veiculo = VeiculoController.buscarVeiculoPorPlaca(placa);
            if (veiculo == null || !(veiculo instanceof Carro)) {
                System.out.println("Carro não encontrado. Deseja cadastrar um novo? (S/N)");
                String opcao = scanner.nextLine();
                if (opcao.equalsIgnoreCase("S")) {
                    System.out.print("Modelo: ");
                    String modelo = scanner.nextLine();
                    System.out.print("Cor: ");
                    String cor = scanner.nextLine();
                    veiculoController.criarCarro(placa, modelo, cor, java.time.LocalDateTime.now());
                    veiculo = VeiculoController.buscarVeiculoPorPlaca(placa);
                } else {
                    return;
                }
            }

            String resultado = estacionamentoController.alocarCarro((Carro) veiculo);

            System.out.println(resultado);
        } catch (Exception e) {
            System.out.println("Erro ao alocar carro: " + e.getMessage());
        }
    }

    private void alocarMoto() {
        try {
            if (estacionamentoController.estacionamentos == null) {
                System.out.println("Estacionamento não cadastrado! Cadastre um estacionamento primeiro.");
                return;
            }

            System.out.print("Placa da moto: ");
            String placa = scanner.nextLine();

            Veiculo veiculo = VeiculoController.buscarVeiculoPorPlaca(placa);
            if (veiculo == null || !(veiculo instanceof Moto)) {
                System.out.println("Moto não encontrada. Deseja cadastrar uma nova? (S/N)");
                String opcao = scanner.nextLine();
                if (opcao.equalsIgnoreCase("S")) {
                    System.out.print("Modelo: ");
                    String modelo = scanner.nextLine();
                    System.out.print("Cor: ");
                    String cor = scanner.nextLine();
                    veiculoController.criarMoto(placa, modelo, cor, java.time.LocalDateTime.now());
                    veiculo = VeiculoController.buscarVeiculoPorPlaca(placa);
                } else {
                    return;
                }
            }

            String resultado = estacionamentoController.alocarMoto((Moto) veiculo);
            System.out.println(resultado);
        } catch (Exception e) {
            System.out.println("Erro ao alocar moto: " + e.getMessage());
        }
    }
}
