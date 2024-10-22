package ru.ir.visualiser;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class SvgController {
    private final Svg svg;
    private final Dot dot;

    public SvgController() {
        try {
            svg = new Svg();
            dot = new Dot();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public String buildSVG(@RequestPart(value = "file", required = false) MultipartFile file) throws Exception {
        if (dot.readFile(file)) {
            if (dot.generateDotFiles()) {
                svg.generateSvgFiles();
            }
        }
        return "redirect:/";
    }

    @GetMapping("/{filename}")
    @ResponseBody
    public Resource show(@PathVariable String filename) {
        return new ClassPathResource("svg_files/."+filename+".svg");
    }

    @GetMapping("/")
    public String  index(){
        return "index";
    }
}
