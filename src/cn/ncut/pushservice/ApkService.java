package cn.ncut.pushservice;
import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;
import java.io.FileOutputStream;  
import java.io.IOException;
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.text.DecimalFormat;
import java.util.zip.ZipEntry;  
import java.util.zip.ZipInputStream;  


public class ApkService { 
	
	public static String GetApkIcon(String aaptPath,String apkPath,String outDir,String iconName) {
		 try{
			 String apkIconName=getApkIconName(aaptPath,apkPath);  
		     getIcon(apkPath, apkIconName, outDir+"\\"+iconName);  
		     if(new File(outDir+"\\"+iconName).exists()) return iconName;
		     else return "default.png";
		 } catch (Exception e) {  
		     e.printStackTrace();  
		     return "default.png";
		 }  
	}
	  
	   
    public static String getApkIconName(String aaptPath,String apkPath) { 
        String iconName = "";  
        try {  	         
            Runtime rt = Runtime.getRuntime();  
            String order = aaptPath  + " d badging \"" + apkPath  + "\"";
            Process proc = rt.exec(order);  
            InputStream inputStream = proc.getInputStream();  
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));  
            String line = null;  
            while((line = bufferedReader.readLine()) != null){  
                if(line.contains("application:")){//application: label='UC浏览器' icon='res/drawable-hdpi/icon.png'   
                    iconName = line.substring(line.lastIndexOf("/") + 1, line.lastIndexOf("'")).trim().toLowerCase();  
                    break;  
                }  
            }  
        } catch (Throwable e) {  
            e.printStackTrace();  
        }  
        return iconName;  
    }  
	  
    public static void getIcon(String apkPath, String iconName, String outPath) throws Exception {  
        FileInputStream fis = null;  
        FileOutputStream fos = null;  
        ZipInputStream zis = null;  
        File apkFile = new File(apkPath);  
        fis = new FileInputStream(apkFile);  
        zis = new ZipInputStream(fis);  
        ZipEntry zipEntry = null;  
        while((zipEntry = zis.getNextEntry()) != null){  
            String name = zipEntry.getName().toLowerCase();  
            if((name.endsWith("/" + iconName) && name.contains("drawable") && name.contains("res")) ||   
                    (name.endsWith("/" + iconName) && name.contains("raw") && name.contains("res"))){  
                fos = new FileOutputStream(new File(outPath));  
                byte[] buffer = new byte[1024];  
                int n = 0;  
                while((n = zis.read(buffer, 0, buffer.length)) != -1){  
                    fos.write(buffer, 0, n);  
                }  
                break;  
            }  
        }  
        fis.close();  
        zis.closeEntry();  
        zis.close();  
        fos.close();
    }  
    
    public static String  formatSize(int size)
    {
    	int KB=1024;
    	int MB=1024*1024;
    	int GB=1024*1024*1024;
    	DecimalFormat df = new DecimalFormat("0.00");//格式化小数
    	if(size>=GB) return  df.format((float)size/GB)+"GB";
    	else if(size>=MB) return  df.format((float)size/MB)+"MB";
    	else if(size>=KB) return  df.format((float)size/KB)+"KB";
    	else return size+"B";
    }
}