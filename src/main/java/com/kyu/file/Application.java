package com.kyu.file;

import com.kyu.file.core.FileExecutionMode;
import com.kyu.file.handler.FileOrganizer;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        String sourceDir = args[0];
        String targetRootDir = args[1];
        new FileOrganizer().execute(sourceDir, targetRootDir, FileExecutionMode.MOVE);
    }

}
