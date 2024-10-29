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
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    @Test
    public void checkReadFromFile() throws URISyntaxException, IOException {
        URI path = getClass().getClassLoader().getResource("test.ll").toURI();
        Parser parser = new Parser();
        String content = Files.readString(Path.of(path));
        ModuleIR moduleIR = parser.parseModule(content);
        List<FunctionIR> functions = moduleIR.getFunctions();

        assertEquals(21, functions.size());

    }
}
