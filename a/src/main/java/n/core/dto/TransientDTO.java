package n.core.dto;

import java.io.Serializable;

import n.core.dto.support.GenericDTO;

/**
 * @author User
 * 瞬态数据传输对象
 */
public abstract class TransientDTO<PK extends Serializable> extends GenericDTO<PK> {

	private static final long serialVersionUID = 6693846907130500855L;

	@Override
	public PK getId() {
		return null;
	}
	
}
