package n.io.protocol;

public class GmInfo implements Info {
	
	private static final long serialVersionUID = -6917968248895544796L;
	private Long rmid;
	private Long uid;
	private Type type;
	private String body;
	
	private String serverHost;

	public enum Type {
		join, leave, lt ,bet
	}

	public Long getRmid() {
		return rmid;
	}

	public void setRmid(Long rmid) {
		this.rmid = rmid;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String body() {
		return this.body;
	}

	@Override
	public boolean isLocalServerHost() {
		return true;
	}

	@Override
	public String getServerHost() {
		return serverHost;
	}

}
