package com.backend.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.util.MD5Util;


public class test {
	

	public static void main(String[] args) throws IOException {

		File big = new File("E:\\jfinal_doc\\md5check\\西西软件下载.txt");
		String md5 = MD5Util.getFileMD5String(big);

		System.out.println("md5:" + md5.toUpperCase());
		
		

	}

}