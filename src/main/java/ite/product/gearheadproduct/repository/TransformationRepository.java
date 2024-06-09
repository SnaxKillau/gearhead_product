package ite.product.gearheadproduct.repository;

import ite.product.gearheadproduct.dto.TransformationDetailDTO;
import ite.product.gearheadproduct.entity.Transformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;


public interface TransformationRepository extends JpaRepository<Transformation, Long> , JpaSpecificationExecutor<Transformation> {
    @Query(value = "SELECT DISTINCT  t.id, acceleration, available_unit, co2, t.descriptions, model, mpg, power, price, top_speed, year ,b.description as brandDescription,  (\n" +
            "        SELECT ARRAY_AGG(i.name)\n" +
            "        FROM public.images i\n" +
            "        WHERE t.id = i.image_id\n" +
            "    ) AS imageNames,c.descriptions as conditionDescriptions, tr.descriptions as transmissionDescriptions , col.description as colorDescription " +
            "FROM public.transformations t " +
            "JOIN public.brands b ON t.brand_id = b.id " +
            "JOIN public.images i ON t.id = i.image_id " +
            "JOIN public.conditions c ON t.condition_id = c.id " +
            "JOIN public.transmissions tr ON t.transmission_id = tr.id " +
            "JOIN public.colors col ON t.color_id = col.id",
            nativeQuery = true)
    List<TransformationDetailDTO> getTransformationsWithDetails();

    @Query(value = "SELECT id, acceleration, available_unit, co2, descriptions, model, mpg, power, price, top_speed, year, brand_id, color_id, condition_id, transmission_id\n" +
    " FROM public.transformations \n "+
    "WHERE price < 20000" ,nativeQuery = true)
    List<Transformation> getTransformationPrice();

    Page<Transformation> findByBrandDescription(String brandDescription , Pageable pageable);

}
