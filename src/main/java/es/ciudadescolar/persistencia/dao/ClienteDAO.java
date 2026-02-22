package es.ciudadescolar.persistencia.dao;

import java.util.List;

import es.ciudadescolar.dominio.modelo.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ClienteDAO {
    
    private final EntityManager em;

    public ClienteDAO(EntityManager em){
        this.em = em;
    }

    public void guardarCliente(Cliente cliente){
        em.persist(cliente);
    }

    public void eliminarCLiente(Cliente cliente){
        em.remove(cliente);
    }

    public Cliente findById(Long id){
        
        return em.find(Cliente.class, id);
    }

    public boolean findByMail(String mail){

        boolean status = true;

        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c WHERE c.mail = : email", Cliente.class);

        query.setParameter("email", mail);
        List<Cliente> resultado = query.getResultList();

        if(resultado.isEmpty()){
            status = false;
        }

        return status;
    }

    public Cliente findClienteByMail(String mail){

        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c WHERE c.mail = : email", Cliente.class);

        query.setParameter("email", mail);
        List<Cliente> resultado = query.getResultList();

        if(resultado.isEmpty()){
            return null;
        }else{
            return resultado.get(0);
        }
        
    }


}
