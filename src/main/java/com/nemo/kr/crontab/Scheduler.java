package com.nemo.kr.crontab;


import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.nemo.kr.common.Const;
import com.nemo.kr.common.exception.ApiException;


@Component
public class Scheduler {
	private static final Logger cj = LoggerFactory.getLogger("cj");
	
	// CJ 해당 일 기준 신규 뉴스 조회 및 등록
	@Scheduled(cron = "0 0 21 * * *")
	public void getAddList() {
		try {
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
