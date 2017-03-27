package com.hitler.demo.code.rule;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h1>代码示例</h1>
 * 这个类是为了演示代码规范.
 * 所以,我是一只标准的五脏俱全的麻雀!
 * @author XXX
 * @date 2016/07/04 18:30:00
 * @version 1.0
 */
public class CodeSimple extends Thread implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected static Logger log = LoggerFactory.getLogger(CodeSimple.class);
	public static final String FINAL_VAR = "IS_LINE";
	
	private String varName;
	 
	@Override
	public String toString() {
		//一个重写的方法
		return varName;
	}
	
	/**
	 * 说明方法的作用
	 * @param anParm 参数的内容
	 * @return       返回值说明
	 */
	public boolean aMethod(String anParm){
		log.debug("###传过来的参数是:"+anParm);
		return false;
	}
	
	public void setVarName(String varName) {
		this.varName = varName;
	}

}
