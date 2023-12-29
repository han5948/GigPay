<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="java.io.*"%>
<%@page import="java.util.UUID"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Properties" %>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>


<%@page import="org.apache.ibatis.io.Resources" %>
<%@page import="com.nemo.kr.util.FileCoder" %>
<%@page import="com.nemo.kr.dto.AdminDTO" %>

<%	
	try{
	 	Properties commonProperties = new Properties();
		Properties dbProperties = new Properties();
		
		String act = System.getProperty("spring.profiles.active");
		Reader reader = Resources.getResourceAsReader("properties/" + act + "/common-config.properties");
		commonProperties.load(reader);
		
		String imageUploadPath = commonProperties.getProperty("imageUploadPath");
		String fileSecretKeyPath = commonProperties.getProperty("fileSecretKeyPath");
		
// 		Reader dbReader = Resources.getResourceAsReader("properties/" + act + "/jdbc.properties");
// 		dbProperties.load(dbReader);
		
// 		String dbDriver = dbProperties.getProperty("jdbc.Driver");
// 		String dbUrl = dbProperties.getProperty("jdbc.ConnectionURL");
// 		String dbUserName = dbProperties.getProperty("jdbc.Username");
// 		String dbPass = dbProperties.getProperty("jdbc.Password");
// 		AdminDTO adminDTO = (AdminDTO)session.getAttribute("ADMIN_SESSION");
		
	    //파일정보
	    String sFileInfo = "";
	    //파일명을 받는다 - 일반 원본파일명
	    String filename = request.getHeader("file-name");
	    //파일 확장자
	    String filename_ext = filename.substring(filename.lastIndexOf(".") + 1);
	    //확장자를소문자로 변경
	    filename_ext = filename_ext.toLowerCase();
	    
	    //이미지 검증 배열변수
	    String[] allow_file = { "jpg", "png", "bmp", "gif" };
	 	
	    //돌리면서 확장자가 이미지인지 
	    int cnt = 0;
	    for (int i = 0; i < allow_file.length; i++) {
	        if (filename_ext.equals(allow_file[i])) {
	            cnt++;
	        }
	    }
	 
	    //이미지가 아님
	    if (cnt == 0) {
	        out.println("NOTALLOW_" + filename);
	    } else {
	        //이미지이므로 신규 파일로 디렉토리 설정 및 업로드   
	        //파일 기본경로
	        String dftFilePath = request.getSession().getServletContext().getRealPath("/");
	        //파일 기본경로 _ 상세경로
	        File file = new File(imageUploadPath);
	        if (!file.exists()) {
	            file.mkdirs(); 
	        }
	        String realFileNm = "";
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	        String today = formatter.format(new java.util.Date());
	        realFileNm = today + "_" + UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
	        ///////////////// 서버에 파일쓰기 ///////////////// 
	        InputStream is = request.getInputStream();
	        OutputStream os = new FileOutputStream(imageUploadPath + realFileNm);
	        int numRead;
	        byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
	        while ((numRead = is.read(b, 0, b.length)) != -1) {
	            os.write(b, 0, numRead);
	        }
	        if (is != null) {
	            is.close();
	        }
	        os.flush();
	        os.close();
	        ///////////////// 서버에 파일쓰기 /////////////////
	        // dbconnection
// 			Class.forName(dbDriver);
// 			Connection conn = DriverManager.getConnection(dbUrl, dbUserName, dbPass);
			
// 			Statement stat = conn.createStatement();
// 			String insertQuery = "INSERT INTO file_info "
// 					+ "( file_name, file_org_name, file_path, file_url,"
// 					+ "service_type, service_code, service_seq, file_order, file_size,"
// 					+ "file_ext, reg_date, reg_admin, mod_admin )"
// 					+ "VALUES ( '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', now(), '%s', '%s' )";
// 			insertQuery = String.format(insertQuery, realFileNm, filename, imageUploadPath, "admin/"+realFileNm,
// 					"ADMIN", "BOARDIMAGE", adminDTO.getAdmin_seq(), "0", request.getHeader("file-size"),
// 					filename_ext, adminDTO.getAdmin_id(), adminDTO.getAdmin_id());
			
// 			System.out.println("file_info insert query ===> " + insertQuery);
// 			int count = stat.executeUpdate(insertQuery);
// 			if( count > 0 ){
// 				System.out.println("file_info query success");
// 			}
// 			stat.close();
// 			conn.close();
	 
	        // 정보 출력
	        sFileInfo += "&bNewLine=true";
	        sFileInfo += "&sFileName=" + filename;
	        sFileInfo += "&sFileURL=/admin/imgLoad?path;"+imageUploadPath+"#name;"+realFileNm;
	        out.println(sFileInfo);
	    }
	}catch(Exception e){
		e.printStackTrace();
	}
%>
