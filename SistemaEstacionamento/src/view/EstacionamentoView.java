package view;

import controllers.EstacionamentoController;
import controllers.VagaOcupadaController;
import controllers.VeiculoController;
import dal.EstacionamentoDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Carro;
import model.Estacionamento;
import model.Moto;
import model.Vaga;
import model.VagaOcupada;
import model.Veiculo;

public class EstacionamentoView {

    private final EstacionamentoController estacionamentoController;
    private final VeiculoController veiculoController;
    private final VagaOcupadaController vagaOcupadaController;
    private final Scanner scanner;

    public EstacionamentoView(EstacionamentoController estacionamentoController,
            VeiculoController veiculoController,
            VagaOcupadaController vagaOcupadaController) {
        this.estacionamentoController = estacionamentoController;
        this.veiculoController = veiculoController;
        this.vagaOcupadaController = vagaOcupadaController;
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
            System.out.println("4. Listar vagas");
            System.out.println("5. Listar vagas ocupadas");
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
                case 4 ->
                    listarVagas();
                case 5 ->
                    listarVagasOcupadas();
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
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Número de vagas: ");
            int vagas = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Endereço: ");
            String endereco = scanner.nextLine();
            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            if (estacionamentoController.estacionamentos != null) {
                System.out.println("Estacionamento já cadastrado! Por favor, edite o estacionamento existente.");

            } else {
                estacionamentoController.cadastrarEstacionamento(nome, vagas, endereco, telefone, email);
                System.out.println("Estacionamento cadastrado com sucesso!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar estacionamento: " + e.getMessage());
        }
    }

    private void alocarCarro() {
        try {
            if (estacionamentoController.estacionamentos == null) {
                System.out.println("Estacionamento não cadastrado! Cadastre um estacionamento primeiro.");
                return;
            }
            System.out.print("Placa do carro: ");
            String placa = scanner.nextLine();
            Veiculo veiculo = veiculoController.buscarVeiculoPorPlaca(placa);
            if (veiculo == null || !(veiculo instanceof Carro)) {
                System.out.println("Carro não encontrado! Certifique-se de cadastrar o veículo primeiro.");
                return;
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
            Veiculo veiculo = veiculoController.buscarVeiculoPorPlaca(placa);
            if (veiculo == null || !(veiculo instanceof Moto)) {
                System.out.println("Moto não encontrada! Certifique-se de cadastrar o veículo primeiro.");
                return;
            }

            String resultado = estacionamentoController.alocarMoto((Moto) veiculo);
            System.out.println(resultado);
        } catch (Exception e) {
            System.out.println("Erro ao alocar moto: " + e.getMessage());
        }
    }

    public void listarVagas() {
        try {
            List<Vaga> vagas = estacionamentoController.estacionamentos.getVagas();
            System.out.println("\n=== Lista de Vagas ===");

            if (vagas.isEmpty()) {
                System.out.println("Não há vagas cadastradas.");
                return;
            }

            for (Vaga vaga : vagas) {
                System.out.println("-------------------------");
                System.out.println("Vaga número: " + vaga.getNumero());
                System.out.println("Status: " + vaga.getStatus());
                System.out.println("Disponível para moto: " + (vaga.estaDisponivelParaMoto() ? "Sim" : "Não"));
                System.out.println("Disponível para carro: " + (vaga.estaDisponivel() ? "Sim" : "Não"));
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar vagas: " + e.getMessage());
        }
    }

    public void listarVagasOcupadas() {
        try {
            List<VagaOcupada> vagasOcupadas = vagaOcupadaController.listarVagasOcupadas();
            System.out.println("\n=== Lista de Vagas Ocupadas ===");

            if (vagasOcupadas.isEmpty()) {
                System.out.println("Não há vagas ocupadas no momento.");
                return;
            }

            for (VagaOcupada vagaOcupada : vagasOcupadas) {
                System.out.println("-------------------------");
                System.out.println("Vaga número: " + vagaOcupada.getVaga().getNumero());
                System.out.println("Status da vaga: " + vagaOcupada.getVaga().getStatus());
                System.out.println("Veículo: " + vagaOcupada.getVeiculo().getPlaca() + " - "
                        + vagaOcupada.getVeiculo().getModelo() + " ("
                        + vagaOcupada.getVeiculo().getCor() + ")");
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar vagas ocupadas: " + e.getMessage());
        }
    }
}
