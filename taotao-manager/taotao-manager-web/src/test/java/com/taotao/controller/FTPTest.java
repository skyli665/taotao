package com.taotao.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.junit.Test;
import com.taotao.common.util.SFTPUtil;

public class FTPTest {
	public void testFtpClient() throws Exception {
		SFTPUtil sftp = new SFTPUtil("root", "14171B", "192.168.0.53", 22);  
        sftp.login();  
        File file = new File("D:\\aaa.jpg");  
        InputStream is = new FileInputStream(file);  
          
        sftp.upload("/usr/local/nginx/html","image", "test_sftp.jpg", is);  
        sftp.logout(); 
	}
}
