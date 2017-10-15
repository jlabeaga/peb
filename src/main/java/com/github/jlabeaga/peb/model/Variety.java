package com.github.jlabeaga.peb.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Variety implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    private Long id;
    
    private String code;

	private String name;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if(this.id == null) {
            return false;
        }

        if (obj instanceof Variety && obj.getClass().equals(getClass())) {
            return this.id.equals(((Variety) obj).id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.id);
        return hash;
    }

	@Override
	public String toString() {
		return "Variety [id=" + id + ", name=" + name + "]";
	}
    
    
    
}
