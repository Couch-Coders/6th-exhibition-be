package couch.exhibition.repository;

import couch.exhibition.entity.Exhibition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition, Long>{

    //@Query("select m from Exhibition m")
    Page<Exhibition> findAll(Pageable pageable);

    @Query("select m from Exhibition m where m.title like %:keyword% or m.place like %:keyword%")
    Page<Exhibition> findByKeyword(@Param("keyword") String keyword,Pageable pageable);

    @Query(value = "select * from Exhibition where to_date(CAST(start_date AS TEXT), 'YYYYMMDD') <= current_date and current_date<=to_date(CAST(end_date AS TEXT), 'YYYYMMDD')"
            ,nativeQuery=true)
    List<Exhibition> findByProgress();

    @Query("select m from Exhibition m where m.placeAddr like %:city%")
    Page<Exhibition> findByCity(@Param("city") String city, Pageable pageable); //시도별 검색 예) 서울시

    @Query("select m from Exhibition m where m.placeAddr like %:area%")
    Page<Exhibition> findByArea(@Param("area") String area, Pageable pageable); //지역구별 검색 예) 서초구

    @Query("select m from Exhibition m where m.placeAddr like %:city% and m.placeAddr like %:area%")
    Page<Exhibition> findByCityAndArea(@Param("city") String city, @Param("area") String area, Pageable pageable); //시도별 검색 예) 서울시

    @Query("select m from Exhibition m where m.placeAddr like %:city% and m.placeAddr like %:area% and (m.title like %:keyword% or m.place like %:keyword% or m.placeAddr like %:keyword%)")
    Page<Exhibition> findByAllCategory(@Param("city") String city, @Param("area") String area, @Param("keyword") String keyword, Pageable pageable);

    @Query("select m from Exhibition m where m.placeAddr like %:city% and (m.title like %:keyword% or m.place like %:keyword% or m.placeAddr like %:keyword%)")
    Page<Exhibition> findByCityAndKeyword(@Param("city") String city, @Param("keyword") String keyword, Pageable pageable);

    @Query("select m from Exhibition m where m.placeAddr like %:area% and (m.title like %:keyword% or m.place like %:keyword% or m.placeAddr like %:keyword%)")
    Page<Exhibition> findByAreaAndKeyword(@Param("area") String area, @Param("keyword") String keyword, Pageable pageable);

    @Override
    Optional<Exhibition> findById(Long id);

    List<Exhibition> findTop10ByOrderByLikeCntDesc();
}
