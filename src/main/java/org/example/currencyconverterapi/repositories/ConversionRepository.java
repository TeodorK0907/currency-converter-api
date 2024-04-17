package org.example.currencyconverterapi.repositories;

import org.example.currencyconverterapi.models.Conversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;

public interface ConversionRepository extends ListCrudRepository<Conversion, Integer>,
        PagingAndSortingRepository<Conversion, Integer> {

    Page<Conversion> findAllByTimeStampAfterOrderByTimeStamp(
            LocalDateTime after, Pageable page);

    Page<Conversion> findAllByTimeStampBeforeOrderByTimeStamp(
            LocalDateTime before, Pageable page);

    Page<Conversion> findAllByTimeStampIsAfterAndTimeStampIsBeforeOrderByTimeStamp(
            LocalDateTime after,
            LocalDateTime before,
            Pageable page);

    Page<Conversion> findAllByUUID(String uuid, Pageable page);

}
