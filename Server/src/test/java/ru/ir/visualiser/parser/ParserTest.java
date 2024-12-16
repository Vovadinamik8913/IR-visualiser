package ru.ir.visualiser.parser;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    @Test
    public void checkReadFromFile() throws URISyntaxException, IOException {
        URI path = getClass().getClassLoader().getResource("test.ll").toURI();
        String content = Files.readString(Path.of(path));
        ModuleIR moduleIR = Parser.parseModule(content);
        Collection<FunctionIR> functions = moduleIR.getFunctions();
        FunctionIR function = functions.iterator().next();


        assertEquals(21, functions.size());
        assertEquals(144, function.getStartLine());
        assertEquals(167,function.getEndLine());
    }

    @Test
    public void dotTest() throws URISyntaxException, IOException {
        URI path = getClass().getClassLoader().getResource("dot/test1.dot").toURI();
        String content = Files.readString(Path.of(path));
        Dot dot = Parser.parseDot(content);
        assertEquals("Node0x55cdef5b4ad0", dot.getSvgIdByLabel("2"));
        assertEquals("Node0x55cdef5b5320", dot.getSvgIdByLabel("14"));
        assertEquals("Node0x55cdef5b5380", dot.getSvgIdByLabel("20"));
        assertEquals("Node0x55cdef5b5900", dot.getSvgIdByLabel("21"));
        assertEquals("Node0x55cdef5b5d60", dot.getSvgIdByLabel("28"));
        assertEquals("Node0x55cdef5b5dc0", dot.getSvgIdByLabel("32"));
        assertEquals("Node0x55cdef5b5830", dot.getSvgIdByLabel("44"));
    }
}
