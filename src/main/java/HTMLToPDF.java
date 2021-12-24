import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import org.xhtmlrenderer.simple.XHTMLPanel;
import org.xhtmlrenderer.simple.PDFRenderer;
import org.xhtmlrenderer.simple.ImageRenderer;

import javax.swing.text.html.HTML;

public class HTMLToPDF {

    /*    private static String htmlToXhtml(String inputFile) throws IOException, URISyntaxException {
            //File inputHTML = new File(inputFile);
            String inputHTML = new String(Files.readAllBytes(Paths.get(inputFile)));
            final Document document = Jsoup.parse(inputHTML, "UTF-8");
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            return document.toString();
        }*/

    private static final String GORKEM_LOCAL_PATH= "/Users/gorkemkaraduman/Coding/IBM-Code/HTML2PDF/";
    private static final String ECE_LOCAL_PATH= "";


    public static void ConvertHTMLToPDF(String input_HTML_path, String output_pdf_path){
        AdjustLayout(input_HTML_path);
        try {
            String XHTML = HTMLToXHTML(input_HTML_path);
            XHTMLToPDF(XHTML, output_pdf_path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void AdjustLayout(String input_HTML_path){
        File input = new File(input_HTML_path);
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        doc.select("table[width=800]").attr("width", "100%");
        doc.select("#notesTable").attr("width", "100%") ; // a with href
        doc.select("script").remove(); // remove Javascript
        try { // Write to file
            FileUtils.writeStringToFile(input, doc.outerHtml(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String HTMLToXHTML(String html) throws IOException {
        String inputHTML = new String(Files.readAllBytes(Paths.get(html)));
        Tidy tidy = new Tidy();
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setXHTML(true);
        tidy.setShowWarnings(false);
        tidy.setMakeClean(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputHTML.getBytes("UTF-8"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString("UTF-8");
    }

    private static void XHTMLToPDF(String xhtml, String outFileName) throws IOException {
        File output = new File(outFileName);
        ITextRenderer renderer = new ITextRenderer();
        SharedContext sharedContext = renderer.getSharedContext();
        sharedContext.setPrint(true);
        sharedContext.setInteractive(false);
        sharedContext.setReplacedElementFactory(new ImageReplacedElementFactory());
        sharedContext.getTextRenderer().setSmoothingThreshold(0);
        try {
            renderer.getFontResolver().addFont(GORKEM_LOCAL_PATH + "Butterfly.ttf", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String baseUrl = FileSystems.getDefault()
                    .getPath(GORKEM_LOCAL_PATH)
                    .toUri()
                    .toURL()
                    .toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        renderer.setDocumentFromString(xhtml);
        renderer.layout();
        try (OutputStream os = new FileOutputStream(output)) {
            renderer.createPDF(os);
            System.out.println("PDF creation completed");
        }
    }
}