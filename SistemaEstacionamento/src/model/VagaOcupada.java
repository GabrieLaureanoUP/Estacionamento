package model;

public class VagaOcupada {
    private Vaga vaga;
    private Veiculo veiculo;

    public VagaOcupada(Vaga vaga, Veiculo veiculo) {
        this.vaga = vaga;
        this.veiculo = veiculo;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public String toString() {
        return "VagaOcupada{" +
                "vaga=" + vaga +
                ", veiculo=" + veiculo +
                '}';
    }

}
