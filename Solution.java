package com.javarush.task.task31.task3101;


import java.io.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
Проход по дереву файлов

пример строки параметров:    e:/txt/ e:/a.txt

*/
public class Solution {
    public static void main(String[] args){
        File path=new File(args[0]);
        ArrayList<File>result;
        result=vozvratFile(path);
        Collections.sort(result, new MyComp()); //new MyComp());
        File resultFileAbsolutePath=new File(args[1]);
        File allFilesContent=new File(resultFileAbsolutePath.getParent()+"/allFilesContent.txt");
        FileUtils.renameFile(resultFileAbsolutePath,allFilesContent);
        try(FileOutputStream fileOutputStream=new FileOutputStream(allFilesContent))
        {
            for (File file:result)
            {
                try(FileInputStream inputStream=new FileInputStream(file))
                {
                    while(inputStream.available()>0)
                    {
                        fileOutputStream.write(inputStream.read());
                    }
                    byte[]bytes=System.lineSeparator().getBytes();
                    fileOutputStream.write(bytes);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static ArrayList<File> vozvratFile(File path)
    {
        ArrayList<File>result=new ArrayList<>();
        for (File file:path.listFiles())
        {
            if (file.isFile()&&file.length()<=50)
            {
                result.add(file);
            }
            if (file.isDirectory())
            {
                ArrayList<File>result1=vozvratFile(file);
                result.addAll(result1);
            }
        }
        return result;
    }

    static class MyComp implements Comparator<File>
    {
        @Override
        public int compare(File o1, File o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
