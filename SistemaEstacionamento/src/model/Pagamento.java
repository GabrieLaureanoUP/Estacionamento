package model;

import java.io.Serializable;

public class Pagamento implements Serializable {

    private int id;
    private Ticket ticket;
    private double valorPago;
    private String formaDePagamento;

    public Pagamento(int id, Ticket ticket, double valorPago, String formaDePagamento) {
        this.id = id;
        this.ticket = ticket;
        this.valorPago = valorPago;
        this.formaDePagamento = formaDePagamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public String getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(String formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pagamento{");
        sb.append("id=").append(id);
        sb.append(", ticket=").append(ticket);
        sb.append(", valorPago=").append(valorPago);
        sb.append(", formaDePagamento=").append(formaDePagamento);
        sb.append('}');
        return sb.toString();
    }

}
