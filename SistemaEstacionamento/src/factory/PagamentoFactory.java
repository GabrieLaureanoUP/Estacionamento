package factory;

import model.Pagamento;
import model.Ticket;

public class PagamentoFactory {

    public static Pagamento criarPagamento(int id, Ticket ticket, double valor, String formaPagamento) throws Exception {
        try {
            Pagamento pagamento = new Pagamento(id, ticket, valor, formaPagamento);
            return pagamento;
        } catch (Exception e) {
            throw new Exception("Erro ao criar pagamento " + e.getMessage());
        }
    }
}
