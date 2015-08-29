package com.symbol.wp.core.util;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.util.*;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

/**
 * 验证码图片生成工具
 * 
 * @author MaxChou
 * @version 2006-4-16
 */
public class ValidateCode extends HttpServlet
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5001414344358720568L;

	
	@Override
	public void init(ServletConfig conf) throws ServletException
	{
		super.init(conf);
	}

	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{
		res.setContentType("image/jpeg");
		res.setHeader("Pragma", "No-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setDateHeader("Expires", 0);
		HttpSession session = req.getSession();

		// 在内存中创建图象
		int width = 60, height = 20;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics2D g = (Graphics2D)image.getGraphics();

		// 生成随机类
		Random random = new Random();

		// 设定背景色
		g.setColor(new Color(225, 250, 255));
		g.fillRect(0, 0, width, height);

		// 设定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

		// 画边框
		g.setColor(new Color(200, 225, 255));
		g.drawRect(0, 0, width - 1, height - 1);

		// 取随机产生的认证码(4位数字)
		String sRand = "";
		int value = 0;
		int count = 0;
		while (count < 4)
		{
			value = 48 + random.nextInt(43);
			if (value > 57 && value < 65)
				continue;

			String rand = new String(new byte[] { (byte)value });
			
			if(!rand.equalsIgnoreCase("0")&&!rand.equalsIgnoreCase("O")){
				sRand += rand;
				g.setColor(new Color(75 + random.nextInt(90), 75 + random.nextInt(90),
						75 + random.nextInt(90)));
				// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
				g.drawString(rand, 13 * count + 3, 16);
				count++;
			}
		}
		String interference = PropertiesUtil.getInstance().getProperty("system.validatecode.interference");
		// 随机产生干扰线，使图象中的认证码不易被其它程序探测到
		if(interference!=null&&interference.equalsIgnoreCase("true")){
			g.setColor(getRandColor(80, 180));
			for (int i = 0; i < 15; i++)
			{
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}
		}
		// 将认证码存入SESSION
		session.setAttribute("LOGIN_VALIDATE_CODE", sRand);

		g.dispose();
		ImageIO.write(image, "JPEG", res.getOutputStream());
	}

	
	// 给定范围获得随机颜色
	private Color getRandColor(int fc, int bc){
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}


	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException
	{
		doGet(req, res);
	}


}
