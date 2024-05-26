function actualizarHref() {
            var fecha = document.getElementById('fecha').value;
            var link = document.getElementById('generarPdfLink');
            link.href = '/generarPdf?fecha=' + fecha;
            link.click();
        }
        
function descargaPdf() {
        if (pdfDto !== null && pdfDto.archivo != undefined) {
          const link = document.createElement('a');
          link.href = 'data:application/pdf;base64,' + pdfDto.archivo;
          link.download = 'ReservasUhmami ' + '.pdf';
          document.body.appendChild(link);
          this._global.loadingOcultar();
          link.click();
          document.body.removeChild(link);
        }
 };
      
  
  