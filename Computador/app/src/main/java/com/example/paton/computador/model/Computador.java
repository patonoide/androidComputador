package com.example.paton.computador.model;


public class Computador {

    private Integer id_computador;
    private String marca;
    private String cpu;
    private String ram;
    private String hd;




    public Computador() {
        super();
    }

    public Integer getId_computador() {
        return id_computador;
    }
    public String getHd() {
        return hd;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }

    public void setId_computador(Integer id_computador) {
        this.id_computador = id_computador;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }
}



