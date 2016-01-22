package kr.co.ecore.web.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ecore.web.dao.MemberDao;

@Service(value="ReportService")
public class ReportServiceImpl implements ReportService {

	protected final static Logger logger = Logger.getLogger(ReportServiceImpl.class);

	@Autowired
	private MemberDao memberDao;
	
	public String getRename(String name) {
		// TODO Auto-generated method stub
		List list = memberDao.list("");
		//List list2 = memberDao.listMysql("");
		
		return "RE"+name+":"+list;//+'/'+list2;
	}
	

}