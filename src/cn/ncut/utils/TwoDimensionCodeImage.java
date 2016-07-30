package cn.ncut.utils;



import java.awt.image.BufferedImage;

import jp.sourceforge.qrcode.data.QRCodeImage;
/**
 * @author wzq
 *
 *version 1.0 2015-1-3 上午10:39:30
 */
public class TwoDimensionCodeImage implements QRCodeImage {

	BufferedImage bufImg;
	
	public TwoDimensionCodeImage(BufferedImage bufImg) {
		this.bufImg = bufImg;
	}
	
	@Override
	public int getHeight() {
		return bufImg.getHeight();
	}

	@Override
	public int getPixel(int x, int y) {
		return bufImg.getRGB(x, y);
	}

	@Override
	public int getWidth() {
		return bufImg.getWidth();
	}

}
