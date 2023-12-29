package com.nemo.kr.dto;

import java.util.List;



/**
 * 일가자 출근자 관리 DTO
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-26
 */
public class TodayWorkerDTO extends GridDefaultDTO {
  private String company_seq;                               // 소속회사 순번
  private String worker_seq;                                // 구직자 순번

  private String status_code;                               // 구직자 출역 상태 코드
  private String status_name;                               // 구직자 출역 상태 명

  private String company_name;                              // 소속회사 명
  private String worker_name;                               // 구직자 이름


  private String[] sel_worker_seq;                          // 선택된 구직자 순번

  private List<WorkerDTO> list;


  public String getCompany_seq() {
    return company_seq;
  }
  public void setCompany_seq(String company_seq) {
   this.company_seq = company_seq;
  }
  public String getWorker_seq() {
    return worker_seq;
  }
  public void setWorker_seq(String worker_seq) {
   this.worker_seq = worker_seq;
  }


  public String getStatus_code() {
    return status_code;
  }
  public void setStatus_code(String status_code) {
    this.status_code = status_code;
  }
  public String getStatus_name() {
    return status_name;
  }
  public void setStatus_name(String status_name) {
    this.status_name = status_name;
  }


  public String getCompany_name() {
    return company_name;
  }
  public void setCompany_name(String company_name) {
    this.company_name = company_name;
  }
  public String getWorker_name() {
    return worker_name;
  }
  public void setWorker_name(String worker_name) {
    this.worker_name = worker_name;
  }


  public String[] getSel_worker_seq() {
    return sel_worker_seq;
  }
  public void setSel_worker_seq(String[] sel_worker_seq) {
    this.sel_worker_seq = sel_worker_seq;
  }


  public List<WorkerDTO> getList() {
    return list;
  }
  public void setList(List<WorkerDTO> list) {
    this.list = list;
  }

}
