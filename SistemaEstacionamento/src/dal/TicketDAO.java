package dal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Ticket;

public class TicketDAO {

    private static final String CAMINHO = "src/dados";

    public static void salvar(List<Ticket> tickets) throws IOException {
        File diretorio = new File(CAMINHO);
        diretorio.mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO + "/tickets.ser"))) {
            oos.writeObject(tickets);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Ticket> carregar() throws IOException, ClassNotFoundException {

        File arquivo = new File(CAMINHO + "/tickets.ser");
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Ticket>) ois.readObject();
        }
    }
}
