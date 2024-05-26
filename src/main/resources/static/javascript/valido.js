const nombre = document.getElementById("nombrereserva");
const apellido = document.getElementById('apellidoreserva');
const email = document.getElementById("emailreserva");
const telefono = document.getElementById("telefonoreserva");
const comensales = document.getElementById('comensalreserva')
const fecha = document.getElementById('fecha')
const hora = document.getElementById('horaReserva')
const politica = document.getElementById('politica')
const mapa =  document.querySelector("#plano_mesas > article.container")

const formulario = document.getElementById('formReserva')
const warningIcon = document.getElementById("warning-icon");
const errorTel = document.getElementById('errorTel')
var mesaSeleccionada = document.getElementById('mesaSeleccionada')
//Validaciones 
var nomCorrecto = false
var apeCorrecto = false
var emCorrecto = false
var telCorrecto = false
var comenCorrecto = false
var fechaCorrecto = false
var horaCorrecto = false
var polCorrecto = false
var errorE = false
var errorT = false
var mesaCorrecta = false

//Añado un escucha para cada input

nombre.addEventListener('focusout', () => {
    var nom = nombre.value.trim();
    
    if (nom=== "") {
        console.log('estoy en nombre vacio')
               
        nombre.style.color = '#E13600'
        nombre.value = "Introduzca el nombre"
        
        nombre.addEventListener('focusin',() => {
           
            if(nom === ""){
            nombre.value = ''
            nombre.style.color = '#616D69'
            nombre.placeholder = ''
            } 
        })
        nomCorrecto = false

    }
    else{
        console.log('Nombre completado')
        nomCorrecto = true
    }
   
})

//Aviso de apellido vacío
apellido.addEventListener('focusout', () => {
    var ape = apellido.value.trim();
    if (ape=== "") {
        console.log('estoy en apellido vacio') 
        apellido.style.color = '#E13600'
        apellido.value = "Introduzca el apellido"
    }
    else{
        console.log('apellido completado')
        apeCorrecto = true
    }
    apellido.addEventListener('focusin',() => {
        apellido.value = ''
        apellido.style.color = '#616D69'
        apellido.placeholder = ''
    })   
})


//Aviso de email vacío

email.addEventListener('focusout', () => {
    var em = email.value.trim();
    if (em== "") {
        console.log('estoy en email vacio')
        email.style.color = '#E13600'
        email.value = "Introduzca el email"
        email.addEventListener('focusin',() => {
            email.value = ''
            email.style.color = '#616D69'
            email.placeholder = ''
        })      
    }
    else{
        var unaAlerta = 0
        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if(em!= ""){
            if (!emailRegex.test(em)) {
                console.log('no pasa')
                if(unaAlerta == 0){
                    alert('Formato incorrecto')
                    errorE= true
                } 
            }
            else{
                errorE = false
             } 
        }
        }
        if(errorE == false){
            console.log('email compleado')
            emCorrecto = true
        }   

        
})


//aviso de telefono incorrecto
telefono.addEventListener('focusout', () => {
    const tel = telefono.value.trim();
    
    if (tel== "") {
        console.log('estoy en telefono vacio')
             
        telefono.style.color = '#E13600'
        telefono.value = "Introduzca el teléfono"


        telefono.addEventListener('focusin',() => {
            telefono.value = ''
            telefono.style.color = '#616D69'
            telefono.placeholder = ''
        })      
    }
    else{
        var telefonoRegex = /(\+34|0034|34)?[-]*(6|7)[ -]*([0-9][ -]*){8}/;
            if (!telefonoRegex.test(tel)) { //Si no pasa la prueba de telefono
                console.log('no pasa telefono')
                    errorTel.style.fontSize = '20px'
                    errorTel.style.color ='#E13600'
                    errorTel.textContent='Formato teléfono incorrecto'
                    telCorrecto = false
            }
            else{
                errorTel.textContent = ''
                console.log('telefono completado')
                telCorrecto = true
                
        } 
        
    }
})


//Aviso de elegir los comensales

var elegirComensal = 0
comensales.addEventListener('focusin', ()=>{
    
    elegirComensal = 1
    comenCorrecto = true
    console.log('comensal elegido')

})


//No se puede elegir una fecha anterior a la de hoy
var fechaMin = new Date();
    var anio = fechaMin.getFullYear();
    var dia = fechaMin.getDate() + 1;
    var _mes = fechaMin.getMonth(); //viene con valores de 0 al 11
    _mes = _mes + 1; //ahora lo tienes de 1 al 12
    if (_mes < 10) //ahora le agregas un 0 para el formato date
    {
        var mes = "0" + _mes;
    } else {
        var mes = _mes.toString;
    }

    var fecha_minimo = anio + '-' + mes + '-' + dia;

