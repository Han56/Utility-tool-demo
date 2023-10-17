package com.pdfbox.demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

public class PdfBoxTest {
    public static File mutiToOneFile(List<File> files,String targetPath) throws IOException {
        PDFMergerUtility mergerUtility = new PDFMergerUtility();
        for (File f:files){
            if (f.exists()&&f.isFile())
                mergerUtility.addSource(f);
        }
        mergerUtility.setDestinationFileName(targetPath);
        mergerUtility.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        return new File(targetPath);
    }


    public static void main(String[] args) throws IOException {
        List<File> files = new ArrayList<>();
        GetFileName getFileName = new GetFileName();
        List<String> allFilesPath = getFileName.filePathList("C:\\Users\\13352\\Desktop\\sonic-utilities代码对比");
        for (String str:allFilesPath)
            files.add(new File(str));
        File f = mutiToOneFile(files,"C:\\Users\\13352\\Desktop\\muti2One.pdf");
    }
}
