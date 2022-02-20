package couch.exhibition.repository;

import couch.exhibition.entity.Exhibition;
import couch.exhibition.entity.Member;
import couch.exhibition.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitionReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByExhibition(Exhibition exhibition, Pageable pageable);

    Page<Review> findAllByMember(Member member, Pageable pageable);
}


