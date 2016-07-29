package br.com.tti.sefaz.persistence;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class EventId implements Serializable {
	private String keyxml;

	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;

	public String getKeyxml() {
		return keyxml;
	}

	public void setKeyxml(String keyxml) {
		this.keyxml = keyxml;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((keyxml == null) ? 0 : keyxml.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventId other = (EventId) obj;
		if (id != other.id)
			return false;
		if (keyxml == null) {
			if (other.keyxml != null)
				return false;
		} else if (!keyxml.equals(other.keyxml))
			return false;
		return true;
	}

}
