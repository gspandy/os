package com.wise.core.dto;

import java.io.Serializable;

import com.wise.core.dto.support.GenericDTO;

/**
 * @author User
 * 持久数据传输对象
 */
public abstract class PersistentDTO<PK extends Serializable> extends GenericDTO<PK> {

	private static final long serialVersionUID = -7604988709496150282L;

}
