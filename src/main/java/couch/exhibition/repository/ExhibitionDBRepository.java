package couch.exhibition.repository;

import couch.exhibition.entity.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitionDBRepository extends JpaRepository<Exhibition, Long> {
}
