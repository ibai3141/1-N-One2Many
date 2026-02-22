package es.ciudadescolar.servicio;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.dominio.modelo.Cliente;
import es.ciudadescolar.dominio.modelo.Pedido;
import es.ciudadescolar.persistencia.dao.ClienteDAO;
import es.ciudadescolar.persistencia.dao.PedidoDAO;
import es.ciudadescolar.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class PedidoService {

    private static final Logger LOG = LoggerFactory.getLogger(PedidoService.class);


    public void altaPedido(double precio, String mail){

        EntityManager em = JPAUtil.getEntityManager();

        EntityTransaction trans = em.getTransaction();


        try {
            trans.begin();

            ClienteDAO clienteDAO = new ClienteDAO(em);

            Cliente cliente = clienteDAO.findClienteByMail(mail);


            if(cliente == null){
                LOG.info("el cliente no existe");
            }else{
                Pedido nuevoPedido = new Pedido(precio, cliente);

                PedidoDAO pedidoDAO = new PedidoDAO(em);
                pedidoDAO.guardarPedido(nuevoPedido);

                LOG.info("se ha guardado el pedido  {}", nuevoPedido.toString());
            }
            
            trans.commit();
        } catch (Exception e) {

            LOG.error("error durante el alta del pedido "+ e.getMessage());
            if(trans.isActive() && trans!= null){
                trans.rollback();
                LOG.info("se hizo rollback");
            }
            throw e;
        }finally{

            if(em.isOpen() && em!=null){
                em.close();
                LOG.info("se cierra el em");
            }
        }

    }


    public void eliminarPedidos(String mail){

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();

            ClienteDAO clienteDAO = new ClienteDAO(em);

            Cliente buscado = clienteDAO.findClienteByMail(mail);

            if(buscado == null){
                LOG.info("el cliente no existe");
            }else{

                PedidoDAO pedidoDAO = new PedidoDAO(em);

                pedidoDAO.eliminarPedidos(buscado);
                LOG.info("se han borrado los pedidos de {}", buscado.getName());

                clienteDAO.eliminarCLiente(buscado);
                LOG.info("se ha eiliminado el ciente {}" ,buscado.getName());
            }

            trans.commit();
        } catch (Exception e) {
            LOG.error("error al borrar los pedidos "+ e.getMessage());
            if(trans.isActive() && trans!= null){
                trans.rollback();
                LOG.info("se hizo rollback");
            }

            throw e;
        }finally{

            try {
                if(em.isOpen() && em!= null){
                    em.close();
                    LOG.info("se cerro el em");
                }
            } catch (Exception e) {
                LOG.error("no se cerro el em " + e.getMessage());
            }
        }

    }


    public void eliminarPedido(String mail, Long id){

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();

            ClienteDAO clienteDAO = new ClienteDAO(em);

            Cliente buscado = clienteDAO.findClienteByMail(mail);

            if(buscado == null){
                LOG.info("el cliente no existe");
            }else{

                PedidoDAO pedidoDAO = new PedidoDAO(em);

                Pedido pedido = pedidoDAO.findbyId(id);

                if(pedido != null){
                    pedidoDAO.eliminarPedido(pedido);
                    LOG.info("se eilimno el pedido " + pedido.toString());
                }
            }

            trans.commit();
        } catch (Exception e) {
            LOG.error("error al borrar los pedidos "+ e.getMessage());
            if(trans.isActive() && trans!= null){
                trans.rollback();
                LOG.info("se hizo rollback");
            }

            throw e;
        }finally{

            try {
                if(em.isOpen() && em!= null){
                    em.close();
                    LOG.info("se cerro el em");
                }
            } catch (Exception e) {
                LOG.error("no se cerro el em " + e.getMessage());
            }
        }

    }
    

    public void reporte(){
        EntityManager em = JPAUtil.getEntityManager();


        try{

            PedidoDAO pedidoDAO = new PedidoDAO(em);

            List<Object[]> lista = pedidoDAO.reporte();

            for(Object[] i : lista){

                String nombre = (String) i[0];
                String mail = (String) i[1];
                double precio = (double) i[2];

                LOG.info("{} | {} | {}" , nombre, mail, precio);

            }

        }finally{

             try {
                
                if(em != null && em.isOpen()){
                    em.close();
                    LOG.info("se ha cerrado el entityManager");
                }
            } catch (Exception e) {
                LOG.error("Error cerrando EntityManager: "+ e.getMessage());
            }
        }
    }
}
