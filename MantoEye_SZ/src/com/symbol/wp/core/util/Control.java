// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   getControl.java

package com.symbol.wp.core.util;

import java.util.ArrayList;

public class Control {
	
	/*功能模块:page的下拉框
	 * init为page下拉框选中的选项
	 * allValue表示传进来的显示所有数据的个数，即value为
	 * allText在下拉框的选项显示所有数据       即text为
	 * input_page 为0时说明没有要自己设定显示数据的个数
	 */
	public static String getPageSelect(String objName,String Events,String Style,int input_page, int allValue, 
			String allText,  int init)
	{
		String control = new String("");
		control = "<select name='" + objName + "' id='" + objName + "'"
		           +Events + Style + ">" + "\n";
		   boolean page_flag = false;
		   switch(input_page)
		   {
		   case 15:
		   case 30:
		   case 50:
		   case 100:
			   page_flag = true; //说明自己定义的页的个数
			   break;
			   default:
				   page_flag =false;
		   }
		   if((!page_flag)&&(input_page!=0))  //自己定义的页数
		   {
			    if(init == input_page)
				{
					control = control + "<option value =" + input_page + " selected>"
					          + input_page + "</option>";
				}
				else
					control = control + "<option value=" + input_page + ">" + input_page
					+ "</option>";
		   }
		   boolean flag = false;//怕数据的总个数与下面几个页数的个数一样
		    switch(init)
		    {
		    case 15:
		        control = control + "<option value=15 selected>15</option>";
			    control = control + "<option value=30>30</option>";
			    control = control + "<option value=50>50</option>";
			    control = control + "<option value=100>100</option>";
			      flag = true;
			    break;
		    case 30:
		        control = control + "<option value=15>15</option>";
			    control = control + "<option value=30  selected>30</option>";
			    control = control + "<option value=50>50</option>";
			    control = control + "<option value=100>100</option>";
			    flag = true;
			    break;
		    case 50:
		        control = control + "<option value=15>15</option>";
			    control = control + "<option value=30>30</option>";
			    control = control + "<option value=50 selected>50</option>";
			    control = control + "<option value=100>100</option>";
			    flag = true;
			    break;
		    case 100:
		        control = control + "<option value=15>15</option>";
			    control = control + "<option value=30>30</option>";
			    control = control + "<option value=50>50</option>";
			    control = control + "<option value=100 selected>100</option>";
			    flag = true;
			    break;
			    default:
			    control = control + "<option value=15>15</option>";
			    control = control + "<option value=30>30</option>";
			    control = control + "<option value=50>50</option>";
			    control = control + "<option value=100>100</option>";
		    }
		    
		    if((init == allValue)&&!flag)
			{
				control = control + "<option value =" + allValue + " selected>"
				          + allText + "</option>";
				
				
			}
			else
				control = control + "<option value=" + allValue + ">" + allText
				+ "</option>";
		return control;
	}
	
	public static String VisibleOrNot(String objName, boolean i) {
		String display = null;
		if (i) {
			display = "<script language='javascript'>\n";
			display = display + "document.all." + objName
					+ ".style.display='';" + "\n";
			display = display + "</script>";
		} else {
			display = "<script language='javascript'>\n";
			display = display + "document.all." + objName
					+ ".style.display='none';" + "\n";
			display = display + "</script>";
		}
		return display;
	}

	public static String setPorperty(String objName, String Porperty, String val) {
		String display = null;
		display = "<script language='javascript'>\n";
		display = display + "document.all." + objName + "." + Porperty + "='"
				+ val + "';\n";
		display = display + "</script>";
		return display;
	}

	public static String setPorperty(String objName, String Porperty, boolean val) {
		String display = null;
		display = "<script language='javascript'>\n";
		display = display + "document.all." + objName + "." + Porperty + "="
				+ val + ";\n";
		display = display + "</script>";
		return display;
	}

