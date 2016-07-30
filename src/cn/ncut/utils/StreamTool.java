package cn.ncut.utils;

import java.io.ByteArrayOutputStream;

import java.io.InputStream;
/**
 * 
 * @author wzq
 *
 * version 1.0 2014-9-19 下午5:20:42
 */
public class StreamTool {
	/**
	 * 
	 * @param inStream
	 * @return
	 */
	public static byte[] read(InputStream inStream) throws Exception{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len = inStream.read(buffer)) != -1 ){
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

}
