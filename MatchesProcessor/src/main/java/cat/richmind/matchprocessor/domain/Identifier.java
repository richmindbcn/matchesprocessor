package cat.richmind.matchprocessor.domain;

import java.io.Serializable;

public class Identifier implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	public Identifier() {
		id = "";
	}
	
	public Identifier(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Identifier [id=" + id + "]";
	}
}