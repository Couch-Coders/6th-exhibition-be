package couch.exhibition.repository;

import couch.exhibition.entity.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition, Long>, JpaSpecificationExecutor<Exhibition> {

    @Query("select m from Exhibition m")
    List<Exhibition> findAllExhibition();

    @Query("select m from Exhibition m where m.title like %:keyword% or m.place like %:keyword%")
    List<Exhibition> findByKeyword(@Param("keyword") String keyword);

    @Query(value = "select * from Exhibition where to_date(CAST(start_date AS TEXT), 'YYYYMMDD') <= current_date and current_date<=to_date(CAST(end_date AS TEXT), 'YYYYMMDD')",nativeQuery=true)
    List<Exhibition> findByProgress();

    @Query("select m from Exhibition m where m.placeAddr like %:city%")
    List<Exhibition> findByCity(@Param("city") String city); //시도별 검색 예) 서울시

    @Query("select m from Exhibition m where m.placeAddr like %:area%")
    List<Exhibition> findByArea(@Param("area") String area); //지역구별 검색 예) 서초구

    @Query("select m from Exhibition m where m.placeAddr like %:city% and m.placeAddr like %:area%")
    List<Exhibition> findByCityAndArea(@Param("city") String city, @Param("area") String area); //시도별 검색 예) 서울시

    @Query("select m from Exhibition m where m.placeAddr like %:city% and m.placeAddr like %:area% and (m.title like %:keyword% or m.place like %:keyword% or m.placeAddr like %:keyword%)")
    List<Exhibition> findByAllCategory(@Param("city") String city, @Param("area") String area, @Param("keyword") String keyword);

    @Query("select m from Exhibition m where m.placeAddr like %:city% and (m.title like %:keyword% or m.place like %:keyword% or m.placeAddr like %:keyword%)")
    List<Exhibition> findByCityAndKeyword(@Param("city") String city, @Param("keyword") String keyword);

    @Query("select m from Exhibition m where m.placeAddr like %:area% and (m.title like %:keyword% or m.place like %:keyword% or m.placeAddr like %:keyword%)")
    List<Exhibition> findByAreaAndKeyword(@Param("area") String area, @Param("keyword") String keyword);

}
