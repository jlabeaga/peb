package com.github.jlabeaga.peb.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Country implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    private String id;
    
    private String name;
    
    public String getId() {
        return id;
    }

    protected void setId(String id) {
        this.id = id;
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

        if (obj instanceof Country && obj.getClass().equals(getClass())) {
            return this.id.equals(((Country) obj).id);
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
		return "Country [id=" + id + ", name=" + name + "]";
	}
    
    
    
}
