const URL_DESTINO = "http://localhost:8087/";
const RECURSO = "login";

function enviarPeticionAsincrona(){
	let xmlHttp = new XMLHttpRequest();
	xmlHttp.onreadystatechange = function (){
		if(this.readyState == 4){
			if(this.status == 200){
				procesarRespuesta(this.responseText);
				window.location.href = "admin"
			} else {
				alert ("Hubo un problema al realizar la petición, por favor intentelo de nuevo");
			}
		}
	};
	xmlHttp.open('POST', URL_DESTINO + RECURSO, true);
	xmlHttp.setRequestHeader('Content-Type', 'application/json');
	
	const username = document.getElementById("username").value;
	const password = document.getElementById("password").value;
	const datos = {
		username: username,
		password: password
	};
	
	xmlHttp.send(JSON.stringify(datos));
}

function procesarRespuesta(responseText){
	const response = JSON.parse(responseText);
	if (response != null){
		sessionStorage.setItem("token", response.jws);
		console.log(sessionStorage);
	}
}