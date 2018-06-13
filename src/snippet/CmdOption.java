package snippet;

import org.kohsuke.args4j.Option;

public class CmdOption {
	

	@Option(name = "-x", usage = "The left in the cordinate")
	public int x = 227;

	@Option(name = "-y", usage = "The top in the cordinate")
	public int y = 335;

	@Option(name = "-w", usage = "The width of the capturing rectange")
	public int width = 55;

	@Option(name = "-h", usage = "The height of the capturing rectange")
	public int height = 100;
	
	@Option(name = "-debug", usage = "whether to save the image")
	public boolean  debug = false;
}
