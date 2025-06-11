package controllers;

import factory.PagamentoFactory;
import model.Moto;
import model.Pagamento;
import model.Ticket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

import dal.PagamentoDAO;

public class PagamentoController {

    List<Pagamento> pagamentos;

    public PagamentoController() {
        pagamentos = new ArrayList<Pagamento>();
    }

    public String pagar(Ticket ticket, double valor, String formaDePagamento) throws Exception {
        int id = 1;
        if (!pagamentos.isEmpty()) {
            id = pagamentos.getLast().getId();
        }
        try {
            Pagamento pagamento = PagamentoFactory.criarPagamento(id, ticket, valor, formaDePagamento);
            pagamentos.add(pagamento);
            if (pagamento != null) {
                if (ticket.getVeiculo().getClass() == Moto.class) {
                    ticket.getVaga().alterarDisponibilidadeMoto(true);
                } else {
                    ticket.getVaga().alterarDisponibilidade(true);
                }
                return "pagamento criado com sucesso!";
            }
            return "pagamento nao criado!";
        } catch (IllegalFormatException e) {
            throw new Exception("Argumentos errados de pagamento" + e.getMessage());
        }

    }

    public String editarPagamentoPorID(int id) throws Exception {
        try {
            Pagamento pagamento = pagamentos.stream().filter(p -> p.getId() == id).findFirst().get();
            if (pagamento != null) {
                return "pagamento editado com sucesso!";
            }
            return "pagamento não encontrado";
        } catch (Exception e) {
            throw new Exception("pagamento não encontrado" + e.getMessage());
        }
    }

    public List<String> listarPagamentos() throws Exception {
        try {
            return pagamentos.stream().map(Pagamento::toString).toList();
        } catch (Exception e) {
            throw new Exception("Nenhum pagamento encontrado" + e.getMessage());
        }
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public String removerPagamentoPorId(int id) throws Exception {
        try {
            Pagamento pg = pagamentos.stream().filter(p -> p.getId() == id).findFirst().get();
            pagamentos.remove(pg);
            return "pagamento excluido com sucesso!";
        } catch (Exception e) {
            throw new Exception("Nenhum pagamento encontrado" + e.getMessage());
        }
    }

    public void setPagamentos(List<Pagamento> pagamentos) throws Exception {
        if (pagamentos == null || pagamentos.isEmpty()) {
            throw new Exception("Lista de pagamentos não pode ser nula ou vazia.");
        }
        this.pagamentos = pagamentos;
    }

    public void salvar() throws IOException, ClassNotFoundException {
        PagamentoDAO.salvar(pagamentos);
    }

    public List<Pagamento> carregar() throws IOException, ClassNotFoundException {
        pagamentos = PagamentoDAO.carregar();
        return pagamentos;
    }
}
