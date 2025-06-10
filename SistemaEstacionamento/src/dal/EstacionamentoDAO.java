package dal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Estacionamento;

public class EstacionamentoDAO {

    private static final String CAMINHO = "src/dados";

    public static void salvar(List<Estacionamento> estacionamentos) throws IOException {
        File diretorio = new File(CAMINHO);
        diretorio.mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO + "/estacionamentos.ser"))) {
            oos.writeObject(estacionamentos);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Estacionamento> carregar() throws IOException, ClassNotFoundException {

        File arquivo = new File(CAMINHO + "/estacionamentos.ser");
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Estacionamento>) ois.readObject();
        }
    }
}
