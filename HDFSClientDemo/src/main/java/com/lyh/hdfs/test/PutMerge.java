package com.lyh.hdfs.test;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;


public class PutMerge {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		FileSystem hdfs = FileSystem.get(new URI("hdfs://hadoop101:9000"), conf, "lyh");
		//获取一个专门用于本地文件系统的文件系统local
		LocalFileSystem local = FileSystem.getLocal(conf);
		
		Path inputDir = new Path("D:\\dp\\test");
		//数组inputFiles的长度等于指定目录的文件个数
		FileStatus[] inputFiles = local.listStatus(inputDir);
		for (int i = 0; i < inputFiles.length; i++) {
			System.out.println(inputFiles[i].getPath().getName());
			FSDataInputStream in = null;
			FSDataOutputStream fos = null;
			try {
				in = local.open(inputFiles[i].getPath());
				fos = hdfs.create(new Path("/user/zhangsan/merge.file"));
				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = in.read(buffer))!= -1) {
					fos.write(buffer, 0, len);
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fos!=null && in != null) {
					fos.close();
					in.close();
				}
			}
			
		}
		
		
		
	}
}
