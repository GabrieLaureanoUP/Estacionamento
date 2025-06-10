package dal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Pagamento;

public class PagamentoDAO {

    private static final String CAMINHO = "SistemaEstacionamento/src/dados";

    public static void salvar(List<Pagamento> tickets) throws IOException {
        File diretorio = new File(CAMINHO);
        diretorio.mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO + "/pagamentos.ser"))) {
            oos.writeObject(tickets);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Pagamento> carregar() throws IOException, ClassNotFoundException {

        File arquivo = new File(CAMINHO + "/pagamentos.ser");
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Pagamento>) ois.readObject();
        }
    }
}
