package couch.exhibition.repository;

import couch.exhibition.dto.ExhibitionDto;
import couch.exhibition.entity.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {



    @Query(value = "select * from Exhibition where title like %?0%", nativeQuery=true)
    Optional<Exhibition> findByTitle(String title);

    @Query(value = "select start_date, end_date from Exhibition where to_date(start_date::TEXT, 'YYYYMMDD') <= now() and now()<=to_date(end_date::TEXT, 'YYYYMMDD')",nativeQuery=true)
    List<ExhibitionDto> findByProgress();

    List<ExhibitionDto> findByPlace(String place);

    @Query(value = "select * from Exhibition where place_addr like %?0% ", nativeQuery=true)
    List<ExhibitionDto> findByCity(String city); //시도별 검색 예) 서울시

    @Query(value = "select * from Exhibition where place_addr like %?0%",nativeQuery=true)
    List<ExhibitionDto> findByArea(String area); //지역구별 검색 예) 서초구

    @Query(value = "select * from Exhibition where place_addr like %?0% and place_addr like %?1%", nativeQuery=true)
    List<ExhibitionDto> findByCityAndArea(String city, String area); //시도별 검색 예) 서울시


}
