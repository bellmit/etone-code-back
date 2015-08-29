/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.fs.microarea.update.MainRunner.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2014-1-24 下午04:54:17 
 *   LastChange: 2014-1-24 下午04:54:17 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.fs.microarea.update;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import z.z.w.project.common.config.Global;
import z.z.w.project.common.config.InitPro;
import z.z.w.project.test.service.BussService;
import z.z.w.project.test.vo.TbCgiInfo;
import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.fs.microarea.update.MainRunner.java
 */
public class MainRunner extends InitPro {

	private BussService bussService = null;

	/**
	 * <br>
	 * Created on: 2014-1-24 下午04:54:17
	 */
	public MainRunner() {
		super();
		init();
	}

	/**
	 * <br>
	 * Created on: 2014-1-24 下午04:57:56
	 */
	private void init() {
		try {
			ApplicationContext context = new FileSystemXmlApplicationContext(Global.SPRING_XML);
			bussService = (BussService) context.getBean("bussService");
		} catch (BeansException e) {
			LogTools.getLogger(getClass()).error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}", new Object[] { "bussService", "init()", e.getMessage(), e.getCause(), e.getClass() });
			System.exit(0);
		}
	}

	/**
	 * <br>
	 * Created on: 2014-1-24 下午04:57:31
	 */
	private void startService() {
		try {

			/**
			 * 1、讀取數據庫tbcgiinfo表信息<br />
			 * 2、讀取微區域文件內容<br />
			 * 3、找到匹配的微區域<br />
			 * 4、寫到文件/或直接更新到數據庫<br />
			 * 5、在更新group表
			 * 
			 * commit;select vcMicroAreaGroup,vcMicroArea,vcDistrict,vcStreet,vcTown,* <br />
			 * from epmp_tbMicroArea_fs order by vcmicroareagroup,vcMicroarea,vcdistrict <br />
			 * commit;select * from epmp_tbMicroAreaMapping_fs
			 */

			List<TbCgiInfo> cgiInfoList = bussService.getCgiInfoList();

			if (DataTools.isEmpty(cgiInfoList)) {
				LogTools.getLogger(getClass()).warn("Cgi info is null,return .");
				return;
			}

			LogTools.getLogger(getClass()).info("New table size : [{}].", cgiInfoList.size());

			// checkTableDataCount(tableNameList);

		} catch (Exception e) {
			LogTools.getLogger(getClass()).error("When [{} - {} --> Exception ] ,  \n  MASSAGE : {} \n  CAUSE :{} \n  CLASS : {}", new Object[] { "bussService", "startService()", e.getMessage(), e.getCause(), e.getClass() });
			System.exit(1);
		}
	}

	/**
	 * <br>
	 * Created on: 2014-1-24 下午04:54:17
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MainRunner mainRunner = new MainRunner();
		mainRunner.startService();
	}

}
