package com.rbu.pms.rbu_pms.repos;

import com.rbu.pms.rbu_pms.domain.Items;
import com.rbu.pms.rbu_pms.domain.Offers;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OffersRepository extends JpaRepository<Offers, Long> {

    Offers findFirstByItemid(Items items);

}
