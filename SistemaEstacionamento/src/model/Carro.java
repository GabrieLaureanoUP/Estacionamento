package model;

import java.time.LocalDateTime;

public class Carro extends Veiculo {

    private static final double VALOR_HORA = 10.0;

    public Carro(String placa, String modelo, String cor, LocalDateTime dataHoraEntrada) {
        super(placa, modelo, cor, dataHoraEntrada);
    }

    @Override
    public double getValorPorHoras() {
        return VALOR_HORA;
    }

    @Override
    public String toString() {
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataHoraFormatada = getDataHoraEntrada() != null ? getDataHoraEntrada().format(formatter) : "";
        return "Carro{"
                + "placa='" + getPlaca() + '\''
                + ", modelo='" + getModelo() + '\''
                + ", cor='" + getCor() + '\''
                + ", Data e Hora de Entrada = " + dataHoraFormatada
                + '}';
    }
}
