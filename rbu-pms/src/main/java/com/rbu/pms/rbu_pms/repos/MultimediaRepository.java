package com.rbu.pms.rbu_pms.repos;

import com.rbu.pms.rbu_pms.domain.Items;
import com.rbu.pms.rbu_pms.domain.Multimedia;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MultimediaRepository extends JpaRepository<Multimedia, Long> {

    Multimedia findFirstByItemid(Items items);

}
