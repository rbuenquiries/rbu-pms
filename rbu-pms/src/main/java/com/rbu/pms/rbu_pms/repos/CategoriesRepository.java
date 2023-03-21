package com.rbu.pms.rbu_pms.repos;

import com.rbu.pms.rbu_pms.domain.Categories;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoriesRepository extends JpaRepository<Categories, Long> {
}
