package interfaces;

public interface Estacionavel {

    boolean estaDisponivel();

    boolean estaDisponivelParaMoto();

    void alterarDisponibilidade(boolean disponivel);

    void alterarDisponibilidadeMoto(boolean disponivel);
}
