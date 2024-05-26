package uhmami.modelo.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;

import uhmami.modelo.dto.PdfDto;
import uhmami.modelo.entities.Reserva;

public class PdfAdminServiceImpl {
	
	@Autowired
	private ReservaServiceImpl reservaServiceImpl;
	
	static final Paragraph ESPACIO = new Paragraph().add(" ").setFontSize(9f);
	static final String CABECERA_UHMAMI = "classpath:static/assets/img/FondoPdf.jpg";
	static final String FORMATO_FECHA = "dd/MM/yyyy";
	static final String NOMBRE_ARCHIVO = "ReservasUhmami.pdf";
	
	List<Reserva> lista = new ArrayList<>();
	String fechaReserva;
	
	
	public void espacioBlanco(Document document, int espacios) {
		for (int i = 0; i < espacios; i++) {
            document.add(ESPACIO);
        }
	}
	
	public void cabecera(Document document) throws IOException {
		ResourceLoader loader = new DefaultResourceLoader();
		Paragraph cabecera = new Paragraph();
		this.espacioBlanco(document, 2);
		Image logo = new Image(ImageDataFactory.create(loader.getResource(CABECERA_UHMAMI).getURL())).scaleToFit(240, 240)
                .setRelativePosition(0, -25, 0, 0);
		cabecera.add(logo);
		document.add(cabecera);
		this.espacioBlanco(document, 2);
		Paragraph titulo = new Paragraph();
		titulo.add("Reservas para el día: " + this.fechaReserva);
        titulo.setFontSize(15f);
        titulo.setBold().setRelativePosition(0, -35, 0, 0);
        
        
	}
	
	/**
	 * Coge el pdf, lee el número total de páginas y añade un pie de página con la numeración
	 * dentro del documento.
	 * @param pdfBytes
	 * @return
	 * @throws IOException
	 */
	public String piePagina(byte[] pdfBytes) throws IOException {
        try (PdfDocument pdfDoc = new PdfDocument(new PdfReader(new ByteArrayInputStream(pdfBytes)),
                new PdfWriter(NOMBRE_ARCHIVO))) {
            int totalPages = pdfDoc.getNumberOfPages();
            float x = ((pdfDoc.getPage(1).getPageSize().getLeft() + pdfDoc.getPage(1).getPageSize().getRight()) / 2)
                    - 30;
            float y = pdfDoc.getPage(1).getPageSize().getBottom() + 25;
            for (int i = 1; i <= totalPages; i++) {
                PdfPage page = pdfDoc.getPage(i);
                PdfCanvas canvas = new PdfCanvas(page);
                canvas.beginText().setFontAndSize(PdfFontFactory.createFont(StandardFonts.HELVETICA), 9f).moveText(x, y)
                        .showText(String.format("Página %d de %d", i, totalPages)).endText();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] updatedPdfBytes = java.nio.file.Files.readAllBytes(new File(NOMBRE_ARCHIVO).toPath());
        return java.util.Base64.getEncoder().encodeToString(updatedPdfBytes);
    }
	
	public PdfDto generarPdfAdmin(String fecha) throws IOException {
		
		lista = reservaServiceImpl.buscarPorFecha(fecha);
		fechaReserva = new SimpleDateFormat(FORMATO_FECHA).format(fecha);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final Document document = new Document(new PdfDocument(new PdfWriter(outputStream)));

        document.setMargins(25, 25, 25, 25);
        
        this.cabecera(document);
        
        document.close();
        
        PdfDto pdfDto = new PdfDto();
        pdfDto.setFechaGeneracion(fechaReserva);
        pdfDto.setDocumento(this.piePagina(outputStream.toByteArray()));
        
        Path path = Paths.get(NOMBRE_ARCHIVO);
        if (Files.exists(path) && Files.isRegularFile(path)) {
            Files.delete(path);
        }
        
        return pdfDto;
        
	}

}
