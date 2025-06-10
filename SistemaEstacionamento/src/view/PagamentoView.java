package view;

import controllers.PagamentoController;
import controllers.TicketController;
import dal.PagamentoDAO;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import model.Pagamento;
import model.Ticket;

public class PagamentoView {

    private PagamentoController pagamentoController;
    private TicketController ticketController;
    private Scanner scanner;

    public PagamentoView(PagamentoController pagamentoController, TicketController ticketController) {
        this.pagamentoController = pagamentoController;
        this.ticketController = ticketController;
        this.scanner = new Scanner(System.in);

        // Carrega os pagamentos salvos do DAO para o controller
        try {
            List<Pagamento> pagamentosSalvos = PagamentoDAO.carregar();
            if (pagamentosSalvos != null && !pagamentosSalvos.isEmpty()) {
                for (Pagamento p : pagamentosSalvos) {
                    this.pagamentoController.getPagamentos().add(p);
                }
                System.out.println("[PagamentoView] " + pagamentosSalvos.size() + " pagamentos carregados com sucesso!");
            }
        } catch (Exception e) {
            System.err.println("[PagamentoView] Erro ao carregar pagamentos do DAO: " + e.getMessage());
        }
    }

    public void menuPagamento() {
        int opcao;

        do {
            exibirMenuPagamento();
            opcao = lerOpcao();

            try {
                executarOpcao(opcao);
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                System.err.println("[PagamentoView] Erro ao executar opção " + opcao + ": " + e.getMessage());
            }
        } while (opcao != 0);
    }

    // Método para exibir o menu
    private void exibirMenuPagamento() {
        System.out.println("\n--- Menu de Pagamentos ---");
        System.out.println("1. Realizar Pagamento");
        System.out.println("2. Listar Pagamentos");
        System.out.println("3. Editar Pagamento");
        System.out.println("4. Remover Pagamento");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
    }

    // Método para ler a opção com tratamento de InputMismatchException
    private int lerOpcao() {
        int opcao;
        try {
            opcao = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida! Por favor, digite um número para a opção.");
            System.err.println("[PagamentoView] Erro de entrada de usuário em lerOpcao: " + e.getMessage());
            return -1; // Retorna um valor inválido para manter o loop
        } finally {
            scanner.nextLine(); // Limpar buffer sempre, seja sucesso ou falha
        }
        return opcao;
    }

    // Método para executar a opção selecionada
    private void executarOpcao(int opcao) throws Exception {
        switch (opcao) {
            case 1 ->
                realizarPagamento();
            case 2 ->
                listarPagamentos();
            case 3 ->
                editarPagamento();
            case 4 ->
                removerPagamento();
            case 0 -> {
                try {
                    // Salvamos a lista antes de sair
                    pagamentoController.salvar();
                    System.out.println("✅ Lista de pagamentos salva com sucesso!");
                } catch (Exception e) {
                    System.out.println("❌ Erro ao salvar lista de pagamentos: " + e.getMessage());
                    System.err.println("[PagamentoView] Erro ao salvar lista de pagamentos ao sair: " + e.getMessage());
                    throw e; // Propagamos a exceção para tratamento superior se necessário
                } finally {
                    System.out.println("Voltando ao menu principal...");
                }
            }
            default ->
                System.out.println("❌ Opção inválida! Escolha um número entre 0 e 4.");
        }
    }

    private void realizarPagamento() {
        System.out.println("\n--- Realizar Pagamento ---");
        try {
            System.out.print("ID do Ticket: ");
            int ticketId = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            Ticket ticket = ticketController.getTicketById(ticketId);
            if (ticket == null) {
                System.out.println("❌ Ticket não encontrado!");
                System.err.println("[PagamentoView] Tentativa de pagamento com ticket inexistente: ID " + ticketId);
                return;
            }

            System.out.println("Ticket encontrado: " + ticket.toString());
            System.out.print("Valor a pagar: ");
            double valor = scanner.nextDouble();
            scanner.nextLine(); // Limpar buffer

            System.out.print("Forma de pagamento (DINHEIRO, CARTAO_CREDITO, CARTAO_DEBITO, PIX): ");
            String formaPagamento = scanner.nextLine().toUpperCase();

            String resultado = pagamentoController.pagar(ticket, valor, formaPagamento);
            System.out.println(resultado);

            // Salva automaticamente após cada operação para garantir persistência
            try {
                pagamentoController.salvar();
                System.out.println("✅ Pagamento salvo com sucesso!");
            } catch (Exception e) {
                System.err.println("[PagamentoView] Erro ao salvar pagamento no DAO: " + e.getMessage());
            }

            System.out.println("[PagamentoView] Pagamento realizado para ticket ID " + ticketId);

        } catch (InputMismatchException e) {
            System.out.println("❌ Entrada inválida! Verifique o formato dos dados inseridos.");
            System.err.println("[PagamentoView] InputMismatchException em realizarPagamento: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Argumento inválido: " + e.getMessage());
            System.err.println("[PagamentoView] IllegalArgumentException em realizarPagamento: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Erro ao realizar pagamento: " + e.getMessage());
            System.err.println("[PagamentoView] Exceção geral em realizarPagamento: " + e.getMessage());
        }
    }

