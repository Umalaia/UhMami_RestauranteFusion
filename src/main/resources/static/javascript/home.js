
const foto = document.getElementById('foto_resena')
const p = document.getElementById('textoResena')
const imagenes = [
    '../static/assets/img/fotoResena.png',
    '../static/assets/img/fotoResena2.png',
]
const textoResena = [
    'Desde el primer bocado hasta el último, cada plato era una obra maestra de sabores sorprendentes y presentación impecable. El  ambiente acogedor y el servicio atento hicieron que mi experiencia fuera inolvidable. ¡Definitivamente volveré pronto!',
    'Todo realmente increíble. Gratamente sorprendido con la comida del gran chef Ezra.'
]

let indiceActual = 0
let indiceActual2 = 0
const puntos = document.getElementById('puntos')
puntos.addEventListener('click',()=>{
    console.log('click')

    // Añadimos la clase fade-out para iniciar la animación de desvanecimiento
    foto.classList.add('fade-out')
    p.classList.add('fade-out')

    setTimeout(() => {
    indiceActual = (indiceActual +1) % imagenes.length
    foto.src = imagenes[indiceActual]
    indiceActual2 = (indiceActual2 +1) % textoResena.length
    p.textContent = textoResena[indiceActual2]

     // Eliminamos la clase fade-out y añadimos fade-in para la animación de aparición
     foto.classList.remove('fade-out')
     p.classList.remove('fade-out')

     foto.classList.add('fade-in')
     p.classList.add('fade-in')
    }, 500)

    foto.classList.remove('fade-in')
     p.classList.remove('fade-in')

})