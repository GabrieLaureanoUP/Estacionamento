package model;

import enums.StatusVaga;
import interfaces.Estacionavel;
import java.io.Serializable;

;

public class Vaga implements Estacionavel, Serializable {

    private int numero;
    private StatusVaga status;

    public Vaga(int numero, StatusVaga status) {
        this.numero = numero;
        this.status = status;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public StatusVaga getStatus() {
        return status;
    }

    public void setStatus(StatusVaga status) {
        this.status = status;
    }

    @Override
    public boolean estaDisponivel() {
        return status == StatusVaga.LIVRE;
    }

    @Override
    public boolean estaDisponivelParaMoto() {
        return status == StatusVaga.LIVRE || status == StatusVaga.LIVREMOTO;
    }

    @Override
    public void alterarDisponibilidade(boolean disponivel) {
        this.status = disponivel ? StatusVaga.LIVRE : StatusVaga.OCUPADA;
    }

    @Override
    public void alterarDisponibilidadeMoto(boolean disponivel) {
        if (disponivel) {
            if (this.status.equals(StatusVaga.OCUPADA)) {
                this.status = StatusVaga.LIVREMOTO;
            } else {
                this.status = StatusVaga.LIVRE;
            }
        } else {
            if (this.status.equals(StatusVaga.LIVRE)) {
                this.status = StatusVaga.LIVREMOTO;
            } else {
                this.status = StatusVaga.OCUPADA;
            }
        }
    }

    @Override
    public String toString() {
        return "Vaga [numero=" + numero + ", status=" + status + "]";
    }

}
