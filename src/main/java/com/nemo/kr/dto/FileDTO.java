package com.nemo.kr.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 파일정보 DTO
 * @author nemo
 *
 */
public class FileDTO extends GridDefaultDTO {

	private String file_seq;		// 파일 시퀀스(식별 ID) 추가
  private String file_name;
	private String file_org_name = null;
	private String file_thum_name = null;
	private String file_path = null;
  private String file_url = null;
  private String file_ext = null;
	private long file_size = 0;
  private String file_rel_seq;
  private String file_type;
  private String file_order;

  private String service_type;
  private String service_code;
  private String service_seq;


  private String status = null;
	private String fileTagName = null;
	private String fileMenuDiv = null;
	private String regUserSeq;


	private String[] fileSeqArr;


  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }


  public String getFile_seq() {
    return file_seq;
  }
  public void setFile_seq(String file_seq) {
    this.file_seq = file_seq;
  }
  public String getFile_name() {
    return file_name;
  }
  public void setFile_name(String file_name) {
    this.file_name = file_name;
  }
  public String getFile_org_name() {
    return file_org_name;
  }
  public void setFile_org_name(String file_org_name) {
    this.file_org_name = file_org_name;
  }
  public String getFile_thum_name() {
    return file_thum_name;
  }
  public void setFile_thum_name(String file_thum_name) {
    this.file_thum_name = file_thum_name;
  }
  public String getFile_path() {
    return file_path;
  }
  public void setFile_path(String file_path) {
    this.file_path = file_path;
  }
  public String getFile_url() {
    return file_url;
  }
  public void setFile_url(String file_url) {
    this.file_url = file_url;
  }

  public String getFile_ext() {
    return file_ext;
  }
  public void setFile_ext(String file_ext) {
    this.file_ext = file_ext;
  }
  public long getFile_size() {
    return file_size;
  }
  public void setFile_size(long file_size) {
    this.file_size = file_size;
  }

  public String getFile_rel_seq() {
    return file_rel_seq;
  }
  public void setFile_rel_seq(String file_rel_seq) {
    this.file_rel_seq = file_rel_seq;
  }

  public String getFile_type() {
    return file_type;
  }
  public void setFile_type(String file_type) {
    this.file_type = file_type;
  }
  public String getFile_order() {
    return file_order;
  }
  public void setFile_order(String file_order) {
    this.file_order = file_order;
  }


  public String getService_type() {
    return service_type;
  }
  public void setService_type(String service_type) {
    this.service_type = service_type;
  }
  public String getService_code() {
    return service_code;
  }
  public void setService_code(String service_code) {
    this.service_code = service_code;
  }
  public String getService_seq() {
    return service_seq;
  }
  public void setService_seq(String service_seq) {
    this.service_seq = service_seq;
  }


  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public String getFileTagName() {
    return fileTagName;
  }
  public void setFileTagName(String fileTagName) {
    this.fileTagName = fileTagName;
  }
  public String getFileMenuDiv() {
    return fileMenuDiv;
  }
  public void setFileMenuDiv(String fileMenuDiv) {
    this.fileMenuDiv = fileMenuDiv;
  }
  public String getRegUserSeq() {
    return regUserSeq;
  }
  public void setRegUserSeq(String regUserSeq) {
    this.regUserSeq = regUserSeq;
  }


  public String[] getFileSeqArr() {
    return fileSeqArr;
  }
  public void setFileSeqArr(String[] fileSeqArr) {
    this.fileSeqArr = fileSeqArr;
  }

}
