package couch.exhibition.repository;

import couch.exhibition.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findById(String id);

    Optional<Member> findByNickname(String nickname);
}
