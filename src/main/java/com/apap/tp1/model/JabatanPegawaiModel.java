package com.apap.tp1.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "jabatan_pegawai")
public class JabatanPegawaiModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "pegawai_id",referencedColumnName = "id", nullable = false)
//    @OnDelete(action = OnDeleteAction.NO_ACTION)
//    private PegawaiModel pegawai;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "jabatan_id",referencedColumnName = "id", nullable = false)
//    @OnDelete(action = OnDeleteAction.NO_ACTION)
//    private JabatanModel jabatan;
}