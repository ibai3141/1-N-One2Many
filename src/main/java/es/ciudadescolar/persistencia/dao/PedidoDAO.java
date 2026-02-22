package es.ciudadescolar.persistencia.dao;

import java.util.List;

import es.ciudadescolar.dominio.modelo.Cliente;
import es.ciudadescolar.dominio.modelo.Pedido;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class PedidoDAO {

    private final EntityManager em;

    public PedidoDAO(EntityManager em){
        this.em = em;
    }

    public Pedido findbyId(Long id){
        return em.find(Pedido.class, id);
    }

    public void eliminarPedido(Pedido pedido){
        em.remove(pedido);
    }


    public void guardarPedido(Pedido pedido){
        em.persist(pedido);
    }


    public void eliminarPedidos(Cliente cliente) {
    em.createQuery("DELETE FROM Pedido p WHERE p.client = :eliminar")
      .setParameter("eliminar", cliente)
      .executeUpdate();
    }

    public Pedido findByCliente(Cliente cliente){

        TypedQuery<Pedido> consulta = em.createQuery("SELECT p FROM Pedido p WHERE p.client =: id",Pedido.class);
        List<Pedido> resultado = consulta.getResultList();

        if(resultado != null){
            return resultado.get(0);
        }else{
            return null;
        }

    }

    public List<Object[]> reporte(){
        String jpql = "SELECT p.client.name, p.client.mail, SUM(p.price) FROM Pedido p GROUP BY p.client.id";
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);

        return query.getResultList();
    }

    
    
}
