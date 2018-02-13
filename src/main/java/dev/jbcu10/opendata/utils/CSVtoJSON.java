package dev.jbcu10.opendata.utils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import dev.jbcu10.opendata.domain.Commodity;
import dev.jbcu10.opendata.repository.CommodityRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class CSVtoJSON {
    private final CommodityRepository commodityRepository;

    public CSVtoJSON(CommodityRepository commodityRepository) {
        this.commodityRepository = commodityRepository;
    }

    public void save(String string) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader(); // use first row as header; otherwise defaults are fine
        MappingIterator<Map<String,String>> it = mapper.readerFor(Map.class)
                .with(schema)
                .readValues(string);
        List<Commodity> commodities = new LinkedList<>();
         while (it.hasNext()) {
     //        System.out.println("["+it.next().get("date")+",'"+it.next().get("name_of_market")+"','"+it.next().get("comodity")+"',"+it.next().get("price/kg.")+"],");
             commodities.add(saveCommodities(it.next()));
        //     a++;
        }
        Flux<Commodity> commodityFlux =Flux.fromIterable(commodities);

         commodityRepository.insert(commodityFlux).subscribe();
    }
    private Commodity saveCommodities(  Map map){


         return     new Commodity(map.get("name_of_market").toString(),map.get("date").toString(),map.get("comodity").toString(),map.get("price/kg.").toString());

     }
}
