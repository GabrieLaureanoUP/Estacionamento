package factory;

import model.Pagamento;
import model.Ticket;

public class PagamentoFactory {

    public static Pagamento criarPagamento(int id, Ticket ticket, double valor, String formaPagamento) throws Exception {

        if (ticket == null) {
            throw new IllegalArgumentException("Ticket não pode ser nulo");
        }

        if (valor <= 0) {
            throw new IllegalArgumentException("Valor do pagamento deve ser maior que zero.");
        }

        if (formaPagamento == null || formaPagamento.isEmpty()) {
            throw new IllegalArgumentException("Forma de pagamento não pode ser vazia.");
        }

        if (!formaPagamento.equalsIgnoreCase("dinheiro")
                && !formaPagamento.equalsIgnoreCase("credito")
                && !formaPagamento.equalsIgnoreCase("debito")
                && !formaPagamento.equalsIgnoreCase("pix")) {
            throw new IllegalArgumentException("Forma de pagamento inválida. Deve ser 'dinheiro', 'cartão de crédito' ou 'cartão de débito'.");
        }

        try {
            Pagamento pagamento = new Pagamento(id, ticket, valor, formaPagamento);
            return pagamento;
        } catch (Exception e) {
            throw new Exception("Erro ao criar pagamento " + e.getMessage());
        }
    }
}
