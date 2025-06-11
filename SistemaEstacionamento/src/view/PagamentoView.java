package view;

import controllers.PagamentoController;
import controllers.TicketController;
import dal.PagamentoDAO;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import model.Pagamento;
import model.Ticket;

public class PagamentoView {

    private final PagamentoController pagamentoController;
    private final TicketController ticketController;
    private final Scanner scanner;

    public PagamentoView(PagamentoController pagamentoController, TicketController ticketController) {
        this.pagamentoController = pagamentoController;
        this.ticketController = ticketController;
        this.scanner = new Scanner(System.in);
    }

    public void menuPagamento() {
        int opcao;
        List<Pagamento> lista = new ArrayList<>();
        try {
            lista = pagamentoController.carregar();
        } catch (Exception e) {
            System.err.println("Erro ao carregar a lista de pagamentos: " + e.getMessage());
        }

        try {
            pagamentoController.setPagamentos(lista);
        } catch (Exception e) {
            System.err.println("Erro ao definir pagamentos: " + e.getMessage());
        }
        do {
            exibirMenuPagamento();
            opcao = lerOpcao();

            try {
                executarOpcao(opcao);
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                System.err.println("Erro ao executar opção " + opcao + ": " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private void exibirMenuPagamento() {
        System.out.println("\n=== Menu de Pagamentos ===");
        System.out.println("1. Realizar Pagamento");
        System.out.println("2. Listar Pagamentos");
        System.out.println("3. Editar Pagamento");
        System.out.println("4. Remover Pagamento");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");
    }

    private int lerOpcao() {
        int opcao;
        try {
            opcao = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida! Por favor, digite um número para a opção.");
            System.err.println("Erro de entrada de usuário em lerOpcao: " + e.getMessage());
            opcao = -1;
        } finally {
            scanner.nextLine();
        }
        return opcao;
    }

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
                    pagamentoController.salvar();
                } catch (Exception e) {
                    System.err.println("Erro ao salvar lista de pagamentos: " + e.getMessage());
                } finally {
                    System.out.println("Voltando ao menu principal...");
                }
            }
            default ->
                System.out.println("Opção inválida! Escolha um número entre 0 e 4.");
        }
    }

    private void realizarPagamento() {
        try {
            System.out.println("\n=== Realizar Pagamento ===");
            System.out.print("ID do Ticket: ");
            int ticketId = scanner.nextInt();// Limpar buffer

            Ticket ticket = ticketController.getTicketById(ticketId);
            if (ticket == null) {
                System.out.println("Ticket não encontrado!");
                return;
            }

            System.out.println("Ticket encontrado: " + ticket.toString());
            System.out.print("Valor a pagar: ");
            double valor = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Forma de pagamento (DINHEIRO, CARTAO_CREDITO, CARTAO_DEBITO, PIX): ");
            String formaPagamento = scanner.nextLine().toUpperCase();

            String resultado = pagamentoController.pagar(ticket, valor, formaPagamento);
            System.out.println(resultado);
            try {
                pagamentoController.salvar();
                System.out.println("Pagamento salvo com sucesso!");
            } catch (Exception e) {
                System.err.println("Erro ao salvar pagamento: " + e.getMessage());
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida! Verifique o formato dos dados inseridos.");
        } catch (IllegalArgumentException e) {
            System.out.println("Argumento inválido: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao realizar pagamento: " + e.getMessage());
        }
    }

    private void listarPagamentos() throws Exception {
        System.out.println("\n=== Lista de Pagamentos ===");
        List<String> pagamentos = pagamentoController.listarPagamentos();

        if (pagamentos.isEmpty()) {
            System.out.println("Não há pagamentos registrados.");
            return;
        }

        System.out.println("ID | Ticket | Valor | Forma de Pagamento");
        System.out.println("----------------------------------------");
        pagamentos.forEach(System.out::println);
    }

    private void editarPagamento() throws Exception {
        System.out.println("\n=== Editar Pagamento ===");

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

        boolean encontrado = false;
        for (Pagamento pg : pagamentoController.getPagamentos()) {
            if (pg.getId() == id) {
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Pagamento com ID " + id + " não encontrado!");
            return;
        }

        String resultado = pagamentoController.editarPagamentoPorID(id);
        System.out.println(resultado);

        try {
            pagamentoController.salvar();
            System.out.println("Alterações salvas com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao salvar edição: " + e.getMessage());
        }
    }

    private void removerPagamento() throws Exception {
        System.out.println("\n=== Remover Pagamento ===");

        List<String> pagamentos = pagamentoController.listarPagamentos();
        if (pagamentos.isEmpty()) {
            System.out.println("Não há pagamentos registrados para remover.");
            return;
        }

        System.out.println("Pagamentos disponíveis:");
        pagamentos.forEach(System.out::println);

        System.out.print("ID do pagamento para remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        boolean encontrado = false;
        for (Pagamento pg : pagamentoController.getPagamentos()) {
            if (pg.getId() == id) {
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Pagamento com ID " + id + " não encontrado!");
            return;
        }

        String resultado = pagamentoController.removerPagamentoPorId(id);
        System.out.println(resultado);

        try {
            pagamentoController.salvar();
            System.out.println("Lista de pagamentos atualizada e salva com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao salvar após remoção: " + e.getMessage());
        }
    }
}
