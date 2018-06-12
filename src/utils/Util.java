package utils;


import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import com.asprise.util.ocr.OCR;


/*
 * Copyright (C) 2005-2010 TENCENT Inc.All Rights Reserved.
 * FileName：Util.java
 * Description：
 * History：
 * 1.0 Denverhan 2013-4-11 Create
 */

public class Util
{
	private static OCR				sOcr			= new OCR();
	private static SimpleDateFormat	sDateFormater	= new SimpleDateFormat("yyyy_MM_dd:HH_mm_ss");
	private static Robot			sRobot			= null;

	static
	{
		int i = 0;
		do
		{
			try
			{
				sRobot = new Robot();
			}
			catch (AWTException e)
			{
				e.printStackTrace();
			}
		}
		while (sRobot == null && i++ < 2);
	}

	public static  String exchangeChar(String text)
	{
		if (text != null)
		{
			text = text.toLowerCase();
			text = text.replace("o", "0");
			text = text.replace("O", "0");
			text = text.replace(" ", "");
			text = text.replace("l", "1");
			text = text.replace("g", "9");
		}

		return text;
	}

	public static String doOCR(BufferedImage image )
	{
		return doOCR(image, false);
	}
	
	public static String doOCR(BufferedImage image , boolean doTwoValue)
	{
		String text = null;
		text = sOcr.recognizeCharacters(doTwoValue? doTwoValue(image):image);
		return text;
	}
	
	static BufferedImage doTwoValue(BufferedImage bi)
	{
		int h = bi.getHeight();// 获取图像的高
		int w = bi.getWidth();// 获取图像的宽
		int[][] gray = new int[w][h];
		int average = 0;
		long total = 0;
		for (int x = 0; x < w; x++)
		{
			for (int y = 0; y < h; y++)
			{
				gray[x][y] = getGray(bi.getRGB(x, y));
				total += gray[x][y];
			}
		}
		average = (int) (total / (w * h));
		BufferedImage nbi = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
		int max = new Color(255, 255, 255).getRGB();
		int min = new Color(0, 0, 0).getRGB();
		for (int x = 0; x < w; x++)
		{
			for (int y = 0; y < h; y++)
			{
				if (gray[x][y] > average)
				{
					nbi.setRGB(x, y, max);
				}
				else
				{
					nbi.setRGB(x, y, min);
				}
			}
		}
		return nbi;
	}
	
	public static  boolean  captureRect(Rectangle rect)
	{
		try
		{
			BufferedImage bi = sRobot.createScreenCapture(rect);
			ImageIO.write(bi, "png", new File("./out.png"));
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public static  String capture(Rectangle rect)
	{
		try
		{
			BufferedImage bi = sRobot.createScreenCapture(rect);
			if (bi != null)
			{
				return doOCR(bi);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获得当前时间。
	 * 
	 * @return
	 */
	public static String getCurrentTime()
	{
		return sDateFormater.format(new Date());
	}

	/**
	 * 将秒表示的时间转换为人类可以读时间
	 * 
	 * @param sec 单位为毫秒
	 * @return
	 */
	public static String seconds2Time(long sec)
	{
		Date date = new Date(sec);
		String result = sDateFormater.format(date);
		return result;
	}

	public static FileOutputStream openOutputStream(File file) throws IOException
	{
		if (file.exists())
		{
			if (file.isDirectory())
			{
				throw new IOException("File '" + file + "' exists but is a directory");
			}
			if (file.canWrite() == false)
			{
				throw new IOException("File '" + file + "' cannot be written to");
			}
		}
		else
		{
			File parent = file.getParentFile();
			if (parent != null && parent.exists() == false)
			{
				if (parent.mkdirs() == false)
				{
					throw new IOException("File '" + file + "' could not be created");
				}
			}
		}
		return new FileOutputStream(file);
	}


	public static FileOutputStream openOutputStream(String filePath) throws IOException
	{
		return openOutputStream(new File(filePath));
	}

	public static boolean isEmpty(String text)
	{
		return text == null || text.trim().length() == 0;
	}

	private static int getGray(int rgb)
	{
		Color c = new Color(rgb);
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		int top = (r + g + b) / 3;
		return (int) (top);
	}


	/**
	 * 对图片进行放大
	 * 
	 * @param originalImage 原始图片
	 * @param times 放大倍数
	 * @return
	 */
	public static  BufferedImage zoomInImage(BufferedImage originalImage, float times)
	{

		if (!(times <= 0.9F || times >= 1.1))
		{
			return originalImage;
		}

		int width = (int) (originalImage.getWidth() * times);
		int height = (int) (originalImage.getHeight() * times);
		BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
		Graphics g = newImage.getGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		g = null;
		return newImage;
	}

	/**
	 * 除掉字符串dot后面的内容。
	 * 
	 * @param price
	 * @return
	 */
	public static String trunkFloat(String price)
	{
		String result = price;

		if (Util.isEmpty(price))
		{
			return result;
		}
		else
		{
			int dotPos = price.indexOf(".");
			if (dotPos != -1 || (dotPos = price.indexOf(",")) != -1)
			{
				result = price.substring(0, dotPos);
			}
		}

		return result;
	}
}
