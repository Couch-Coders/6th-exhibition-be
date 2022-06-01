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

    @Query("select m from Exhibition m where m.startDate <= :today and :today <= m.endDate")
    Page<Exhibition> findAll(@Param("today") int today,Pageable pageable);

    @Query("select m from Exhibition m where m.startDate <= :today and :today <= m.endDate")
    List<Exhibition> findAllExhibitions(int today,Pageable pageable);

    @Query("select m from Exhibition m where (m.title like %:keyword% or m.place like %:keyword%) and m.startDate <= :today and :today <= m.endDate ")
    List<Exhibition> findByKeyword(@Param("keyword") String keyword,@Param("today") int today, Pageable pageable);

    @Query("select m from Exhibition m where m.placeAddr like %:city% and m.startDate <= :today and :today<=m.endDate")
    List<Exhibition> findByCity(@Param("city") String city, @Param("today") int today, Pageable pageable); //시도별 검색 예) 서울시

    @Query("select m from Exhibition m where m.placeAddr like %:area% and m.startDate <= :today and :today<=m.endDate")
    List<Exhibition> findByArea(@Param("area") String area,@Param("today") int today, Pageable pageable); //지역구별 검색 예) 서초구

    @Query("select m from Exhibition m where m.placeAddr like %:city% and m.placeAddr like %:area% and m.startDate <= :today and :today<=m.endDate")
    List<Exhibition> findByCityAndArea(@Param("city") String city, @Param("area") String area,@Param("today") int today, Pageable pageable); //시도별 검색 예) 서울시

    @Query("select m from Exhibition m where m.placeAddr like %:city% and m.placeAddr like %:area% and (m.title like %:keyword% or m.place like %:keyword% or m.placeAddr like %:keyword%) and m.startDate <= :today and :today<=m.endDate")
    List<Exhibition> findByAllCategory(@Param("city") String city, @Param("area") String area, @Param("keyword") String keyword,@Param("today") int today, Pageable pageable);

    @Query("select m from Exhibition m where m.placeAddr like %:city% and (m.title like %:keyword% or m.place like %:keyword% or m.placeAddr like %:keyword%) and m.startDate <= :today and :today<=m.endDate")
    List<Exhibition> findByCityAndKeyword(@Param("city") String city, @Param("keyword") String keyword, @Param("today") int today,Pageable pageable);

    @Query("select m from Exhibition m where m.placeAddr like %:area% and (m.title like %:keyword% or m.place like %:keyword% or m.placeAddr like %:keyword%) and m.startDate <= :today and :today<=m.endDate")
    List<Exhibition> findByAreaAndKeyword(@Param("area") String area, @Param("keyword") String keyword, @Param("today") int today,Pageable pageable);

    @Override
    Optional<Exhibition> findById(Long id);

    @Query("select m from Exhibition m where m.startDate <= :today and :today<=m.endDate order by m.likeCnt")
    List<Exhibition> findTop10ByOrderByLikeCntDesc(@Param("today") int today);

    Optional<Exhibition> findByTitle(String title);
}