    private void listarPagamentos() {
        System.out.println("\n--- Lista de Pagamentos ---");
        try {
            List<String> pagamentos = pagamentoController.listarPagamentos();
            if (pagamentos.isEmpty()) {
                System.out.println("Não há pagamentos registrados.");
            } else {
                System.out.println("ID | Ticket | Valor | Forma de Pagamento");
                System.out.println("----------------------------------------");
                pagamentos.forEach(System.out::println);
            }
            System.out.println("[PagamentoView] Listagem de pagamentos concluída.");
        } catch (Exception e) {
            System.out.println("❌ Erro ao listar pagamentos: " + e.getMessage());
            System.err.println("[PagamentoView] Erro interno ao listar pagamentos: " + e.getMessage());
        }
    }

    private void editarPagamento() {
        System.out.println("\n--- Editar Pagamento ---");
        try {
            // Primeiro listar os pagamentos para o usuário ver o que pode editar
            List<String> pagamentos = pagamentoController.listarPagamentos();
            if (pagamentos.isEmpty()) {
                System.out.println("Não há pagamentos registrados para editar.");
                return;
            }

            System.out.println("Pagamentos disponíveis:");
            pagamentos.forEach(System.out::println);

            System.out.print("ID do pagamento para editar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            // Verificamos se o pagamento existe antes de tentar editar
            boolean encontrado = false;
            for (Pagamento pg : pagamentoController.getPagamentos()) {
                if (pg.getId() == id) {
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("❌ Pagamento com ID " + id + " não encontrado.");
                return;
            }

            String resultado = pagamentoController.editarPagamentoPorID(id);
            System.out.println(resultado);

            // Salva automaticamente após a edição
            try {
                pagamentoController.salvar();
                System.out.println("✅ Alterações salvas com sucesso!");
            } catch (Exception e) {
                System.err.println("[PagamentoView] Erro ao salvar edição no DAO: " + e.getMessage());
            }

        } catch (InputMismatchException e) {
            System.out.println("❌ Entrada inválida! Verifique o formato dos dados inseridos.");
            System.err.println("[PagamentoView] Entrada inválida em editarPagamento: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Argumento inválido: " + e.getMessage());
            System.err.println("[PagamentoView] Argumento inválido em editarPagamento: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Erro ao editar pagamento: " + e.getMessage());
            System.err.println("[PagamentoView] Exceção geral em editarPagamento: " + e.getMessage());
        }
    }

    private void removerPagamento() {
        System.out.println("\n--- Remover Pagamento ---");
        try {
            // Primeiro listar os pagamentos para o usuário ver o que pode remover
            List<String> pagamentos = pagamentoController.listarPagamentos();
            if (pagamentos.isEmpty()) {
                System.out.println("Não há pagamentos registrados para remover.");
                return;
            }

            System.out.println("Pagamentos disponíveis:");
            pagamentos.forEach(System.out::println);

            System.out.print("ID do pagamento para remover: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            // Verificamos se o pagamento existe antes de tentar remover
            boolean encontrado = false;
            for (Pagamento pg : pagamentoController.getPagamentos()) {
                if (pg.getId() == id) {
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("❌ Pagamento com ID " + id + " não encontrado.");
                return;
            }

            String resultado = pagamentoController.removerPagamentoPorId(id);
            System.out.println(resultado);

            // Salva automaticamente após a remoção
            try {
                pagamentoController.salvar();
                System.out.println("✅ Lista de pagamentos atualizada e salva com sucesso!");
            } catch (Exception e) {
                System.err.println("[PagamentoView] Erro ao salvar após remoção no DAO: " + e.getMessage());
            }

            System.out.println("[PagamentoView] Remoção do pagamento ID " + id + " concluída");

        } catch (InputMismatchException e) {
            System.out.println("❌ Entrada inválida! Verifique o formato dos dados inseridos.");
            System.err.println("[PagamentoView] Entrada inválida em removerPagamento: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Argumento inválido: " + e.getMessage());
            System.err.println("[PagamentoView] Argumento inválido em removerPagamento: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Erro ao remover pagamento: " + e.getMessage());
            System.err.println("[PagamentoView] Exceção geral em removerPagamento: " + e.getMessage());
        }
    }
}
