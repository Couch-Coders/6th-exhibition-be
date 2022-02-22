package couch.exhibition.repository;

import couch.exhibition.entity.Likes;
import couch.exhibition.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findById(Long likeId);

    //@Query("select l from Likes l where l.member = :memberId and l.exhibition = :exhibitionId")

    Page<Likes> findByMember(Member member, Pageable pageable);

    @Modifying
    @Query("UPDATE Exhibition e set e.likeCnt = :likeCnt where e.id= :exhibitionId")
    void updateExhibitionCount(@Param("exhibitionId") Long exhibitionId, int likeCnt);

}
