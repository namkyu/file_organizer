package com.kyu.file.filter;

import java.io.File;
import java.io.FileFilter;


public class ImageFileFilter implements FileFilter {

    private final String[] okFileExtensions = new String[]{"jpg", "png", "gif", "bmp", "jpeg"};

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }

        String fileName = file.getName().toLowerCase();
        for (String extension : okFileExtensions) {
            if (fileName.endsWith(extension)) {
                return true;
            }
        }

        return false;
    }
}
