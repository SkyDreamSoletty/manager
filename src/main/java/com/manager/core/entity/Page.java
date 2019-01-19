package com.manager.core.entity;

import com.manager.core.util.Util;

public class Page {
	private String _BY;
	private int _LM;
	private int _ST;
	public String get_BY() {
		return _BY;
	}
	public void set_BY(String _BY) {
    	if(Util.sqlValidate(_BY)){
    		throw new RuntimeException("包含非法字符");
    	}
		this._BY = _BY;
	}
	public int get_LM() {
		return _LM;
	}
	public void set_LM(int _LM) {
		this._LM = _LM;
	}
	public int get_ST() {
		return _ST;
	}
	public void set_ST(int _ST) {
		this._ST = _ST;
	}
	public Page(int _LM, int _ST) {
		super();
		this._LM = _LM;
		this._ST = _ST;
	}
	public Page() {
		super();
	}
	
}
