package dev.jbcu10.opendata.utils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Service;

import java.io.IOException;
 import java.util.LinkedList;
import java.util.List;
import java.util.Map;
@Service
public class CSVtoJSON {
     public  List< Map<String,String>> convert(String string) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader(); // use first row as header; otherwise defaults are fine
        MappingIterator<Map<String,String>> it = mapper.readerFor(Map.class)
                .with(schema)
                .readValues(string);
        List< Map<String,String>> mapList = new LinkedList<>();
         while (it.hasNext()) {
            mapList.add(it.next());
        }
        return mapList;
    }
}
