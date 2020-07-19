package com.eversec.fastdfs_java.my;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.User;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public FileResponseData add(
			//BindingResult br,MultipartFile attach, HttpServletRequest request
			)
	        throws IOException, MyException {
		FileResponseData response = new FileResponseData();
		File filelocal = new File("d:/sms6.txt");
    	MultipartFile mulFile = new MockMultipartFile(
    	"sms6.txt",
    	"sms6.txt",
//    	ContentType.APPLICATION_OCTET_STREAM.toString(),
    	null,
    	new FileInputStream(filelocal)
    	);
		
	
	    // 获取文件后缀名 
	    String ext = mulFile.getOriginalFilename().substring(mulFile.getOriginalFilename().lastIndexOf(".")+1);
	    FastDFSFile file = new FastDFSFile(mulFile.getBytes(),ext);
	    NameValuePair[] meta_list = new NameValuePair[4];
	    meta_list[0] = new NameValuePair("fileName", mulFile.getOriginalFilename());
	    meta_list[1] = new NameValuePair("fileLength", String.valueOf(mulFile.getSize()));
	    meta_list[2] = new NameValuePair("fileExt", ext);
	    meta_list[3] = new NameValuePair("fileAuthor", "WangLiang");
	    String filePath = FileManager.upload(file,meta_list);
//	    user.setFilePath(filePath);
//	    users.put(user.getUsername(), user);
	    response.setCode("200");
	    response.setFilePath(filePath);
	    response.setFileType(ext);
	    response.setSuccess(true);
	    return response;
	}
	
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
//	public ResponseEntity<byte[]> download(@PathVariable String username, Model model,HttpServletResponse response) throws IOException, MyException {
		public ResponseEntity<byte[]> download() throws IOException, MyException {
//	    User u = users.get(username);
//	    String filePath = u.getFilePath();
		String filePath = "http://192.168.17.50/group1/M00/00/00/wKgRMlw-7T2ASZj7AAAAFNyj2Ms910.txt";
	    String substr = filePath.substring(filePath.indexOf("group"));
	    String group = substr.split("/")[0];
	    String remoteFileName = substr.substring(substr.indexOf("/")+1);
//	    String specFileName = username + substr.substring(substr.indexOf("."));
	    return FileManager.download(group, remoteFileName,null);
//			return null;
	}
	
	
	
}
