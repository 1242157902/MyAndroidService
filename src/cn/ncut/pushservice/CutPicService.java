package cn.ncut.pushservice;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class CutPicService {
	public static String getFormatInFile(InputStream input) {
		ImageInputStream iis;
		String ext="jpg";//默认
		try {
			iis = ImageIO.createImageInputStream(input);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) return null;
			ImageReader reader = iter.next();
			//ext=reader.getFormatName();
			iis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ext;
	}
	
	
    public static void cutJPG(InputStream input, OutputStream out, int x,
            int y, int width, int height) throws IOException {
        ImageInputStream imageStream = null;
        try {
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = readers.next();
        imageStream = ImageIO.createImageInputStream(input);
        reader.setInput(imageStream, true);
        ImageReadParam param = reader.getDefaultReadParam();
        
        System.out.println(reader.getWidth(0));
        System.out.println(reader.getHeight(0));
        Rectangle rect = new Rectangle(x, y, width, height);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ImageIO.write(bi, "jpg", out);
    } finally {
        imageStream.close();
    }
}


public static void cutPNG(InputStream input, OutputStream out, int x,
        int y, int width, int height) throws IOException {
    ImageInputStream imageStream = null;
    try {
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("png");
        ImageReader reader = readers.next();
        imageStream = ImageIO.createImageInputStream(input);
        reader.setInput(imageStream, true);
        ImageReadParam param = reader.getDefaultReadParam();
        
        System.out.println(reader.getWidth(0));
        System.out.println(reader.getHeight(0));
        
        Rectangle rect = new Rectangle(x, y, width, height);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ImageIO.write(bi, "png", out);
    } finally {
        imageStream.close();
    }
}

public static void cutImage(InputStream input, OutputStream out, String type,int x,
        int y, int width, int height) throws IOException {
    ImageInputStream imageStream = null;
    try {
        String imageType=(null==type||"".equals(type))?"jpg":type;
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(imageType);
        ImageReader reader = readers.next();
        imageStream = ImageIO.createImageInputStream(input);
        reader.setInput(imageStream, true);
        ImageReadParam param = reader.getDefaultReadParam();
        Rectangle rect = new Rectangle(x, y, width, height);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ImageIO.write(bi, imageType, out);
    }  
	finally {
        imageStream.close();
    }
}

public static void cutImg(InputStream input, OutputStream out, String type,int x,
        int y, int width, int height) throws IOException {
    ImageInputStream imageStream = null;
    try {
        String imageType=(null==type||"".equals(type))?"jpg":type;
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(imageType);
        ImageReader reader = readers.next();
        imageStream = ImageIO.createImageInputStream(input);
        reader.setInput(imageStream, true);
        ImageReadParam param = reader.getDefaultReadParam();
        Rectangle rect = new Rectangle(x, y, width, height);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ImageIO.write(bi, imageType, out);
    } finally {
        imageStream.close();
        out.close();
    }
}

}