package controllers;

import dal.VeiculoDAO;
import factory.VeiculoFactory;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Veiculo;

public class VeiculoController {

    static List<Veiculo> veiculos;

    public VeiculoController() throws Exception {

        try {
            veiculos = new ArrayList<>();
        } catch (Exception e) {
            throw new Exception("Erro ao inicializar VeiculoController: " + e.getMessage(), e);
        }
    }

    public void criarCarro(String placa, String modelo, String cor, LocalDateTime dataHoraEntrada) throws Exception {
        try {
            veiculos.add(VeiculoFactory.criarCarro(placa, modelo, cor, dataHoraEntrada));
        } catch (Exception e) {
            throw new Exception("Falha ao criar carro.", e);
        }
    }

    public void criarMoto(String placa, String modelo, String cor, LocalDateTime dataHoraEntrada) throws Exception {
        try {
            veiculos.add(VeiculoFactory.criarMoto(placa, modelo, cor, dataHoraEntrada));
        } catch (Exception e) {
            throw new Exception("Falha ao criar moto.", e);
        }
    }

    public List<String> listarVeiculos() throws Exception {
        try {

            return veiculos.stream().map(Veiculo::toString).toList();
        } catch (Exception e) {
            throw new Exception("Erro ao listar veículos: " + e.getMessage(), e);
        }
    }

    public List<Veiculo> getVeiculos() throws Exception {
        try {
            return veiculos;
        } catch (Exception e) {
            throw new Exception("Erro ao obter veículos: " + e.getMessage(), e);
        }

    }

    public static Veiculo buscarVeiculoPorPlaca(String placa) throws Exception {
        try {
            for (Veiculo veiculo : veiculos) {
                if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                    return veiculo;
                }
            }
            return null;
        } catch (Exception e) {
            throw new Exception("Erro ao obter veículos: " + e.getMessage(), e);
        }
    }

    public void removerVeiculo(String placa) throws Exception {
        try {
            Veiculo veiculo = buscarVeiculoPorPlaca(placa);
            if (veiculo != null) {
                veiculos.remove(veiculo);
            }
        } catch (Exception e) {
            throw new Exception("Erro ao remover veículo com placa " + placa + ": " + e.getMessage(), e);
        }
    }

    public Veiculo atualizarVeiculo(String placa, String modelo, String cor) throws Exception {
        try {
            Veiculo veiculo = buscarVeiculoPorPlaca(placa);
            if (veiculo != null) {
                veiculo.setModelo(modelo);
                veiculo.setCor(cor);
                return veiculo;
            }
            return null;
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar veículo com placa " + placa + ": " + e.getMessage(), e);
        }
    }

    public void setVeiculos(List<Veiculo> veiculos) throws Exception {
        try {
            VeiculoController.veiculos = veiculos;
        } catch (Exception e) {
            throw new Exception("Erro ao definir veículos: " + e.getMessage(), e);
        }
    }

    public void salvar() throws IOException, ClassNotFoundException {
        try {
            VeiculoDAO.salvar(veiculos);
        } catch (IOException e) {
            throw e;
        } catch (Exception e) { // Para qualquer outra exceção inesperada
            throw new IOException("Erro inesperado ao salvar dados.", e); // Envolve em IOException para consistência
        }
    }

    public List<Veiculo> carregar() throws Exception {
        try {
            return VeiculoDAO.carregar();
        } catch (Exception e) {
            throw new Exception("Erro ao carregar veiculos.", e);
        }
    }
}
