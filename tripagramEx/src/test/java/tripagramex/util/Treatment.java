package tripagramex.util;

import org.junit.jupiter.api.AfterAll;

import java.io.File;
import java.util.Arrays;

public class Treatment {

    @AfterAll
    static void deleteTestFolder() {

        File folder = new File("./testimage");
        File[] files = folder.listFiles();

        if (files == null) {
            return;
        }

        Arrays.stream(files)
                .forEach(File::delete);
    }
}
