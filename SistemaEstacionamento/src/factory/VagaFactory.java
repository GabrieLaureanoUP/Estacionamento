package factory;

import enums.StatusVaga;
import model.Vaga;

public abstract class VagaFactory {

    public static Vaga criarVaga(int numero, StatusVaga status) throws Exception {
        if (numero <= 0) {
            throw new IllegalArgumentException("Número da vaga deve ser maior que zero.");
        }

        if (status == null) {
            throw new IllegalArgumentException("Status da vaga não pode ser nulo.");
        }

        try {
           return new Vaga(numero, status); 
        } catch (Exception e) {
            throw new Exception("Erro ao criar vaga: " + e.getMessage(), e);
        }
        
    }
}
