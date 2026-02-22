package es.ciudadescolar;

import es.ciudadescolar.servicio.ClienteService;
import es.ciudadescolar.servicio.PedidoService;

/*Crea un programa en Java haciendo uso de ORM Hibernate + JPA y arquitectura con separación de responsabilidades que cumpla:
La aplicación persigue la gestión de clientes y los pedidos que realizan los clientes de El Corte Inglés.

La aplicación debe implementar la siguiente lógica:
	a) Crear un nuevo cliente
	b) Añadir pedido a cliente
	c) Eliminar cliente junto con todos sus pedidos
	d) Eliminar un pedido de un cliente
	e) Generar un reporte personalizado de todos los clientes junto con el importe total gastado por cada uno:
			nombre 		| email 			| total_gastado
			Jacobo Sanz |jsanz@empresa.es	| 151.0 */
public class Main {
    public static void main(String[] args) {


		ClienteService clienteService = new ClienteService();
		PedidoService pedidoService = new PedidoService();


		//clienteService.altaCliente("ibai", "ibai@gmail.com");


		//pedidoService.altaPedido(100, "ibai@gmail.com");

		//pedidoService.eliminarPedidos("ibai@gmail.com");

		//pedidoService.eliminarPedido("jsanz@empresa.es", 3L);

		pedidoService.reporte();



        
    }
}