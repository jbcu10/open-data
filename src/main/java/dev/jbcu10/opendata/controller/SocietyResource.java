package dev.jbcu10.opendata.controller;

 import dev.jbcu10.opendata.utils.CSVtoJSON;
 import dev.jbcu10.opendata.utils.Scraped;
 import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;

 import java.io.IOException;
 import java.util.logging.Logger;


@RestController
@RequestMapping("/api/society")
public class SocietyResource {

    Logger logger = Logger.getLogger(this.getClass().getName());
    OkHttpClient client = new OkHttpClient();
    public final CSVtoJSON csVtoJSON;
    public final Scraped scraped;

    public SocietyResource(CSVtoJSON csVtoJSON, Scraped scraped) {
        this.csVtoJSON = csVtoJSON;
        this.scraped = scraped;
    }

    @GetMapping(value = "/csv", params = {"file"},produces = "application/json")
    @ResponseBody
    public ResponseEntity csvToJSON(@RequestParam String file) throws IOException{
             String url ="http://data.gov.ph/sites/default/files/"+file;
            logger.info("url: "+url);
              Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
        return ResponseEntity.ok(csVtoJSON.convert(response.body().string()));
    }

    @GetMapping( produces = "application/json")
    @ResponseBody
    public ResponseEntity society() throws Exception {

        return ResponseEntity.ok(scraped.getSocietyJSON());
    }
}


