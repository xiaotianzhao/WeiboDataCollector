package com.weibo.zxt.op;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author 赵笑天
 *
 * @time 2016年1月26日
 * 
 */
public class FileOps {
	public static void writeToFile(String path,String content) throws Exception{
		
		File file = new File(path);
		
		if (! file.exists()){
			file.createNewFile();
		}
		
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		
		bw.close();
	}
}
