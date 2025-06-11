package dal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import model.VagaOcupada;


public class VagaOcupadaDAO {
    private static final String CAMINHO = "src/dados";

    public static void salvar(List<VagaOcupada> vagas) throws IOException {
        File diretorio = new File(CAMINHO);
        diretorio.mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO + "/vagasOcupadas.ser"))) {
            oos.writeObject(vagas);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<VagaOcupada> carregar() throws IOException, ClassNotFoundException {

        File arquivo = new File(CAMINHO + "/vagasOcupadas.ser");
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<VagaOcupada>) ois.readObject();
        }
    }
}
