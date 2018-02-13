package dev.jbcu10.opendata.repository;

import dev.jbcu10.opendata.domain.Commodity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CommodityRepository extends ReactiveMongoRepository<Commodity,String>{
    Flux<Commodity> findAllByDateLikeIgnoreCase(String date);
    Flux<Commodity> findAllByPriceLikeIgnoreCase(String price);
    Flux<Commodity> findAllByMarketLikeIgnoreCase(String marke);
    Flux<Commodity> findAllByNameLikeIgnoreCase(String name);

}
