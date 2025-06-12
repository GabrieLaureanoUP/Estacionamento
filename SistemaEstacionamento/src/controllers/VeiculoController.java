package controllers;

import dal.VeiculoDAO;
import factory.VeiculoFactory;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Veiculo;

public class VeiculoController {

    private List<Veiculo> veiculos;

    public VeiculoController() throws Exception {
        veiculos = new ArrayList<>();
    }

    public void criarCarro(String placa, String modelo, String cor, LocalDateTime dataHoraEntrada) throws Exception {
        try {
            Veiculo existente = buscarVeiculoPorPlaca(placa);
            if (existente != null) {
                existente.setModelo(modelo);
                existente.setCor(cor);
                existente.setDataHoraEntrada(dataHoraEntrada);
                return;
            }

            veiculos.add(VeiculoFactory.criarCarro(placa, modelo, cor, dataHoraEntrada));
        } catch (Exception e) {
            throw new Exception("Falha ao criar carro.", e);
        }
    }

    public void criarMoto(String placa, String modelo, String cor, LocalDateTime dataHoraEntrada) throws Exception {
        try {
            Veiculo existente = buscarVeiculoPorPlaca(placa);
            if (existente != null) {
                existente.setModelo(modelo);
                existente.setCor(cor);
                existente.setDataHoraEntrada(dataHoraEntrada);
                return;
            }

            veiculos.add(VeiculoFactory.criarMoto(placa, modelo, cor, dataHoraEntrada));
        } catch (Exception e) {
            throw new Exception("Falha ao criar moto.", e);
        }
    }

    public List<Veiculo> listarVeiculos() throws Exception {
        try {
            return new ArrayList<>(veiculos);
        } catch (Exception e) {
            throw new Exception("Erro ao listar veículos: " + e.getMessage(), e);
        }
    }

    public List<Veiculo> getVeiculos() throws Exception {
        return veiculos;
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) throws Exception {
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                return veiculo;
            }
        }
        return null;
    }

    public void removerVeiculo(String placa) throws Exception {
        Veiculo veiculo = buscarVeiculoPorPlaca(placa);
        if (veiculo != null) {
            veiculos.remove(veiculo);
        }
    }

    public Veiculo atualizarVeiculo(String placa, String modelo, String cor) throws Exception {
        Veiculo veiculo = buscarVeiculoPorPlaca(placa);
        if (veiculo != null) {
            veiculo.setModelo(modelo);
            veiculo.setCor(cor);
            return veiculo;
        }
        return null;
    }

    public void setVeiculos(List<Veiculo> veiculos) throws Exception {
        this.veiculos = veiculos;
    }

    public void salvar() throws IOException {
        try {
            VeiculoDAO.salvar(veiculos);
        } catch (IOException e) {
            throw new IOException("Erro ao salvar veículos: " + e.getMessage(), e);
        }
    }

    public List<Veiculo> carregar() throws Exception {
        try {
            return VeiculoDAO.carregar();
        } catch (Exception e) {
            throw new Exception("Erro ao carregar veículos: " + e.getMessage(), e);
        }
    }
}
