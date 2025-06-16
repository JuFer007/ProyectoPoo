package APP_RESTAURANTE.CLASES;
import APP_RESTAURANTE.CONEXIONBD.ICRUDCliente;
import APP_RESTAURANTE.CONEXIONBD.ICRUDPedido;
import APP_RESTAURANTE.CONEXIONBD.ICRUDdetallePedido;
import APP_RESTAURANTE.CONEXIONBD.ICRUDpago;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ComprobantePDF {
    private Document documento;
    private FileOutputStream fileOutputStream;
    private String numeroComprobante;
    private double IGV = 0.18;

    //Fuente titulo y parrafos
    Font fuenteTitulo = FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, Font.BOLD);
    Font fuenteParrafo = FontFactory.getFont(FontFactory.HELVETICA);
    Font fuenteComponentes = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD);


    //Metodo para crear el documento
    private void CrearDocumento() {
        try {
            documento = new Document(PageSize.A4, 35, 30, 50, 50);

            //Archivo pdf a generar
            String ruta = System.getProperty("user.home") + "\\Desktop\\COMPROBANTES DE PAGO";
            fileOutputStream = new FileOutputStream(ruta + "\\ComprobanteDePago" +numeroComprobante+ ".pdf");

            //Obtener instancia para escribir datos
            PdfWriter.getInstance(documento, fileOutputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo para abrir el documento
    private void AbrirDocumento() {
        documento.open();
    }

    //Metodo para agregar titulo
    private void agregarTitulo(String titulo) {
        try {
            PdfPTable tablaTitulo = new PdfPTable(1);
            PdfPCell celda = new PdfPCell(new Phrase(titulo, fuenteTitulo));
            celda.setColspan(5);
            celda.setBorderColor(BaseColor.WHITE);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaTitulo.addCell(celda);
            documento.add(tablaTitulo);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo para agregar parrafo al documento
    private void agregarParrafo(String texto) {
        try {
            Paragraph parrafo = new Paragraph(texto, fuenteParrafo);
            documento.add(parrafo);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo para agregar saltos de linea
    private void agregarSaltosDeLinea() {
        try {
            Paragraph saltosLinea = new Paragraph();
            saltosLinea.add(new Phrase(Chunk.NEWLINE));
            saltosLinea.add(new Phrase(Chunk.NEWLINE));
            documento.add(saltosLinea);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    //Agregar tabla detalle de pedido segun id
    private void agregarTablaDetallePedido(int idPedido) {
        try {
            PdfPTable tablaDetalle = new PdfPTable(6);
            tablaDetalle.setWidthPercentage(100);
            tablaDetalle.setWidths(new float[]{2, 2, 3, 2, 2, 2});

            Font fuenteEncabezado = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            PdfPCell celdaEncabezado;
            String[] encabezadosTabla = {"ID Pedido", "Número de Plato", "Nombre de Plato", "Cantidad", "Precio Unitario", "SubTotal"};
            for (String encabezado : encabezadosTabla) {
                celdaEncabezado = new PdfPCell(new Phrase(encabezado, fuenteEncabezado));
                celdaEncabezado.setHorizontalAlignment(Element.ALIGN_CENTER);
                celdaEncabezado.setBackgroundColor(BaseColor.LIGHT_GRAY);
                celdaEncabezado.setPadding(8);
                tablaDetalle.addCell(celdaEncabezado);
            }

            ICRUDdetallePedido icrudDetallePedido = new ICRUDdetallePedido();
            ArrayList<DetallePedido> listaDetalles = icrudDetallePedido.mostrarDetallePedidoParaComprobante(idPedido);
            for (DetallePedido detalle : listaDetalles) {
                tablaDetalle.addCell(new PdfPCell(new Phrase(String.valueOf(detalle.getIdPedido()))));
                tablaDetalle.addCell(new PdfPCell(new Phrase(String.valueOf(detalle.getNumeroPlato()))));
                tablaDetalle.addCell(new PdfPCell(new Phrase(String.valueOf(detalle.getNombrePlato() != null ? detalle.getNombrePlato() : "N/A"))));
                tablaDetalle.addCell(new PdfPCell(new Phrase(String.valueOf(detalle.getCantidad()))));
                tablaDetalle.addCell(new PdfPCell(new Phrase(String.valueOf(detalle.getPrecioUnitario()))));
                tablaDetalle.addCell(new PdfPCell(new Phrase(String.valueOf(detalle.getSubTotal()))));
            }
            documento.add(tablaDetalle);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo para obtener el total del pedido
    private void agregarTotal(String texto) {
        try {
            Paragraph parrafo = new Paragraph();
            parrafo.add(new Phrase(texto, fuenteComponentes));
            parrafo.setAlignment(Element.ALIGN_RIGHT);
            documento.add(parrafo);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo para cerrar el documento
    private void cerrarDocumento() {
        documento.close();
    }

    private String generarNumeroDeComprobante() {
        int digito1 = (int)(Math.random() * 10);
        int digito2 = (int)(Math.random() * 10);
        int digito3 = (int)(Math.random() * 10);
        int digito4 = (int)(Math.random() * 10);

        String numeroBoleta = "" + digito1 + digito2 + digito3 + digito4;
        return numeroBoleta;
    }

    //Metodo para agregar una linea de separacion
    private void agregarLineaSeparacion() {
        try {
            LineSeparator linea = new LineSeparator();
            linea.setLineColor(BaseColor.BLACK);
            documento.add(linea);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo para agregar la imagen
    private void agregarImagen() {
        try {
            String rutaImange = "C:\\Users\\JUNIOR\\Downloads\\Nueva carpeta (19)\\POO - PROYECTO FINAL\\PROYECTO FINAL - POO\\src\\APP_RESTAURANTE\\IMAGENES\\LOGO SISTEMA.png";
            Image imagen = Image.getInstance(rutaImange);
            imagen.scaleToFit(150, 150);
            imagen.setAlignment(Element.ALIGN_RIGHT);
            documento.add(imagen);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    //Calcular IGV
    CalcularIGV calcularIGV = ((precioTotal, porcentajIGV) -> precioTotal * IGV);

    //Generar comprobante de pago
    public void generarComprobanteDePagoPDF(int idPedido, String DNICliente) {
        try {
            numeroComprobante = generarNumeroDeComprobante();
            ICRUDCliente icrudCliente = new ICRUDCliente();
            ICRUDPedido icrudPedido = new ICRUDPedido();
            ICRUDpago icruDpago = new ICRUDpago();

            CrearDocumento();
            AbrirDocumento();

            agregarTitulo("Comprobante de Pago N°" + numeroComprobante);
            agregarImagen();
            agregarParrafo("Cliente: " + icrudCliente.nombreCliente(idPedido));
            agregarParrafo("DNI: " + DNICliente);
            agregarParrafo("Fecha: " + icrudPedido.ObtenerFechaPedido(idPedido));
            agregarParrafo("Hora: " + icrudPedido.ObtenerHoraPedido(idPedido));
            agregarParrafo("Metodo de Pago: " + icruDpago.obtenerMetodoDePago(idPedido));
            agregarSaltosDeLinea();
            agregarTablaDetallePedido(idPedido);
            agregarSaltosDeLinea();
            agregarLineaSeparacion();

            double subTotalPedido = icrudPedido.ObtenerTotalPedido(idPedido);
            double igvCalculado = calcularIGV.calcularIGV(subTotalPedido, 18);
            double total = subTotalPedido + igvCalculado;

            agregarParrafo("Sub total pedido: " + String.format("%.2f", subTotalPedido));
            agregarParrafo("IGV: " + String.format("%.2f", igvCalculado));

            agregarTotal("Total: " + String.format("%.2f", total));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        cerrarDocumento();
    }
}