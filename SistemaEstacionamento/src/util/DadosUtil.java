package util;

import java.io.File;

public class DadosUtil {

    private static final String CAMINHO_DADOS = "src/dados";

    public static void limparArquivosSerializados() {
        File diretorio = new File(CAMINHO_DADOS);

        if (!diretorio.exists()) {
            diretorio.mkdirs();
            return;
        }

        File[] arquivos = diretorio.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isFile() && arquivo.getName().endsWith(".ser")) {
                    boolean deletado = arquivo.delete();
                }
            }
        }
    }
}
