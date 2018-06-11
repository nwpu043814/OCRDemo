import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import snippet.CmdOption;
import snippet.RectangleBuilder;
import utils.Util;

public class OCRDriver {

	 public static Logger log = Logger.getLogger(OCRDriver.class); 
	public static void main(String []arg)
	{
		try {

			//ʹ��Ĭ�ϵ�������Ϣ������Ҫдlog4j.properties
		     BasicConfigurator.configure();
		     //������־�������Ϊinfo���⽫���������ļ������õļ���
		     log.setLevel(Level.INFO);			
			CmdOption option = new CmdOption();
			CmdLineParser parser = new CmdLineParser(option);
			if (arg.length == 0) {
				log.info("Usage:java -jar OCR.jar -x 0|-y 0|-w 55|-h 120");
//				return;
			}
			
			try {
				parser.parseArgument(arg);
			} catch (CmdLineException e1) {
				e1.printStackTrace();
				log.debug(e1.getMessage());
				return;
			}
			
//			Util.captureRect(RectangleBuilder.newBuilder().x(option.x).y(option.y).width(option.width).height(option.height).build());
			BufferedImage img = ImageIO.read(new File("./out.png"));
			String text = Util.doOCR(img);
			System.out.print(Util.exchangeChar(text));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}