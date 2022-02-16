package couch.exhibition.repository;

import couch.exhibition.entity.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {

    List<Exhibition> findById(String id);

    Optional<Exhibition> findByTitle(String title);

    List<Exhibition> findByDate(Integer startDate, Integer endDate);

    List<Exhibition> findByPlace(String place);

    List<Exhibition> findByArea(String area);


}
