-- DROP TABLE ------------------------------------------------------------------

drop table seq;
drop table code;
drop table company;
drop table admin;
drop table employer;
drop table work;
drop table ilbo;
drop table today_worker;
drop table worker;

--------------------------------------------------------------------------------

alter table employer change employer_phone employer_phone1 varchar(100) comment '구인처 폰번호1';


alter table employer add employer_phone2 varchar(100) comment '구인처 폰번호2' after employer_phone1;
alter table employer add employer_phone3 varchar(100) comment '구인처 폰번호3' after employer_phone2;
alter table employer add employer_phone4 varchar(100) comment '구인처 폰번호4' after employer_phone3;




CREATE TABLE seq (
  seq_key                     varchar(250)    NOT NULL                          COMMENT '일련번호 값',
  seq_val                     bigint(11)      NOT NULL                          COMMENT '일련번호',

  reg_date                    datetime                                          COMMENT '등록일시',
  reg_admin                   varchar(1000)   NOT NULL                          COMMENT '등록자',
  mod_date                    timestamp       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP       COMMENT '변경일시',
  mod_admin                   varchar(1000)   NOT NULL                          COMMENT '변경자',

  PRIMARY KEY (seq_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT='일련번호';


CREATE TABLE code (
  code_seq                    bigint(20)      NOT NULL AUTO_INCREMENT           COMMENT '코드 순번',
                          
  code_type                   int(11)         NOT NULL                          COMMENT '코드 구분',
  code_value                  varchar(1000)   NOT NULL                          COMMENT '코드 값',
  code_name                   varchar(1000)   NOT NULL                          COMMENT '코드 명',
  code_rank                   int(11)         DEFAULT '1'                       COMMENT '코드 순서',
                          
  use_yn                      int(11)         DEFAULT '1'                       COMMENT '사용유무 - 0:정지 1:사용',

  reg_date                    datetime                                          COMMENT '등록일시',
  reg_admin                   varchar(1000)   NOT NULL                          COMMENT '등록자',
  mod_date                    timestamp       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP       COMMENT '변경일시',
  mod_admin                   varchar(1000)   NOT NULL                          COMMENT '변경자',

  PRIMARY KEY (code_seq)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT='코드';


CREATE TABLE company (
  company_seq                 bigint(20)      NOT NULL AUTO_INCREMENT           COMMENT '회사 순번',

  company_num                 varchar(100)                                      COMMENT '회사 사업자번호',
  company_name                varchar(300)                                      COMMENT '회사 명',
  company_owner               varchar(200)                                      COMMENT '회사 대표자',
  company_business            varchar(300)                                      COMMENT '회사 업태',
  company_sector              varchar(300)                                      COMMENT '회사 업종',
  company_tel                 varchar(100)                                      COMMENT '회사 전화번호',
  company_fax                 varchar(100)                                      COMMENT '회사 팩스',
  company_phone               varchar(100)                                      COMMENT '회사 폰번호',
  company_email               varchar(300)                                      COMMENT '회사 이메일',
  company_addr                varchar(1000)                                     COMMENT '회사 주소',
  company_feature             varchar(500)                                      COMMENT '회사 특징',
  company_memo                text            CHARACTER SET latin1              COMMENT '회사 메모',

  use_yn                      int(11)         DEFAULT '1'                       COMMENT '사용유무 - 0:정지 1:사용',

  reg_date                    datetime                                          COMMENT '등록일시',
  reg_admin                   varchar(1000)   NOT NULL                          COMMENT '등록자',
  mod_date                    timestamp       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP       COMMENT '변경일시',
  mod_admin                   varchar(1000)   NOT NULL                          COMMENT '변경자',

  PRIMARY KEY (company_seq)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT='회사정보';


CREATE TABLE admin (
  admin_seq                   bigint(20)      NOT NULL AUTO_INCREMENT           COMMENT '관리자 순번',
  company_seq                 bigint(20)      NOT NULL                          COMMENT '소속 회사 순번',

  admin_name                  varchar(200)    NOT NULL                          COMMENT '관리자 이름',
  admin_id                    varchar(100)    NOT NULL                          COMMENT '관리자 아이디',
  admin_pass                  varchar(100)    NOT NULL                          COMMENT '관리자 패스워드',
  admin_phone                 varchar(100)                                      COMMENT '관리자 폰번호',
  admin_email                 varchar(50)                                       COMMENT '관리자 이메일',
  admin_memo                  text            CHARACTER SET latin1              COMMENT '관리자 메모, auth_level 0 인 최고 관리자만 작성 및 보기 가능',

  auth_level                  int(11)         DEFAULT '2'                       COMMENT '권한 - 0:전체최고관리자 1:회사최고관리자 2: 회사관리자',
  use_yn                      int(11)         DEFAULT '1'                       COMMENT '사용유무 - 0:미사용 1:사용',

  reg_date                    datetime                                          COMMENT '등록일시',
  reg_admin                   varchar(1000)   NOT NULL                          COMMENT '등록자',
  mod_date                    timestamp       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP       COMMENT '변경일시',
  mod_admin                   varchar(1000)   NOT NULL                          COMMENT '변경자',

  PRIMARY KEY (admin_seq)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT='관리자';


CREATE TABLE employer (
  employer_seq                bigint(20)      NOT NULL AUTO_INCREMENT           COMMENT '구인처 순번',
  company_seq                 bigint(20)      NOT NULL                          COMMENT '회사 순번',

  employer_num                varchar(100)                                      COMMENT '구인처 사업자번호',
  employer_name               varchar(300)    NOT NULL                          COMMENT '구인처 명',
  employer_owner              varchar(200)                                      COMMENT '구인처 대표자',
  employer_business           varchar(300)                                      COMMENT '구인처 업태',
  employer_sector             varchar(300)                                      COMMENT '구인처 업종',
  employer_tel                varchar(100)                                      COMMENT '구인처 전화번호',
  employer_fax                varchar(100)                                      COMMENT '구인처 팩스',
  employer_phone1             varchar(100)    NOT NULL                          COMMENT '구인처 폰번호1',
  employer_phone2             varchar(100)                                      COMMENT '구인처 폰번호2',
  employer_phone3             varchar(100)                                      COMMENT '구인처 폰번호3',
  employer_phone4             varchar(100)                                      COMMENT '구인처 폰번호4',
  employer_email              varchar(300)                                      COMMENT '구인처 이메일',
  employer_address            varchar(1000)                                     COMMENT '구인처 주소',
  employer_feature            text            CHARACTER SET latin1              COMMENT '구인처 특징',
  employer_memo               text            CHARACTER SET latin1              COMMENT '구인처 메모',

  use_yn                      int(11)         DEFAULT '1'                       COMMENT '사용유무 - 0:미사용 1:사용',

  reg_date                    datetime                                          COMMENT '등록일시',
  reg_admin                   varchar(1000)   NOT NULL                          COMMENT '등록자',
  mod_date                    timestamp       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP       COMMENT '변경일시',
  mod_admin                   varchar(1000)   NOT NULL                          COMMENT '변경자',

  PRIMARY KEY (employer_seq)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT='구인처';


CREATE TABLE work (
  work_seq                    bigint(20)      NOT NULL AUTO_INCREMENT           COMMENT '구인처 현장 순번',
  company_seq                 bigint(20)      NOT NULL                          COMMENT '회사 순번',
  employer_seq                bigint(20)      NOT NULL                          COMMENT '구인처 순번',

  work_name                   varchar(1000)   NOT NULL                          COMMENT '현장명',
  work_arrival_time           varchar(100)                                      COMMENT '현장도착시간',
  work_address                varchar(1000)                                     COMMENT '현장주소',
  work_latlng                 varchar(1000)                                     COMMENT '현장좌표',
  work_breakfast_yn           int(11)         DEFAULT '0'                       COMMENT '조식유무 - 1:유 0:무',
  work_age_limit              int(11)         DEFAULT '0'                       COMMENT '나이제한 - 0: 제한없음',
  work_blood_pressure_limit   int(11)         DEFAULT '0'                       COMMENT '혈압제한 - 0:제한없음',

  work_manager_name           varchar(200)                                      COMMENT '현장담당자 이름',
  work_manager_fax            varchar(100)                                      COMMENT '현장 팩스',
  work_manager_phone          varchar(100)    NOT NULL                          COMMENT '폰번호',
  work_manager_email          varchar(50)                                       COMMENT '현장담장자 이메일',

  work_memo                   text            CHARACTER SET latin1              COMMENT '현장설명',

  use_yn                      int(11)         DEFAULT '1'                       COMMENT '사용유무 - 0:미사용 1:사용',

  reg_date                    datetime                                          COMMENT '등록일시 - 출근시간',
  reg_admin                   varchar(1000)   NOT NULL                          COMMENT '등록자',
  mod_date                    timestamp       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP       COMMENT '변경일시',
  mod_admin                   varchar(1000)   NOT NULL                          COMMENT '변경자',

  PRIMARY KEY (work_seq)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT='구인처 현장';


-- 현장명 검색을 용이하게 하기 위해
CREATE OR REPLACE VIEW v_employer_work
AS
SELECT e.company_seq, e.employer_seq, w.work_seq, e.employer_name || ' (' || w.work_name || ')' as work_name
FROM employer e, work w
WHERE e.company_seq = w.company_seq
  AND e.employer_seq = e.employer_seq
;

CREATE TABLE ilbo (
  ilbo_seq                    bigint(20)      DEFAULT '1'                       COMMENT '순번',
  company_seq                 bigint(20)      NOT NULL                          COMMENT '회사  순번',
  employer_seq                bigint(20)      NOT NULL                          COMMENT '구인처 순번',
  work_seq                    bigint(20)      NOT NULL                          COMMENT '구인처 현장 순번',
  worker_seq                  bigint(20)                                        COMMENT '근로자 순번',
                             
  ilbo_date                   datetime                                          COMMENT '출역날짜',
  ilbo_work_name              varchar(1000)   NOT NULL                          COMMENT '현장명',

  ilbo_type_code              varchar(1000)                                     COMMENT '타입 코드 - 1차 2차 등 ( 기본값을 고민하자)',
  ilbo_type_name              varchar(1000)                                     COMMENT '타입 명 - 1차 2차 등 ( 기본값을 고민하자)',

  ilbo_kind_code              varchar(1000)                                     COMMENT '직종 코드 ( 기본값을 고민하자)',
  ilbo_kind_name              varchar(1000)                                     COMMENT '직종 명 ( 기본값을 고민하자)',

  ilbo_job_comment            varchar(1000)                                     COMMENT '작업내용',

  ilbo_unit_price             int(11)         DEFAULT '0'                       COMMENT '단가',
  ilbo_gongsu                 varchar(100)                                      COMMENT '공수',
  ilbo_fee                    int(11)         DEFAULT '0'                       COMMENT '수수료',
  ilbo_extra_pay              int(11)         DEFAULT '0'                       COMMENT '추가지불',
  ilbo_pay                    int(11)         DEFAULT '0'                       COMMENT '지불금',
  ilbo_pay_type_code          varchar(1000)                                     COMMENT '지불타입 코드',
  ilbo_pay_type_name          varchar(1000)                                     COMMENT '지불타입 명',
  ilbo_pay_time               datetime                                          COMMENT '지불처리 시간',
  ilbo_pay_memo               varchar(1000)                                     COMMENT '지불메모',

  ilbo_income_type            int(11)         DEFAULT '0'                       COMMENT '입금 타입( 기본값을 고민하자)',
  ilbo_income_time            datetime                                          COMMENT '입금 시간',
  ilbo_income_memo            varchar(1000)                                     COMMENT '입금메모',

  ilbo_confirm                int(11)         DEFAULT '0'                       COMMENT '승인 - 0:미승인 1:승인  기본값0',
  ilbo_arrival_time           varchar(100)                                      COMMENT '현장 도착시간',
  ilbo_age_limit              int(11)         DEFAULT '0'                       COMMENT '나이제한 - 0: 없음',
  ilbo_bp_limit               int(11)         DEFAULT '0'                       COMMENT '혈압제한 - 0: 제한없음',
  ilbo_bf_yn                  int(11)         DEFAULT '0'                       COMMENT '조식유무 - 1:유 0:무',

  ilbo_memo                   varchar(1000)                                     COMMENT '일보메모',

  reg_date                    datetime                                          COMMENT '등록일시',
  reg_admin                   varchar(1000)   NOT NULL                          COMMENT '등록자',
  mod_date                    timestamp       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP       COMMENT '변경일시',
  mod_admin                   varchar(1000)   NOT NULL                          COMMENT '변경자',

  PRIMARY KEY (company_seq, ilbo_date, ilbo_seq)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT='출역일보';


CREATE TABLE today_worker (
  company_seq                 bigint(20)      NOT NULL                          COMMENT '회사  순번',
  worker_seq                  bigint(20)      NOT NULL                          COMMENT '구직자 순번',

  status_code                 varchar(1000)                                     COMMENT '구직자 출역 상태 코드',
  status_name                 varchar(1000)                                     COMMENT '구직자 출역 상태 명',

  reg_date                    datetime                                          COMMENT '등록일시 - 출근시간',
  reg_admin                   varchar(1000)   NOT NULL                          COMMENT '등록자',
  mod_date                    timestamp       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP       COMMENT '변경일시',
  mod_admin                   varchar(1000)   NOT NULL                          COMMENT '변경자',

  PRIMARY KEY (reg_date, worker_seq)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT='출근자';


CREATE TABLE worker (
  worker_seq                  bigint(20)      NOT NULL AUTO_INCREMENT           COMMENT '구직자 순번',
  company_seq                 bigint(20)      NOT NULL                          COMMENT '회사 순번',
                                
  login_key                   varchar(200)                                       COMMENT '로그인 키',
  worker_name                 varchar(200)    NOT NULL                          COMMENT '근로자명',
  worker_password             varchar(100)                                       COMMENT '패스워드',
                                
  address                     varchar(1000)                                     COMMENT '주소',
  jumin_num                   varchar(100)    NOT NULL                          COMMENT '주민번호',
  phone_num                   varchar(100)                                      COMMENT '핸드폰번호',
  job_kind_code               varchar(1000)                                     COMMENT '전문분야 코드',
  job_kind_name               varchar(1000)                                     COMMENT '전문분야 명',
  tel1                        varchar(100)    NOT NULL                          COMMENT '연락처1',
  tel2                        varchar(100)                                      COMMENT '연락처2',
  tel3                        varchar(100)                                      COMMENT '연락처3',
                                
  memo                        text            CHARACTER SET latin1              COMMENT '메모',
                                
  bank_code                   bigint(20)                                        COMMENT '은행코드 순번',
  bank_name                   varchar(100)                                      COMMENT '은행명',
  bank_account                varchar(50)                                       COMMENT '계좌번호',
  bank_owner                  varchar(100)                                      COMMENT '예금주',
                                
  feature                     varchar(500)                                      COMMENT '특징',

  blood_pressure              int(11)         DEFAULT '0'                       COMMENT '혈압 - 0:혈압없음 >0:혈압수치',
                                
  OSH_yn                      int(11)         DEFAULT '0'                       COMMENT '건설업 기초안전보건교육 이수 여부 - 0:미이수 1:이수',
                                
  jumin_scan_yn               int(11)         DEFAULT '0'                       COMMENT '주민등록증 scan 여부',
  OSH_scan_yn                 int(11)         DEFAULT '0'                       COMMENT '이수증 scan 여부',
                                
  worker_status               int(11)         DEFAULT '0'                       COMMENT '현재 상태 - 0:승인대기 1:승인완료 3:강제중지 4:본인중지',
  ilgaja_status               int(1)          DEFAULT '0'                        COMMENT '일가자 작업 예약 상태 0:본인중지 1:예약받음 2:관리자가 중지 시킴',
                                
  current_latlng              varchar(1000)                                     COMMENT '일하러 가는 주소좌표',
  app_use_status              int(11)                                           COMMENT '앱사용유무 - 0:대기 1:사용 2:관리자중지 3:사용자중지',
  mobile_os                   char(1)         CHARACTER SET latin1              COMMENT '핸드폰 종류 - A:안드로이드 I : 아이폰',
  device_id                   varchar(100)                                      COMMENT '단말기 아이디',
  device_token                varchar(1000)                                     COMMENT '푸쉬토큰',
  app_version                 int(11)                                           COMMENT '앱버젼',
  latest_connection           datetime                                          COMMENT '모바일 마지막 접속 일시',
  mobile_os_version           varchar(500)                                      COMMENT '단말기 os version',
  push_yn                     int(11)                                           COMMENT '푸쉬 사용유무 - 0:app 미설치 1:사용 2:강제중지 3:본인중지',
  sms_auth                    int(11)                                            COMMENT '회원인증 sms',

  reg_date                    datetime                                          COMMENT '등록일시',
  reg_admin                   varchar(1000)   NOT NULL                          COMMENT '등록자',
  mod_date                    timestamp       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP       COMMENT '변경일시',
  mod_admin                   varchar(1000)   NOT NULL                          COMMENT '변경자',

  PRIMARY KEY (worker_seq)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT='구직자';


CREATE TABLE file_info (
  file_seq                    bigint(11)      NOT NULL AUTO_INCREMENT           COMMENT '파일 순번',
  file_name                   varchar(500)                                      COMMENT '저장파일명',
  file_org_name               varchar(500)                                      COMMENT '원본파일명',
  file_path                   varchar(1000)                                     COMMENT '파일경로',

  file_url                    varchar(1000)                                     COMMENT '파일FULL URL',

  file_ext                    varchar(100)                                      COMMENT '확장자명',
  file_size                   bigint(11)                                        COMMENT '파일크기',

  file_rel_seq                bigint(11)                                        COMMENT '게시글 순번',

  file_type                   varchar(100)                                      COMMENT '파일종류 (fileImg, fileVideo...)',
  service_name                varchar(100)                                      COMMENT '테이블명',
  service_type                varchar(100)                                      COMMENT '파일구분',

  file_order int(11)                                                            COMMENT '파일노출순번',

  reg_date                    datetime                                          COMMENT '등록일시',
  reg_admin                   varchar(1000)   NOT NULL                          COMMENT '등록자',
  mod_date                    timestamp       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP       COMMENT '변경일시',
  mod_admin                   varchar(1000)   NOT NULL                          COMMENT '변경자',

  PRIMARY KEY (file_seq)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT='첨부파일';
