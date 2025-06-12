package util;

import java.io.File;

public class DadosUtil {

    private static final String CAMINHO_DADOS = "src/dados";

    public static void limparArquivosSerializados() {
        File diretorio = new File(CAMINHO_DADOS);

        // Criar o diretório se não existir
        if (!diretorio.exists()) {
            diretorio.mkdirs();
            System.out.println("DEBUG: Diretório de dados criado: " + CAMINHO_DADOS);
            return; // Se acabou de ser criado, não há nada para limpar
        }

        File[] arquivos = diretorio.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isFile() && arquivo.getName().endsWith(".ser")) {
                    boolean deletado = arquivo.delete();
                    System.out.println("DEBUG: Arquivo " + arquivo.getName() + (deletado ? " deletado." : " não pôde ser deletado."));
                }
            }
        }
    }
}
