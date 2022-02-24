package couch.exhibition.service;

import couch.exhibition.dto.LikesDTO;
import couch.exhibition.entity.Exhibition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import couch.exhibition.entity.Likes;
import couch.exhibition.entity.Member;
import couch.exhibition.exception.CustomException;
import couch.exhibition.exception.ErrorCode;
import couch.exhibition.repository.ExhibitionRepository;
import couch.exhibition.repository.LikesRepository;
import couch.exhibition.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class LikesService {

    private final LikesRepository likesRepository;
    private final ExhibitionRepository exhibitionRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public LikesService(LikesRepository likesRepository, ExhibitionRepository exhibitionRepository, MemberRepository memberRepository) {
        this.likesRepository = likesRepository;
        this.exhibitionRepository = exhibitionRepository;
        this.memberRepository = memberRepository;
    }


    @Transactional
    public LikesDTO createLike(Member member, Long exhibitionId){

        //전시회의 유무확인
        Exhibition exhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXHIBITION));

        //내가 좋아요한 전시회 리스트
        Page<Likes> likesList = likesRepository.findByMember(member, Pageable.unpaged());

        int likeCnt = exhibition.getLikeCnt();

        Likes saveLike = Likes.builder()
                .exhibition(exhibitionRepository.getById(exhibitionId))
                .member(member)
                .build();

        for(Likes like : likesList){
            if(like.getExhibition().equals(saveLike.getExhibition())) throw new CustomException(ErrorCode.EXIST_LIKED_EXHIBITION);
        }

        likesRepository.save(saveLike);
        likeCnt++;
        likesRepository.updateExhibitionCount(exhibition.getId(),likeCnt);
        return new LikesDTO(saveLike);

    }


    @Transactional
    public void deleteLike(Member member, Long exhibitionId){

        Exhibition exhibition = exhibitionRepository.findById(exhibitionId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXHIBITION));

        int likeCnt = exhibition.getLikeCnt();

        Page<Likes> likesList = likesRepository.findByMember(member, Pageable.unpaged());

        boolean flag = false;
        for(Likes like : likesList) {

            if (like.getExhibition().equals(exhibition)) {
                likesRepository.delete(like);
                likeCnt--;
                likesRepository.updateExhibitionCount(exhibition.getId(), likeCnt);
                flag = true;
                break;
            }
        }
        if(flag == false) throw new CustomException(ErrorCode.EXIST_LIKED_DELETE_EXHIBITION);
    }

    public Page<Likes> listLikeExhibition(Member member, Pageable pageable){
        return likesRepository.findByMember(member, pageable);
    }

    @Transactional
    public void updateExhibitionLikeCnt(Exhibition exhibition,Member member){
        judgeNotFoundExhibition(exhibition.getId());
        Page<Likes> likesList = likesRepository.findByMember(member, Pageable.unpaged());
        int likeCnt = exhibition.getLikeCnt();

        for(Likes likes : likesList) {
            if (likes.getExhibition().equals(exhibition)) {
                likesRepository.delete(likes);
                likeCnt--;
            } else {
                likesRepository.save(likes);
                likeCnt++;
            }
            likesRepository.updateExhibitionCount(exhibition.getId(), likeCnt);
        }
    }

    public int countLikes(Exhibition exhibition){
        return exhibition.getLikeCnt();
    }

    public Optional<Likes> findById(Long likesId){
        return likesRepository.findById(likesId);
    }

    private void judgeNotFoundExhibition(Long exhibitionId) {
        if (exhibitionRepository.findById(exhibitionId).isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_EXHIBITION);
        }
    }

}
