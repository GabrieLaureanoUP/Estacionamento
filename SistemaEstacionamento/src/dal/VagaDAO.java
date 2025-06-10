package dal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Vaga;

public class VagaDAO {

    private static final String CAMINHO = "src/dados";

    public static void salvar(List<Vaga> vagas) throws IOException {
        File diretorio = new File(CAMINHO);
        diretorio.mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO + "/vagas.ser"))) {
            oos.writeObject(vagas);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Vaga> carregar() throws IOException, ClassNotFoundException {

        File arquivo = new File(CAMINHO + "/vagas.ser");
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Vaga>) ois.readObject();
        }
    }
}