	public static String increaseItem(String objName, String Content, String value) {
		String display = null;
		display = "<script language='javascript'>\n";
		display = display + "var olength=" + objName + ".length;";
		display = display + objName + ".options[olength]=new Option('"
				+ Content + "',olength);";
		display = display + objName + ".options[olength].value=" + value + ";";
		display = display + "</script>";
		return display;
	}

	public static String getSelect(ArrayList resultlist, String CategoryID,
			String objName, String init) {
		String text1 = null;
		String control = new String("");
		int size = resultlist.size();
		control = "<select name=\"" + objName + "\" id=\"" + objName + "\">"
				+ "\n";
		for (int i = 0; i < size; i++) {
			String result[] = new String[4];
			result = (String[]) resultlist.get(i);
			String id = result[0];
			if (id.equals(CategoryID)) {
				String val1 = result[2];
				if (val1.equals(init))
					control = control + "<option value=" + val1 + " selected>";
				else
					control = control + "<option value=" + val1 + ">";
				text1 = result[3];
				control = control + text1 + "</option>" + "\n";
			}
		}

		control = control + "</select>";
		return control;
	}

	public static String getSelect(ArrayList resultlist, String CategoryID,
			String objName, String init, String Style) {
		String text1 = null;
		String control = new String("");
		int size = resultlist.size();
		control = "<select name=\"" + objName + "\" id=\"" + objName + "\" "
				+ Style + ">" + "\n";
		for (int i = 0; i < size; i++) {
			String result[] = new String[4];
			result = (String[]) resultlist.get(i);
			String id = result[0];
			if (id.equals(CategoryID)) {
				String val1 = result[2];
				if (val1.equals(init))
					control = control + "<option value=" + val1 + " selected>";
				else
					control = control + "<option value=" + val1 + ">";
				text1 = result[3];
				control = control + text1 + "</option>" + "\n";
			}
		}

		control = control + "</select>";
		return control;
	}

	public static String getSelect(ArrayList resultlist, String CategoryID,
			String objName, String init, String Style, String FirstValue,
			String FirstText) {
		String text1 = null;
		String control = new String("");
		int size = resultlist.size();
		control = "<select name=\"" + objName + "\" id=\"" + objName + "\" "
				+ Style + ">" + "\n";
		if (init == FirstValue)
			control = control + "<option value=" + FirstValue + " selected>"
					+ FirstText + "</option>";
		else
			control = control + "<option value=" + FirstValue + ">" + FirstText
					+ "</option>";
		for (int i = 0; i < size; i++) {
			String result[] = new String[4];
			result = (String[]) resultlist.get(i);
			String id = result[0];
			if (id.equals(CategoryID)) {
				String val1 = result[2];
				if (val1.equals(init))
					control = control + "<option value=" + val1 + " selected>";
				else
					control = control + "<option value=" + val1 + ">";
				text1 = result[3];
				control = control + text1 + "</option>" + "\n";
			}
		}

		control = control + "</select>";
		return control;
	}

	public static String getRadioCheck(String Content[], String objName, int kind,
			String initial) {
		String result = "";
		if (Content != null) {
			int length = Content.length;
			for (int i = 0; i < length; i++) {
				if (kind == 1)
					if (initial.equals(String.valueOf(i)))
						result = result + "<input name=\"" + objName
								+ "\" type=\"radio\" value=\"" + i
								+ "\" checked>" + Content[i];
					else
						result = result + "<input name=\"" + objName
								+ "\" type=\"radio\" value=\"" + i + "\">"
								+ Content[i];
				if (kind == 2) {
					int isContain = 0;
					String aa[] = initial.split(",");
					for (int j = 0; j < aa.length; j++) {
						if (!aa[j].equals(String.valueOf(i)))
							continue;
						isContain = 1;
						break;
					}

					if (isContain == 1)
						result = result + "<input name=\"" + objName
								+ "\" type=\"checkbox\" value=\"" + i
								+ "\" checked>" + Content[i];
					else
						result = result + "<input name=\"" + objName
								+ "\" type=\"checkbox\" value=\"" + i + "\">"
								+ Content[i];
				}
			}

		}
		return result;
	}
}
