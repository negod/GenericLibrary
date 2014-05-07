/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.genericstest;

import com.negod.genericlibrary.App;
import com.negod.genericlibrary.dto.Dto;
import com.negod.genericlibrary.xml.XmlFileHandler;
import com.negod.genericstest.CmdCommands.Command;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.TestCase.assertTrue;
import org.junit.Test;

/**
 *
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 */
public class TestXmlParser {

    public static final String commandFileName = "CommandFile";

    @Test
    public void testParser() {

        Dto<CmdCommands> command = new Dto<>(CmdCommands.class);
        command.set(CmdCommands.COMMANDS, getCommandsList());

        try {
            XmlFileHandler handler = new XmlFileHandler();
            handler.createXml(command, commandFileName);

            Dto dtoData = handler.getXmlFileAsDto(commandFileName);
            List<Dto> commandList = new ArrayList(dtoData.getValues());

            assertTrue(commandList.size() == getCommandsList().size());

            System.out.print("Hello");
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Dto> getCommandsList() {

        List<Dto> command = new ArrayList<>();

        Dto<Command> commandDto = new Dto<>(Command.class);
        commandDto.set(Command.VALUE, "1");
        command.add(commandDto);

        Dto<Command> commandDto2 = new Dto<>(Command.class);
        commandDto2.set(Command.VALUE, "1");
        command.add(commandDto2);

        return command;

    }

}
