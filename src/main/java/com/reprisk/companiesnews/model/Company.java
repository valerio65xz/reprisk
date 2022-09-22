package com.reprisk.companiesnews.model;

public class Company {

    private Integer id;
    private String name;

    public Company() {
    }

    public Company(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return id + name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Company company)) {
            return false;
        }

        return (id.intValue() == company.getId().intValue()) && (name.equals(company.getName()));
    }

    @Override
    public String toString(){
        return "" + id + " - " + name;
    }
}
