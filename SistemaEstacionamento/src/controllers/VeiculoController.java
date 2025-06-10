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
            System.err.println("[Controller] Erro inesperado ao carregar veículos na inicialização: " + e.getMessage());
            throw new Exception("Erro ao inicializar VeiculoController: " + e.getMessage(), e);
        }
    }

    public void criarCarro(String placa, String modelo, String cor, LocalDateTime dataHoraEntrada) throws Exception {
        try {
            veiculos.add(VeiculoFactory.criarCarro(placa, modelo, cor, dataHoraEntrada));
        } catch (Exception e) {
            System.err.println("[Controller] Erro ao criar carro com placa " + placa + ": " + e.getMessage());
            throw new Exception("Falha ao criar carro.", e);
        }
    }

    public void criarMoto(String placa, String modelo, String cor, LocalDateTime dataHoraEntrada) throws Exception {
        try {
            veiculos.add(VeiculoFactory.criarMoto(placa, modelo, cor, dataHoraEntrada));
        } catch (Exception e) {
            System.err.println("[Controller] Erro ao criar moto com placa " + placa + ": " + e.getMessage());
            throw new Exception("Falha ao criar moto.", e);
        }
    }

    public List<String> listarVeiculos() throws Exception {
        try {

            return veiculos.stream().map(Veiculo::toString).toList();
        } catch (Exception e) {
            System.err.println("[Controller] Erro ao listar veículos: " + e.getMessage());
            throw new Exception("Erro ao listar veículos: " + e.getMessage(), e);
        }
    }

    public List<Veiculo> getVeiculos() throws Exception {
        try {
            return veiculos;
        } catch (Exception e) {
            System.err.println("[Controller] Erro ao obter veículos: " + e.getMessage());
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
            System.err.println("[Controller] Erro ao buscar veículo por placa " + placa + ": " + e.getMessage());
            return null;
        }
    }

    public void removerVeiculo(String placa) throws Exception {
        try {
            Veiculo veiculo = buscarVeiculoPorPlaca(placa);
            if (veiculo != null) {
                veiculos.remove(veiculo);
            }
        } catch (Exception e) {
            System.err.println("[Controller] Erro ao remover veículo com placa " + placa + ": " + e.getMessage());
            throw new Exception("Erro ao remover veículo com placa " + placa + ": " + e.getMessage(), e);
        }
    }

    public Veiculo atualizarVeiculo(String placa, String modelo, String cor) {
        try {
            Veiculo veiculo = buscarVeiculoPorPlaca(placa);
            if (veiculo != null) {
                veiculo.setModelo(modelo);
                veiculo.setCor(cor);
            }
            return veiculo;
        } catch (Exception e) {
            System.err.println("[Controller] Erro ao atualizar veículo com placa " + placa + ": " + e.getMessage());
            return null;
        }
    }

    public void salvar() throws IOException, ClassNotFoundException {
        try {
            VeiculoDAO.salvar(veiculos);
        } catch (IOException e) {
            System.err.println("[Controller] Erro de I/O ao salvar veículos: " + e.getMessage());
            e.printStackTrace();
            throw e; // Relança a IOException para a camada que chamou (View ou Main)
        } catch (Exception e) { // Para qualquer outra exceção inesperada
            System.err.println("[Controller] Erro inesperado ao salvar veículos: " + e.getMessage());
            throw new IOException("Erro inesperado ao salvar dados.", e); // Envolve em IOException para consistência
        }
    }
}
