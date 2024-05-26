package uhmami.modelo.entities;

import java.io.Serializable;
import java.util.Date;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="consultas")
public class MesaConReserva implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private Mesa idMesa;
	@Id
	private Reserva idReserva;
	private Date fecha;
	private Date hora;

}
