/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.other.GetFileSize.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-26 下午02:32:15 
 *   LastChange: 2013-8-26 下午02:32:15 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.other;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

import org.junit.Test;

import z.z.w.project.SuperClass;

/**
 * z.z.w.project.test.other.GetFileSize.java
 */
public class GetFileSize extends SuperClass {

	@Test
	public void testGetFileSize2() {
		File[] roots = File.listRoots();
		long total = 0l;
		for (File _file : roots) {
			System.out.println(_file.getPath());
			System.out.println("剩余空间 = " + FormetFileSize(_file.getFreeSpace())
					+ " G");
			System.out.println("已使用空间 = "
					+ FormetFileSize(_file.getUsableSpace()) + " G");
			System.out.println(_file.getPath() + "盘总大小 = "
					+ FormetFileSize(_file.getTotalSpace()) + " G");
			System.out.println();
			total += _file.getTotalSpace();
		}
		System.out.println("你的硬盘总大小 = " + FormetFileSize(total));
	}

	@Test
	public void testGetFileSize() {
		GetFileSize g = new GetFileSize();
		long startTime = System.currentTimeMillis();
		try {
			long l = 0;
			String path = "D:\\09_study\\media";
			File ff = new File(path);
			if (ff.isDirectory()) { // 如果路径是文件夹的时候
				System.out.println("文件个数           " + g.getlist(ff));
				System.out.println("目录");
				l = g.getFileSize(ff);
				System.out.println(path + "目录的大小为：" + g.FormetFileSize(l));
			} else {
				System.out.println("     文件个数           1");
				System.out.println("文件");
				l = g.getFileSizes(ff);
				System.out.println(path + "文件的大小为：" + g.FormetFileSize(l));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("总共花费时间为：" + (endTime - startTime) + "毫秒...");
	}

	public long getFileSizes(File f) throws Exception {// 取得文件大小
		long s = 0;
		if (f.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(f);
			s = fis.available();
		} else {
			f.createNewFile();
			System.out.println("文件不存在");
		}
		return s;
	}

	// 递归
	public long getFileSize(File f) throws Exception// 取得文件夹大小
	{
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}

	public String FormetFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.##");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	public long getlist(File f) {// 递归求取目录文件个数
		long size = 0;
		File flist[] = f.listFiles();
		size = flist.length;
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getlist(flist[i]);
				size--;
			}
		}
		return size;

	}

}
