package uhmami.modelo.dto;

import lombok.Data;

@Data
public class ModificarReservasDto {

	private String idReserva;
	private Integer comensales;
	private String observaciones;
	private String fecha;
	private String hora;
	private Integer idCliente;
	private String nombre;
	private String apellidos;
	private String email;
	private Integer telefono;
}
