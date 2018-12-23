package com.taotao.service.impl;

import java.io.InputStream;
import java.util.Map;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.taotao.common.util.IDUtils;
import com.taotao.common.util.SFTPUtil;
import com.taotao.service.PictureService;
import java.util.HashMap;

@Service
public class PictureServiceImpl implements PictureService{

	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWD}")
	private String FTP_PASSWD;
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;
	@Override
	public Map<Object, Object> uploadPicture(MultipartFile uploadFile) {
		Map<Object, Object> resultMap=new HashMap<>();
		//生成新文件名
		//取扩展名
		String oldName=uploadFile.getOriginalFilename();
		//生成新文件名
		//UUID.randomUUID();
		String newName=IDUtils.genImageName();
		newName=newName+oldName.substring(oldName.lastIndexOf("."));
		//图片上传
		SFTPUtil sftp = new SFTPUtil(FTP_USERNAME, FTP_PASSWD, FTP_ADDRESS, FTP_PORT);  
        sftp.login();  
        try {
        	String imagePath=new DateTime().toString("/yyyy/MM/dd");
	        InputStream is = uploadFile.getInputStream(); 
	        sftp.upload(FTP_BASE_PATH,imagePath,newName , is);
	        resultMap.put("error", 0);
	        resultMap.put("url",IMAGE_BASE_URL+imagePath+"/"+newName);
        }catch (Exception e) {
        	resultMap.put("error", 1);
	        resultMap.put("message","上传失败"+e.getMessage());
		}
        sftp.logout();
		return resultMap;
	}
	
}
