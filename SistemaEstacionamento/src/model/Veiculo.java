package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Veiculo implements Serializable {

    private String placa;
    private String modelo;
    private String cor;
    private LocalDateTime dataHoraEntrada;
    int idVaga;

    public Veiculo(String placa, String modelo, String cor, LocalDateTime dataHoraEntrada) {
        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
        this.idVaga = 0;
        this.dataHoraEntrada = dataHoraEntrada;
    }

    public abstract double getValorPorHoras();

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public LocalDateTime getDataHoraEntrada() {
        return dataHoraEntrada;
    }

    public void setDataHoraEntrada(LocalDateTime dataHoraEntrada) {
        this.dataHoraEntrada = dataHoraEntrada;
    }

    public int getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(int idVaga) {
        this.idVaga = idVaga;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataHoraFormatada = dataHoraEntrada != null ? dataHoraEntrada.format(formatter) : "";
        return "Veiculo="
                + "\ncor:'" + cor + '\''
                + "\nplaca='" + placa + '\''
                + "\nmodelo='" + modelo + '\''
                + ", Data e Hora de Entrada = " + dataHoraFormatada;
    }
}
