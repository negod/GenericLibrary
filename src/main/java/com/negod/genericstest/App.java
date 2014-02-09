package com.negod.genericstest;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {

        List<Dto> userList = new ArrayList<Dto>();
        Dto setting = new Dto(Setting.class);
        setting.set(Setting.COMPILE, 123);

        Dto user1 = new Dto(User.class);
        user1.set(User.LASTNAME, "Johansson");
        Dto user2 = new Dto(User.class);
        user2.set(User.LASTNAME, "Karlsson");

        userList.add(user1);
        userList.add(user2);

        Dto phone = new Dto(Phone.class);
        phone.set(Phone.HOME, 123456L);
        user1.set(User.PHONE, phone);

        setting.set(Setting.USERS, userList);

        Integer data = setting.get(Setting.COMPILE);
        
        try {
            XmlFileHandler handler = new XmlFileHandler();
            handler.createXml(setting, "TestFile");
            Dto dtoData = handler.parseXmlToDto("TestFile");
            System.out.print("Hello");
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

    }

    public <T> String marshal(T object) {
        try {
            StringWriter stringWriter = new StringWriter();
            JAXBContext jc = JAXBContext.newInstance(object.getClass());
            Marshaller m = jc.createMarshaller();
            m.marshal(object, stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e) {
        }
        return null;
    }

    public <T> T unMarshal(String content, Class<T> clasz) {
        try {
            JAXBContext jc = JAXBContext.newInstance(clasz);
            Unmarshaller u = jc.createUnmarshaller();
            return u.unmarshal(new StreamSource(new StringReader(content)), clasz).getValue();
        } catch (JAXBException e) {
        }
        return null;
    }
}
