package controllers;

import factory.PagamentoFactory;
import model.Moto;
import model.Pagamento;
import model.Ticket;
import model.Vaga;

import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

import dal.PagamentoDAO;
import dal.VagaDAO;

public class PagamentoController {

    List<Pagamento> pagamentos;
    private VagaOcupadaController vagaOcupadaController;

    public PagamentoController() {
        pagamentos = new ArrayList<Pagamento>();
        this.vagaOcupadaController = null;
    }

    public PagamentoController(VagaOcupadaController vagaOcupadaController) {
        pagamentos = new ArrayList<Pagamento>();
        this.vagaOcupadaController = vagaOcupadaController;
    }

    public void setVagaOcupadaController(VagaOcupadaController vagaOcupadaController) {
        this.vagaOcupadaController = vagaOcupadaController;
    }

    public String pagar(Ticket ticket, double valor, String formaDePagamento) throws Exception {
        int id = 1;
        if (!pagamentos.isEmpty()) {
            id = pagamentos.getLast().getId() + 1;
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

                ticket.getVeiculo().setIdVaga(0);

                if (this.vagaOcupadaController != null) {
                    this.vagaOcupadaController.removerVagaOcupada(ticket.getVaga().getNumero());
                    try {
                        this.vagaOcupadaController.salvar();
                    } catch (IOException e) {
                        System.err.println("Erro ao salvar vagas ocupadas: " + e.getMessage());
                    }
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
        try {
            PagamentoDAO.salvar(pagamentos);
        } catch (IOException e) {
            throw new IOException("Erro ao salvar pagamentos: " + e.getMessage(), e);
        }
    }

    public List<Pagamento> carregar() throws Exception {
        try {
            return PagamentoDAO.carregar();
        } catch (Exception e) {
            throw new Exception("Erro ao carregar vagas.", e);
        }
    }
}
