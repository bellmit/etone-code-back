package com.symbol.wp.core.dao.impl;

/* 所有BO的父类 */

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.symbol.wp.core.dto.VBaseOpLog;
import com.symbol.wp.core.entity.TbBaseOpLog;
import com.symbol.wp.core.util.Utils;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

/**
 * @author wendy 日志功能
 */
//Spring DAO Bean的标识
@Repository
public class BaseOpLogDAO extends HibernateDao<TbBaseOpLog, String>{
	public final static int ERROR = 0;
	public final static int WARNING = 1;
	public final static int INFO = 2;
	public final static int CODE_ISO_8859_1 = 0;
	public final static int CODE_GBK = 1;
	public final static int TO_DB = 0;
	public final static int TO_FILE = 1;
	private com.symbol.wp.core.util.Log m_log = null;
	private int m_code = -1;
	private StringBuilder m_sb = null;

	/**
	 * @param TbBaseOpLog
	 * @param type
	 *            参数设定:(TbSysOpLog TbSysOpLog, int type) 功能描述:记录日志
	 *            逻辑判断:type:为0表示写如数据库，为1表示生成日志文件 返回值:
	 * @author:wendy
	 */
	public void log(TbBaseOpLog TbBaseOpLog, int type) {
		if (type == TO_DB) {
			checkLock(0);
			this.save(TbBaseOpLog);						
		}
		if (type == TO_FILE) {
			m_sb.setLength(0);
			m_sb.append(TbBaseOpLog.getId());
			m_sb.append(" | ");
			m_sb.append(TbBaseOpLog.getVcSysLogType());
			m_sb.append(" | ");
			m_sb.append(TbBaseOpLog.getVcLoginUser());
			m_sb.append(" | ");
			m_sb.append(TbBaseOpLog.getVcLoginIp());
			m_sb.append(" | ");
			m_sb.append(TbBaseOpLog.getVcLogContent());
			m_sb.append(" | ");
			m_sb.append(TbBaseOpLog.getDtRecordTime());
			m_sb.append(" | ");
			m_log.log(m_sb.toString());
		}
	}
	private void checkLock(int cnt){
		boolean islock = isLock("tbBaseOpLog");
		if(islock){//表已经被锁
			logger.info("日志表被锁，无法插入日志，1秒后重试");
			try {
				//休息一秒后再次测试表锁是否存在
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}
			if(cnt>5){//重试5次无法成功，发生错误
				logger.error("日志表被锁，重试5次无法成功，发生错误！");
			}else{
				checkLock(cnt++);
			}		
		}else{
			//logger.info("日志表没有被锁.");
		}
	}
	/**
	 * @param content
	 * @return 参数设定:(String content) 功能描述:对内容进行编码 逻辑判断: 返回值:
	 * @author:wendy
	 */
	private String getContent(String content) {
		if (m_code == CODE_ISO_8859_1) {
			return Utils.toISO_8859_1(content);
		} else if (m_code == CODE_GBK) {
			return Utils.toGBK(content);
		} else
			return content;
	}

	public VBaseOpLog findVById(Long id) {

		return null;
	}
}
