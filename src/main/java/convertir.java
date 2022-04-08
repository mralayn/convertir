import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@XmlRootElement(name = "convertir")
public class convertir {

    public final static String  XML_FILE    = "ejemplo.xml";
    public final static String  JSON_FILE   = "ejemplo.json";

    public static convertir fromJson(InputStream in) {
        Gson gson = new GsonBuilder().create();
        convertir result = gson.fromJson(new InputStreamReader(in), convertir.class);
        return result;
    }

    public static convertir fromXML(InputStream in) throws Exception {
        JAXBContext context = JAXBContext.newInstance(convertir.class);
        Unmarshaller um = context.createUnmarshaller();
        return (convertir) um.unmarshal(in);
    }

    public static void main(String[] args) throws Exception {

        convertir f = new convertir("manzana", "rojo", "dulce");
        convertir f2 = new convertir("pera", "verde", "dulce");

        System.out.println(f.toXML());
        System.out.println(f2.toJSON());

        f.saveXML(new FileOutputStream(new File(XML_FILE)));
        f2.saveJSON(new FileOutputStream(new File(JSON_FILE)));

        convertir f3 = convertir.fromXML(new FileInputStream(new File(XML_FILE)));
        System.out.println(f3.toJSON());

        convertir f4 = convertir.fromJson(new FileInputStream(new File(JSON_FILE)));
        System.out.println(f4.toXML());

    }

    private String  name;
    private String  color;
    private String  taste;

    public convertir() {

    }

    public convertir(final String name, final String color, final String taste) {
        this.name = name;
        this.color = color;
        this.taste = taste;
    }


    public final String getColor() {
        return this.color;
    }

    public final String getName() {
        return this.name;
    }

    public final String getTaste() {
        return this.taste;
    }

    public void saveJSON(OutputStream out) throws IOException {
        GsonBuilder gb = new GsonBuilder();
        gb.setPrettyPrinting();
        gb.disableHtmlEscaping();
        Gson gson = gb.create();
        PrintWriter writer = new PrintWriter(out);
        gson.toJson(this, writer);
        writer.flush();
        writer.close();
    }

    public void saveXML(OutputStream out) throws Exception {
        JAXBContext context = JAXBContext.newInstance(convertir.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(this, out);
    }


    public final void setColor(String color) {
        this.color = color;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final void setTaste(String taste) {
        this.taste = taste;
    }

    public String toJSON() throws IOException {
        GsonBuilder gb = new GsonBuilder();
        gb.setPrettyPrinting();
        gb.disableHtmlEscaping();
        Gson gson = gb.create();
        return gson.toJson(this, convertir.class);
    }

    public String toXML() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        JAXBContext context = JAXBContext.newInstance(convertir.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(this, out);
        return out.toString();
    }

}
