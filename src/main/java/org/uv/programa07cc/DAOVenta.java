/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.programa07cc;

import java.util.Iterator;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DAOVenta implements IDAOGeneral<Venta, Integer>{

    @Override
    public Venta create(Venta p) {
        Session session= HibernateUtil.getSession();
        Transaction t= session.beginTransaction();
        
        session.save(p);
        for (Iterator<DetalleVenta> iterator = p.getDetalle().iterator(); iterator.hasNext();) {
            DetalleVenta det = iterator.next();
            session.save(det);
        }
        
        t.commit();
        session.close();
        return p;
    }

    @Override
    public boolean delete(Integer id) {
        Session session= HibernateUtil.getSession();
        Transaction t= session.beginTransaction();
        //Venta v=
        boolean res=false;
        Venta venta=findById(id);
        if(venta==null){
            res= false;
        }
        else{
            Query q = session.createQuery("delete DetalleVenta where idVenta ="+id);
            q.executeUpdate();
            session.delete(venta);
            res= true;
        }
        t.commit();
        session.close();
        return res;
    }

    @Override
    public Venta update(Integer id, Venta p) {
        Session session=HibernateUtil.getSession();
        Transaction t=session.beginTransaction();
        Venta vent=session.get(Venta.class, id);
        vent.setFecha(p.getFecha());
        vent.setMonto(p.getMonto());
        vent.setDetalle(p.getDetalle());
        session.update(vent);
        
        if (vent != null && p.getDetalle() != null) {;
            Query q = session.createQuery("delete DetalleVenta where idVenta ="+id);
            q.executeUpdate();
            for (DetalleVenta det: vent.getDetalle()) {
                //DetalleVenta det = iterator.next();
                det.setVenta(vent);
                session.save(det);
            }

        }
        t.commit();
        session.close();
        return vent;
    }

    @Override
    public List<Venta> findAll() {
        List<Venta> ventas=null;
        Session session = HibernateUtil.getSession();
        Transaction t = session.beginTransaction();
        ventas = session.createQuery("from Venta").list();
        for (Venta venta : ventas) {
            List<DetalleVenta> detalle = null;
            detalle = session.createQuery("from DetalleVenta where idVenta=" + venta.getIdVenta()).list();
            for (DetalleVenta detalleVenta : detalle) {
                venta.getDetalle().add(detalleVenta);
            }
        }
        t.commit();
        return ventas;
    }

    @Override
    public Venta findById(Integer id) {
        Session session=HibernateUtil.getSession();
        Transaction t=session.beginTransaction();
        List<DetalleVenta> detalle=null;
        detalle=session.createQuery("from DetalleVenta where idVenta=" + id).list();
        Venta vent=session.get(Venta.class, id);
        
        if(vent!=null){
            for (DetalleVenta detalleVenta : detalle) {
                vent.getDetalle().add(detalleVenta);
            }
            t.commit();
            session.close();
            return vent;
        }
        else{
            return null;
        }
    }
    
}