fecha.setAttribute('min',fecha_minimo)
//Evitamos que el usuario no haya dejado los comensales sin elegir 
// y validamos que haya seleccionado una fecha
fecha.addEventListener('focusin', ()=> {
    if(elegirComensal == 0){
        alert('Por favor, dígamos cuantos comensales son.')
        elegirComensal = 1
        comensales.focus()
    }

    if(fecha.value != null){
        console.log('fecha correcto')
        fechaCorrecto = true
    }
    else
    {
    console.log('Fecha sin seleccionar')
    fechaCorrecto = false
    }
})


//Validación de elegir hora

hora.addEventListener('click', () => {
    if(hora.value!=''){
        console.log('hora elegida')
        horaCorrecto = true
    }
    else{
        horaCorrecto = false
    }
})

//Validar el checkeo de las politicas de privacidad
politica.addEventListener('click',()=> {
    
    if(politica.checked == true){
        console.log('Ha aceptado')
        polCorrecto = true
    }
    else
    {
        console.log('No ha aceptado')
        polCorrecto = false
    }
})






//MESAS


var mesa1 = document.getElementById('mesa1')

mesa1.addEventListener('click',() =>{
        mesaSeleccionada.value = 1   
         mesaCorrecta = true
        console.log('mesa 1 eleginada')
        
   
})

var mesa2 = document.getElementById('mesa2')

mesa2.addEventListener('click',() =>{
        
        console.log('mesa 2 eleginada')
        mesaSeleccionada.value = 2  
        mesaCorrecta = true
   
})

var mesa3 = document.getElementById('mesa3')

mesa3.addEventListener('click',() =>{
        
        console.log('mesa 3 eleginada')
        mesaSeleccionada.value = 3  
        mesaCorrecta = true
})

var mesa4 = document.getElementById('mesa4')

mesa4.addEventListener('click',() =>{
        
        console.log('mesa 4 eleginada')
        mesaSeleccionada.value = 4 
        mesaCorrecta = true
   
})

var mesa5 = document.getElementById('mesa5')

mesa5.addEventListener('click',() =>{
        
        console.log('mesa 5 eleginada')
        mesaSeleccionada.value = 5   
        mesaCorrecta = true
   
})

var mesa6 = document.getElementById('mesa6')

mesa6.addEventListener('click',() =>{
        
        console.log('mesa 6 eleginada')
        mesaSeleccionada.value = 6  
        mesaCorrecta = true
   
})

var mesa7 = document.getElementById('mesa7')

mesa7.addEventListener('click',() =>{
        
        console.log('mesa 7 eleginada')
        mesaSeleccionada.value = 7 
        mesaCorrecta = true
})

var mesa8 = document.getElementById('mesa8')

mesa8.addEventListener('click',() =>{
        
        console.log('mesa 8 elegida')
        mesaSeleccionada.value = 8   
        mesaCorrecta = true
   
})

var mesa9 = document.getElementById('mesa9')

mesa9.addEventListener('click',() =>{
        
        console.log('mesa 9 eleginada')
        mesaSeleccionada.value = 9
        mesaCorrecta = true
   
})

var mesa10 = document.getElementById('mesa10')

mesa10.addEventListener('click',() =>{
        
        console.log('mesa 10 eleginada')
        mesaSeleccionada.value = 10
        mesaCorrecta = true
   
})

var mesa11 = document.getElementById('mesa11')

mesa11.addEventListener('click',() =>{
        
        console.log('mesa 11 eleginada')
        mesaSeleccionada.value = 11
        mesaCorrecta = true
   
})

var mesa12 = document.getElementById('mesa12')

mesa12.addEventListener('click',() =>{
        
        console.log('mesa 12 eleginada')
        mesaSeleccionada.value = 12
        mesaCorrecta = true
   
})

var mesa13 = document.getElementById('mesa13')

mesa13.addEventListener('click',() =>{
        
        console.log('mesa 13 eleginada')
        mesaSeleccionada.value = 13
        mesaCorrecta = true
   
})





function validateReservaForm() {
    //Creamos un valor que hasta que no sea validado sea false
    var validado = false
    
    if (nombre.value === "" || apellido.value=="" || email.value=="" || telefono.value=="" ) {
        nombre.focus()
        alert('Faltan campos de rellenar')
        validado = false
    }else{
    if(nomCorrecto === true && 
    apeCorrecto === true &&
    emCorrecto === true &&
    telCorrecto === true &&
    comenCorrecto === true &&
    fechaCorrecto === true &&
    horaCorrecto === true &&
    polCorrecto === true &&
    mesaCorrecta===true ){
        validado = true
        console.log('todo correcto')
        alert('Reserva realizada!')
    }}

      
    return validado;
    }
   
   

