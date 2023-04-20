/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.programa07cc;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVenta")
    private int idVenta;
    @Column(name="fecha")
    private Date fecha;
    @Column(name="monto")
    private double monto;
    
    //El fetch lazy fuerza a hacer un inner join despues
    //Se refiere a que no carga todas las tablas alas que se relacione con una foreign key
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "venta")
    
    private Set<DetalleVenta> detalle;

    public Venta() {
        detalle= new HashSet<>();
    }

    public Set<DetalleVenta> getDetalle() {
        return detalle;
    }

    public void setDetalle(Set<DetalleVenta> detalle) {
        this.detalle = detalle;
    }
    

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
    
    
}
