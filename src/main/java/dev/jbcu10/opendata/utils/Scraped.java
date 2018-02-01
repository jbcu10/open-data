package dev.jbcu10.opendata.utils;


 import dev.jbcu10.opendata.domain.Source;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;


@Service
public class Scraped {

     public List<Source> getSocietyJSON() throws Exception {

        Resource resource = new ClassPathResource("soc-record.csv");
         List<Source> sources = new LinkedList<>();
        try {

            List<String> stream = Files.readAllLines(Paths.get(resource.getURL().getPath()));
            stream.forEach(string-> {
                String[] sourceArray = string.split(",");
                String[] srcArray =sourceArray[1].split("/");
                String[] csvArray =sourceArray[2].split("files/");
                if(csvArray[csvArray.length-1].contains("csv")) {
                    sources.add(new Source(sourceArray[0], srcArray[srcArray.length - 1].toUpperCase(), csvArray[csvArray.length - 1]));
                }});

        }catch (Exception e) {
            e.printStackTrace();
         }
        return sources;
    }


}
