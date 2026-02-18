package es.ciudadescolar.servicio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.dominio.modelo.Cliente;
import es.ciudadescolar.persistencia.dao.ClienteDAO;
import es.ciudadescolar.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ClienteService {

    private static final Logger LOG = LoggerFactory.getLogger(ClienteService.class);
    

    public void altaCliente(String nombre, String mail){

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        Cliente nuevoCliente= null;

        try {

            trans.begin();
            ClienteDAO clienteDAO = new ClienteDAO(em);

            if(!clienteDAO.findByMail(mail)){

                nuevoCliente = new Cliente(nombre, mail);

                clienteDAO.guardarCliente(nuevoCliente);

                LOG.debug("se ha dado de alta a " + nuevoCliente);
            }else{
                LOG.info("este usuario ya esta dado de alta");
            }

            trans.commit();

            
        } catch (Exception e) {
            LOG.error("error durante el alta del cliente "+ e.getMessage());

            if(trans.isActive() && trans != null){
                trans.rollback();
                LOG.info("se ha hecho rollback");
            }

            throw e;
        }finally{

            try {
                if(em.isOpen() && em != null){
                    em.close();
                    LOG.info("se ha cerrado el em");
                }
            } catch (Exception e) {
                LOG.error("Error cerrando EntityManager:: "+ e.getMessage());
            }
        }
    }
}
