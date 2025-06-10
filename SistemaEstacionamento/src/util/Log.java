package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Log {

    public static void setError() {
        try {
            File logDir = new File("logs");
            logDir.mkdir();

            PrintStream logErro = new PrintStream(new FileOutputStream("logs/erro.txt", true)) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyys HH:mm:ss");

                @Override
                public void println(String x) {
                    super.println("[" + LocalDateTime.now().format(dtf) + "] " + x);
                }
            };

            System.setErr(logErro);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
