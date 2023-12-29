package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.NotificationDTO;

/**

  * @FileName : NoticeMapper.java

  * @Project : ilgaja

  * @Date : 2020. 10. 12. 

  * @작성자 : Na Gil Jin

  * @변경이력 :

  * @프로그램 설명 :
 */
public interface NotificationMapper {
	public int selectListCnt(NotificationDTO notificationDTO);
	public int selectSuperAdminListCount(NotificationDTO notificationDTO);
	
	public List<NotificationDTO> selectSuperAdminList(NotificationDTO notificationDTO);
	public List<NotificationDTO> selectList(NotificationDTO notificationDTO);

	//nemojjang: 소장용 앱에서사용됨
	public int selectNotificationListCnt(NotificationDTO notificationDTO);
	public List<NotificationDTO> selectNotificationList(NotificationDTO notificationDTO);
	
	
}
