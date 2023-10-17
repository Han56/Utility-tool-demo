package com.pdfbox.demo;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class GetFileName {
    /*
     * @param:文件父目录路径 parentPath
     * */
    public List<String> filePathList(String parentPath){
        File f = new File(parentPath);
        List<String> returnList = new ArrayList<>();
        //过滤出后缀名为 .HFMED 的文件
        File[] filteredFiles = f.listFiles(new FilenameFilter(){

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".pdf");
            }
        });
        if (filteredFiles!=null){
            for (File file:filteredFiles)
                returnList.add(file.getAbsolutePath());
        }
        return returnList;
    }
}
