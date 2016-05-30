package ua.kiev.prog;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientServerXMLParser {
    public static <T> T jaxbXMLReader(InputStream is, Class<T> cls) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(cls);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            T obj = (T) unmarshaller.unmarshal(is);
            return obj;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> void jaxbXMLWriter(OutputStream os, T obj) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(obj, os);
            marshaller.marshal(obj, sw);
            System.out.println("[" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date())+ "] Marshalling " + obj.getClass().getName() + ". XML length " + sw.toString().length());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
//        return sw.toString();
    }
}
