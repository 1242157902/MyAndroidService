<%    
	String path =request.getSession().getServletContext().getRealPath("/")+"help/";//
    String filename = request.getParameter("filename");  
    //String filepath = request.getParameter("filepath");//"d:\\";    
    int i = 0;    
    response.setContentType("application/octet-stream");    
    response.setHeader("Content-Disposition","attachment;filename ="+filename);   
    java.io.FileInputStream fileInputStream = new java.io.FileInputStream(path+filename);    
    while((i= fileInputStream.read()) != -1){    
        out.write(i);    
    }   
    fileInputStream.close();
    out.close();  
%>   