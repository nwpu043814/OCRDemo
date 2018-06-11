package snippet;

import java.awt.Rectangle;

public final class RectangleBuilder {
	int mX;
	int mY;
	int mWidth;
	int mHeight;
	
	public static RectangleBuilder newBuilder()
	{
		return new RectangleBuilder();
	}
	
	public RectangleBuilder x(int x)
	{
		this.mX = x;
		return this;
	}
	
	public RectangleBuilder y(int y)
	{
		this.mY = y;
		return this;
	}
	
	public RectangleBuilder width(int w)
	{
		this.mWidth = w;
		return this;
	}
	
	public RectangleBuilder height(int h)
	{
		this.mHeight = h;
		return this;
	}
	
	public Rectangle build()
	{
		return new Rectangle(mX, mY, mWidth, mHeight);
	}
	
}
