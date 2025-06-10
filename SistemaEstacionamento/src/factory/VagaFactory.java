package factory;

import enums.StatusVaga;
import model.Vaga;

public abstract class VagaFactory {

    public static Vaga criarVaga(int numero, StatusVaga status) {
        return new Vaga(numero, status);
    }
}
