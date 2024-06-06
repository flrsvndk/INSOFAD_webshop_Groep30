package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.models.CustomUser;
import com.example.todoappdeel3.models.Giftcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//maps the product class to the database using the Long type as default of ID's
@Repository
public interface GiftcardRepository extends JpaRepository<Giftcard, Long> {

    Optional<Giftcard> findById(long id);

    List<Giftcard> findByOwner(CustomUser owner);
    List<Giftcard> findByBuyer(CustomUser buyer);

}
