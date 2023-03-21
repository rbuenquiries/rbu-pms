package com.rbu.pms.rbu_pms.repos;

import com.rbu.pms.rbu_pms.domain.Brands;
import com.rbu.pms.rbu_pms.domain.Items;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemsRepository extends JpaRepository<Items, Long> {

    Items findFirstByBrandid(Brands brands);

}
