package uhmami.modelo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import uhmami.modelo.dto.MesaDto;
import uhmami.modelo.dto.MesasBloqueadasDto;
import uhmami.modelo.utils.Utils;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@Autowired
	private Utils utils;
	
	
	@PostMapping("/reservas/mesasBloqueadas")
	public MesasBloqueadasDto mostrarReservas(@RequestBody MesaDto mesaDto) {

		return utils.mesasReservadas(mesaDto);
	}
	
}
