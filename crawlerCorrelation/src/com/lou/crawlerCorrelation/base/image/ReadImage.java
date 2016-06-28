package com.lou.crawlerCorrelation.base.image;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * @author loukangwei
 * @typename ReadImage
 * @desc 把bpm格式转换为jpg格式
 */
public class ReadImage {

	public static void download(String imageUrl, String imageFilename) throws IOException {
		URL url = new URL(imageUrl);
		Image src = javax.imageio.ImageIO.read(url);

		int wideth = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage tag = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);

		// 绘制缩小的图
		tag.getGraphics().drawImage(src, 0, 0, wideth, height, null);

		File file = new File(imageFilename);

		ImageIO.write(tag, "jpg", file);
	}
	
	public  static void main(String[] args)
	{
		
	}

}
