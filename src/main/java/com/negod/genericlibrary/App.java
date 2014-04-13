package com.negod.genericlibrary;

import com.negod.genericlibrary.xml.XmlFileHandler;
import com.negod.genericlibrary.constants.User;
import com.negod.genericlibrary.constants.Setting;
import com.negod.genericlibrary.constants.Phone;
import com.negod.genericlibrary.constants.PhonePhone;
import com.negod.genericlibrary.dto.Dto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {

        List<Dto> userList = new ArrayList<Dto>();
        userList.add(getUser());
        userList.add(getUser());

        Dto<Setting> setting = new Dto(Setting.class);
        setting.set(Setting.COMPILE, 123);
        setting.set(Setting.COMPILE_PATH, "CompilePath");
        setting.set(Setting.DEPLOY_PATH, "DeployPath");
        setting.set(Setting.UNZIP, "UnZipPath");
        setting.set(Setting.USERS, userList);
        setting.set(Setting.ZIP_PATH, "ZipPath");
        setting.set(Setting.USER, getUser());

        try {
            XmlFileHandler handler = new XmlFileHandler();
            handler.createXml(setting, "\\xml\\TestFile");
            Dto dtoData = handler.getXmlFileAsDto("TestFile");
            handler.createXml(dtoData, "TestFile2");
            System.out.print("Hello");
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Dto getUser() {
        Dto<User> dto = new Dto(User.class);
        dto.set(User.LASTNAME, "Johansson");
        dto.set(User.PRENAME, "Joakim");
        dto.set(User.PHONE, getPhone());
        return dto;
    }

    public static Dto getPhone() {
        Dto<Phone> dto = new Dto(Phone.class);
        dto.set(Phone.HOME, "0522 - 18087");
        dto.set(Phone.MOBILE, "0704 - 101192");
        dto.set(Phone.PHONEPHONE, getPhonePhone());
        return dto;
    }

    public static Dto getPhonePhone() {
        Dto<PhonePhone> dto = new Dto(PhonePhone.class);
        dto.set(PhonePhone.SUPANUMBER, 1234);
        return dto;
    }
}
