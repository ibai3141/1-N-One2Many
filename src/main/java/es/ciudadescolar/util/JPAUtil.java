package es.ciudadescolar.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    
    private static final EntityManagerFactory emf;

    private static final Logger LOG = LoggerFactory.getLogger(JPAUtil.class);

    static{
        LOG.debug("creando pool de conexiones...");

        emf = Persistence.createEntityManagerFactory("PersistenciaUsuarios");
    }

    public static EntityManager getEntityManager(){

        LOG.debug("Nueva conexión del pool solicitada");
        return emf.createEntityManager();
    }

    public void close(){
        if(emf.isOpen()){
            emf.close();
            LOG.debug("Se ha cerrado el pool de conexiones con la BD");
        }
    }
}
