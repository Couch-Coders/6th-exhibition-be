package couch.exhibition.repository;

import couch.exhibition.entity.Exhibition;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
@Primary
public interface SpringDataExhibitionRepository extends JpaRepository<Exhibition, Long>, ExhibitionRepository {

}
