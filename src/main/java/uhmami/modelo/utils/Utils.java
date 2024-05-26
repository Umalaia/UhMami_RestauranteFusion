package uhmami.modelo.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import uhmami.modelo.dto.MesaDto;
import uhmami.modelo.dto.MesasBloqueadasDto;
import uhmami.modelo.dto.ReservaDto;
import uhmami.modelo.entities.Cliente;
import uhmami.modelo.entities.Consulta;
import uhmami.modelo.entities.Mesa;
import uhmami.modelo.entities.Reserva;
import uhmami.modelo.service.ClienteServiceImpl;
import uhmami.modelo.service.ConsultaServiceImpl;
import uhmami.modelo.service.MesaServiceImpl;
import uhmami.modelo.service.ReservaServiceImpl;
import uhmami.modelo.service.UsuarioServiceImpl;

@Service
@NoArgsConstructor
public class Utils {
	
	@Autowired
	private ConsultaServiceImpl consultaServiceImpl;
	
	@Autowired
	private ClienteServiceImpl clienteServiceImpl;
	
	@Autowired
	private ReservaServiceImpl reservaServiceImpl;
	
	@Autowired
	private MesaServiceImpl mesaServiceImpl;
	
	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;
	
	@Autowired
	private MesaServiceImpl mesaServiceimpl;
	
	public boolean procesarFormContacto(String nombre, Integer telefono, String comentario) {
		
		if(clienteServiceImpl.buscarPorNombreYTelefono(nombre, telefono) == null) {
			Cliente cliente = new Cliente();
			cliente.setNombre(nombre);
			cliente.setEmail(" ");
			cliente.setApellidos(" ");
			cliente.setTelefono(telefono);
			if(clienteServiceImpl.altaCliente(cliente)) {
				return true;
			} else {
				return false;
			}
		}
		
		Consulta consulta = new Consulta();
		consulta.setCliente(clienteServiceImpl.buscarPorNombreYTelefono(nombre, telefono));
		consulta.setComentario(comentario);
		consulta.setFecha(new Date());
		if(consultaServiceImpl.altaConsulta(consulta)) {
			return true;
		} else {
			return false;
		}
			
	}
	
	public boolean procesarFormReserva(ReservaDto reservaDto) throws ParseException {
		Cliente cliente = new Cliente();
		if(clienteServiceImpl.buscarPorNombreYTelefono(reservaDto.getNombre(), Integer.valueOf(reservaDto.getTelefono())) == null) {
			cliente.setNombre(reservaDto.getNombre());
			cliente.setApellidos(reservaDto.getApellidos());
			cliente.setEmail(reservaDto.getEmail());
			cliente.setTelefono(Integer.valueOf(reservaDto.getTelefono()));
			clienteServiceImpl.altaCliente(cliente);
		} else {
			cliente = clienteServiceImpl.buscarPorNombreYTelefono(reservaDto.getNombre(), Integer.valueOf(reservaDto.getTelefono()));
			cliente.setApellidos(reservaDto.getApellidos());
			cliente.setEmail(reservaDto.getEmail());
			clienteServiceImpl.modificarCliente(cliente);
		}
		Reserva reserva = new Reserva();
		reserva.setId(generarIdReserva(reservaDto));
		reserva.setCliente(cliente);
		reserva.setComensales(Integer.valueOf(reservaDto.getComensales()));
		reserva.setObservaciones(reservaDto.getObservaciones());
		reserva.setHora(reservaDto.getHora());
		//reserva.setUsuario(usuarioServiceImpl.buscarUno("cliente"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaBbdd = LocalDate.parse(reservaDto.getFecha(), formatter);
		reserva.setFecha(fechaBbdd);
		reserva.setMesa(mesaServiceImpl.buscarUna(Integer.valueOf(reservaDto.getMesa())));
		if(reservaServiceImpl.altaReserva(reserva)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String generarIdReserva(ReservaDto reservaDto) {
		String iniciales = String.valueOf(reservaDto.getNombre().charAt(0)) + String.valueOf(reservaDto.getApellidos().charAt(0));
		String id = iniciales + eliminarGuionesYPuntos(reservaDto.getFecha()) + eliminarGuionesYPuntos(reservaDto.getHora());
		return id;
	}
	
	public static String eliminarGuionesYPuntos(String texto) {
        // Reemplaza las comillas dobles y los puntos por una cadena vacía
        String resultado = texto.replaceAll("-", "");
        return resultado;
    }
	

	public MesasBloqueadasDto mesasReservadas(MesaDto mesaDto) {
		List<Integer> mesasBloqueadas = reservaServiceImpl.buscarMesasOcupadas(mesaDto.getFecha(), mesaDto.getHora());
		System.out.println(mesasBloqueadas);
		MesasBloqueadasDto mesasBloqueadasDto = new MesasBloqueadasDto();
		mesasBloqueadasDto.setIdMesas(mesasBloqueadas);
		return mesasBloqueadasDto;
	}

}
