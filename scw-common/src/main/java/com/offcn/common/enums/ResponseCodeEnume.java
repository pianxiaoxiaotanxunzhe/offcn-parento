package com.offcn.common.enums;

public enum ResponseCodeEnume {
	
	SUCCESS(0,"操作成功"),
	FAIL(1,"服务器异常"),
	NOT_FOUND(404,"资源未找到"),
	NOT_AUTHED(403,"无权限，访问拒绝"),
	PARAM_INVAILD(400,"提交参数非法");
	// 上方每一个值都是枚举的对象

	private Integer code;
	private String msg;
	
	// 枚举中所有的构造方法必须全部都是private的
	private ResponseCodeEnume(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
