package uhmami.modelo.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDto {
	private String nombre;
	private String apellidos;
	private String email;
	private String telefono;
	private String comensales;
	private String fecha;
	private String hora;
	private String mesa;
	private String observaciones;
}
