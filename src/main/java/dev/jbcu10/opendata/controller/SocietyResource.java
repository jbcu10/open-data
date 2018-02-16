package dev.jbcu10.opendata.controller;

 import dev.jbcu10.opendata.domain.Commodity;
 import dev.jbcu10.opendata.repository.CommodityRepository;
 import dev.jbcu10.opendata.utils.CSVtoJSON;
 import dev.jbcu10.opendata.utils.Scraped;
 import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;
 import reactor.core.publisher.Flux;
 import reactor.core.publisher.Mono;

 import java.io.IOException;
 import java.util.*;
 import java.util.logging.Logger;
 import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/society")
public class SocietyResource {

    Logger logger = Logger.getLogger(this.getClass().getName());
    OkHttpClient client = new OkHttpClient();
    public final CSVtoJSON csVtoJSON;
    public final Scraped scraped;
    public final CommodityRepository commodityRepository;

    public SocietyResource(CSVtoJSON csVtoJSON, Scraped scraped, CommodityRepository commodityRepository) {
        this.csVtoJSON = csVtoJSON;
        this.scraped = scraped;
        this.commodityRepository = commodityRepository;
    }

    @GetMapping(value = "/csv", params = {"file"},produces = "application/json")
    @ResponseBody
    public Flux<Commodity> csvToJSON(@RequestParam String file) throws IOException{

        if (commodityRepository.count().block()>0){
            return commodityRepository.findAll();
        }
            Request request = new Request.Builder()
                    .url(buildURL(file))
                    .build();
            Response response = client.newCall(request).execute();
         csVtoJSON.save(response.body().string());
        return commodityRepository.findAll();

    }
    @GetMapping(value = "/csv", params = {"file","key","value"},produces = "application/json")
    @ResponseBody
    public Flux<Commodity> getCommodities(@RequestParam String file ,@RequestParam String key,@RequestParam String value) throws IOException{
        logger.info(commodityRepository.count().block().toString());

        if (commodityRepository.count().block()<1){
            Request request = new Request.Builder()
                    .url(buildURL(file))
                    .build();
            Response response = client.newCall(request).execute();
            csVtoJSON.save(response.body().string());
        }
        if(key.equals("commodity"))
        {
            return commodityRepository.findAllByNameLikeIgnoreCase(value);

        } if(key.equals("market"))
        {
            return commodityRepository.findAllByMarketLikeIgnoreCase(value);

        } if(key.equals("date"))
        {
            return commodityRepository.findAllByDateLikeIgnoreCase(value);

        } if(key.equals("price"))
        {
            return commodityRepository.findAllByPriceLikeIgnoreCase(value);

        }if(value.equals(""))
        {
            return commodityRepository.findAll();

        }
            return null;

     }
     @GetMapping(value = "/commodity" ,produces = "application/json")
    @ResponseBody
    public Mono<Set> getCommoditiesWithoutSimilar() throws IOException{
              Set<String> strings = new HashSet<>();
             List<Commodity> commodities  = commodityRepository.findAll().collectList().block();
             commodities.forEach(commodity -> {
                 String []name = commodity.getName().split(",");
                 String finalName = name[0].toLowerCase().replace("medium","").replace(" meduim.","").replace("unripe","");
                 strings.add(finalName);
             });

           return   Mono.just(strings);




     }


   /* private List<Map<String,String>> filterByKey(String key,String value,List<Map<String,String>> json){
        List<Map<String,String>> filteredCommodities = new LinkedList<>();
        for (Map map: json) {

            if(map.get(key).toString().toLowerCase().contains(value.toLowerCase())){
                filteredCommodities.add(map);
            }
        }
        return filteredCommodities;
    }*/

    @GetMapping( produces = "application/json")
    @ResponseBody
    public ResponseEntity society() throws Exception {

        return ResponseEntity.ok(scraped.getSocietyJSON());
    }
    static String buildURL(String file){
        return "http://data.gov.ph/sites/default/files/"+file;

    }

}


