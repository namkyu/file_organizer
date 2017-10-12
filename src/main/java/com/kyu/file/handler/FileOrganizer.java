package com.kyu.file.handler;

import com.kyu.file.core.FileExecutionMode;
import com.kyu.file.filter.ImageFileFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class FileOrganizer {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 실행
     * @param sourceDir
     * @param destDir
     * @throws IOException
     */
    public void execute(String sourceDir, String targetRootDir, FileExecutionMode mode) throws IOException {
        List<File> fileList = new ArrayList<>();
        fileList = recursiveFile(fileList, new File(sourceDir));
        System.out.printf("대상 파일 리스트 : %s\n", fileList.size());

        for (File file : fileList) {
            Path sourceFilePath = Paths.get(file.getAbsolutePath());
            Path targetPath = makeDir(targetRootDir, file);
            System.out.printf("sourceFilePath : %s, targetPath : %s\n", sourceFilePath, targetPath);

            String targetFileName = renameFile(file);
            if (FileExecutionMode.COPY == mode) {
                Files.copy(sourceFilePath, targetPath.resolve(targetFileName));
            } else if (FileExecutionMode.MOVE == mode) {
                Files.move(sourceFilePath, targetPath.resolve(targetFileName));
            }
        }
    }

    /**
     * 디렉토리 생성
     * @param targetRootDir
     * @param file
     * @return
     * @throws IOException
     */
    private Path makeDir(String targetRootDir, File file) throws IOException {
        long lastModified = file.lastModified(); // 수정일자
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(lastModified), ZoneId.systemDefault());
        String year = String.valueOf(localDateTime.getYear());
        String month = String.format("%02d", localDateTime.getMonthValue());

        Path targetPathDir = Paths.get(targetRootDir, year, month);
        return Files.createDirectories(targetPathDir);
    }

    /**
     * 대상 파일 리스트
     * @param fileList
     * @param file
     * @return
     */
    private List<File> recursiveFile(List<File> fileList, File file) {
        if (file.isFile()) {
            fileList.add(file);
            return fileList;
        }

        File[] fileArr = file.listFiles(new ImageFileFilter());
        for (File childFile : fileArr) {
            recursiveFile(fileList, childFile);
        }

        return fileList;
    }


    /**
     * 이미지 파일명 변경
     * @param file
     * @return
     */
    private String renameFile(File file) {
        String lastModified = sdf.format(file.lastModified());
        String randomDigit = String.format("%04d", new Random().nextInt(10000));
        String fileExtension = getFileExtension(file);

        StringBuilder builder = new StringBuilder();
        builder.append(lastModified);
        builder.append("-");
        builder.append(randomDigit);
        builder.append(".");
        builder.append(fileExtension);
        return builder.toString();
    }

    /**
     * 파일 확장자 제거
     * @param str
     * @return
     */
    private String stripExtension(String str) {
        if (str == null) {
            return null;
        }

        int pos = str.lastIndexOf(".");
        if (pos == -1) {
            return str;
        }

        return str.substring(0, pos);
    }

    /**
     * 파일 확장자 추출
     * @param file
     * @return
     */
    private String getFileExtension(File file) {
        String name = file.getName();
        return name.substring(name.lastIndexOf(".") + 1);
    }
}

