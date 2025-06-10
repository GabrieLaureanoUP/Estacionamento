package model;

import controllers.VagaController;
import java.io.Serializable;
import java.util.List;

public class Estacionamento implements Serializable {

    private String nome;
    private int numeroDeVagas;
    private List<Vaga> vagas;
    private String endereco;
    private String telefone;
    private String email;
    private VagaController vagaController;

    public Estacionamento(String nome, String endereco, int numeroDeVagas, String telefone, String email, List<Vaga> vagas) {
        this.nome = nome;
        this.numeroDeVagas = numeroDeVagas;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.vagas = vagas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumeroDeVagas() {
        return numeroDeVagas;
    }

    public void setNumeroDeVagas(int numeroDeVagas) {
        this.numeroDeVagas = numeroDeVagas;
    }

    public List<Vaga> getVagas() {
        return vagas;
    }

    public VagaController getVagaController() {
        return vagaController;
    }

    @Override
    public String toString() {
        return "\nEstacionamento="
                + "\nemail:" + email
                + "\nnome:'" + nome
                + "\nnumeroDeVagas:" + numeroDeVagas
                + "\nendereco:" + endereco
                + "\ntelefone:" + telefone;
    }
}
