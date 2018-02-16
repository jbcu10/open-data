package dev.jbcu10.opendata.repository;

import dev.jbcu10.opendata.domain.Commodity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CommodityRepository extends ReactiveMongoRepository<Commodity,String>{
    @Cacheable
    Flux<Commodity> findAllByDateLikeIgnoreCase(String date);
    @Cacheable

    Flux<Commodity> findAllByPriceLikeIgnoreCase(String price);
    @Cacheable

    Flux<Commodity> findAllByMarketLikeIgnoreCase(String market);
    @Cacheable
    Flux<Commodity> findAllByNameLikeIgnoreCase(String name);

}
