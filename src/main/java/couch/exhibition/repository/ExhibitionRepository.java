package couch.exhibition.repository;

import couch.exhibition.dto.ExhibitionDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExhibitionRepository extends JpaRepository<ExhibitionDto, Long> {
}
