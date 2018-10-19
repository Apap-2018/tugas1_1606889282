package com.apap.tp1.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "provinsi")
public class ProvinsiModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "presentase_tunjangan", nullable = false)
    private double presentaseTunjangan;

    @OneToMany(mappedBy = "provinsi", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<InstansiModel> instansiList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getPresentaseTunjangan() {
        return presentaseTunjangan;
    }

    public void setPresentaseTunjangan(double presentaseTunjangan) {
        this.presentaseTunjangan = presentaseTunjangan;
    }

    public List<InstansiModel> getInstansiList() {
        return instansiList;
    }

    public void setInstansiList(List<InstansiModel> instansiList) {
        this.instansiList = instansiList;
    }
}