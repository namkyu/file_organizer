package com.kyu.file.handler;

import com.kyu.file.core.FileExecutionMode;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class FileOrganizerTest {


    @Test
    public void execute() throws Exception {
        String sourceDir = "E:\\test\\image";
        String destDir = "E:\\test\\image\\dest";
        boolean existsDestDir = new File(destDir).exists();

        if (existsDestDir) {
            Files.walk(Paths.get(destDir))
                    .map(Path::toFile)
                    .sorted((o1, o2) -> -o1.compareTo(o2))
                    .forEach(File::delete);
        }

        new FileOrganizer().execute(sourceDir, destDir, FileExecutionMode.COPY);
    }
    
    @Test
    public void LocalDateTime테스트() {
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(1505699012452L), ZoneId.systemDefault());
        System.out.println(date.getYear());
        System.out.println(date.getMonthValue());
        System.out.println(date.getDayOfMonth());
    }

    @Test
    public void 월표시() {
        System.out.println(String.format("%02d", 9));
        System.out.println(String.format("%02d", 12));
        System.out.println(String.format("%02d", 1));
    }

    @Test
    public void path테스트() throws IOException {
        Path path = Paths.get(System.getProperty("user.home"),"logs", "foo.log");
        System.out.println(path);

        Path result = Files.createDirectories(path.toAbsolutePath());
        System.out.println(result);
    }

    @Test
    public void format테스트() {
        String.format("%d", 93);
        System.out.printf("%d", 93);
    }

}