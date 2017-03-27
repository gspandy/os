package n.io.protocol;

import java.io.Serializable;

public interface Info extends Serializable  {
	
	boolean isLocalServerHost();
	String  getServerHost();
	
	public String body();
	
}
