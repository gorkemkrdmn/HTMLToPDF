import javax.swing.text.html.HTML;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String html = "/Users/gorkemkaraduman/Coding/IBM-Code/HTML2PDF/HelloWorld.html";
        String invoice = "/Users/gorkemkaraduman/Coding/IBM-Code/HTML2PDF/invoice.html";
        String order_details = "/Users/gorkemkaraduman/Coding/IBM-Code/HTML2PDF/orderdetails.html";
        String untitled = "/Users/gorkemkaraduman/Coding/IBM-Code/HTML2PDF/Untitled.html";
//        HTMLToPDF.ConvertHTMLToPDF(html, "/Users/gorkemkaraduman/Coding/IBM-Code/HTML2PDF/output.pdf");
        HTMLToPDF.ConvertHTMLToPDF(invoice, "/Users/gorkemkaraduman/Coding/IBM-Code/HTML2PDF/invoice.pdf");
//        HTMLToPDF.ConvertHTMLToPDF(untitled, "/Users/gorkemkaraduman/Coding/IBM-Code/HTML2PDF/untitled.pdf");
//        HTMLToPDF.ConvertHTMLToPDF(order_details, "/Users/gorkemkaraduman/Coding/IBM-Code/HTML2PDF/order_details.pdf");
    }
}
