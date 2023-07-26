package com.a2m.gen.services.sys;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a2m.gen.constants.CommonContants;
import com.a2m.gen.entities.TccoStd;
import com.a2m.gen.repository.common.TccoSTDRepository;
import com.a2m.gen.repository.sys.sys0104.Sys0104Repository;
import com.a2m.gen.services.common.CommonService;

@Service
public class Sys0104Service {

	private DecimalFormat myFormatter = new DecimalFormat("00");

	@Autowired
	private Sys0104Repository sys0104Repo;
	@Autowired(required = false)
	private TccoSTDRepository tccoStdRepo;
	@Autowired
	private CommonService commonService;

	public List<TccoStd> search(Map<String, Object> params) {
		return sys0104Repo.search(params);
	}

	public String getMaxCommCd(String commCd) {
		return tccoStdRepo.getMaxCommCd(commCd);
	}

	public String getMaxCommCd2() {
		return tccoStdRepo.getMaxCommCd2();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public TccoStd save(TccoStd tccoStd) throws Exception {
		
		String commCd = getCommCd(tccoStd);
		String userUid = commonService.getUserUid();
		tccoStd.setCommCd(commCd);
		tccoStd.setCreatedBy(userUid);
		tccoStd.setCreatedDate(new Date());
		tccoStd.setUseYn(CommonContants.USE_Y);

		if (StringUtils.isEmpty(tccoStd.getUpCommCd())) {
			tccoStd.setLev(1);
		} else {
			Optional<TccoStd> upTccoStd = tccoStdRepo.findByCommCd(tccoStd.getUpCommCd());
			if (upTccoStd == null) {
				throw new SQLException("CommCd not found !!!");
			} 
			tccoStd.setLev(upTccoStd.get().getLev() + 1);
		}
		return tccoStdRepo.save(tccoStd);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public TccoStd update(TccoStd tccoStd) throws Exception {
		String userUid = commonService.getUserUid();
		tccoStd.setUpdatedBy(userUid);
		tccoStd.setUpdatedDate(new Date());
		return tccoStdRepo.save(tccoStd);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void deleteCommCd(String commCd) {
		tccoStdRepo.deleteByCommCd(commCd);
	}

	public String getCommCd(TccoStd tccoStd) throws SQLException {
		String upCommCd = tccoStd.getUpCommCd();
		String commCd = "";
		if (StringUtils.isNotEmpty(upCommCd)) {
			String maxCommCd = getMaxCommCd(upCommCd);
			if (StringUtils.isEmpty(maxCommCd)) {
				if (isExistCommCd(upCommCd)) {
					commCd = upCommCd + "-01";
				} else {
					throw new SQLException("CommCd not found !!!");
				}
			} else {
				int lastIndexOfMinus = maxCommCd.lastIndexOf("-");
				int number = Integer.parseInt(maxCommCd.substring(lastIndexOfMinus + 1));
				commCd = maxCommCd.substring(0, lastIndexOfMinus + 1) + myFormatter.format(number + 1);
			}
		} else {
			String maxCommCd = getMaxCommCd2();
			if (StringUtils.isNotEmpty(maxCommCd)) {
				commCd = myFormatter.format(Integer.parseInt(maxCommCd) + 1);
			} else {
				commCd = "01";
			}

		}
		return commCd;
	}

	public boolean isExistCommCd(String commCd) {
		int count = tccoStdRepo.countCommCd(commCd);
		if (count > 0) {
			return true;
		}
		return false;
	}
}
