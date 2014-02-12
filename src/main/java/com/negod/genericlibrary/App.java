package com.negod.genericlibrary;

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
        userList.add(getUser());
        userList.add(getUser());

        Dto setting = new Dto(Setting.class);
        setting.set(Setting.COMPILE, 123);
        setting.set(Setting.COMPILE_PATH, "CompilePath");
        setting.set(Setting.DEPLOY_PATH, "DeployPath");
        setting.set(Setting.UNZIP, "UnZipPath");
        setting.set(Setting.USERS, userList);
        setting.set(Setting.ZIP_PATH, "ZipPath");

        try {
            XmlFileHandler handler = new XmlFileHandler();
            handler.createXml(setting, "TestFile");
            Dto dtoData = handler.parseXmlToDto("TestFile");
            handler.createXml(dtoData, "TestFile2");
            System.out.print("Hello");
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Dto getUser() {
        Dto dto = new Dto(User.class);
        dto.set(User.LASTNAME, "Johansson");
        dto.set(User.PRENAME, "Joakim");
        dto.set(User.PHONE, getPhone());
        return dto;
    }

    public static Dto getPhone() {
        Dto dto = new Dto(Phone.class);
        dto.set(Phone.HOME, "0522 - 18087");
        dto.set(Phone.MOBILE, "0704 - 101192");
        dto.set(Phone.PHONEPHONE, getPhonePhone());
        return dto;
    }

    public static Dto getPhonePhone() {
        Dto dto = new Dto(PhonePhone.class);
        dto.set(PhonePhone.SUPANUMBER, 1234);
        return dto;
    }
}
