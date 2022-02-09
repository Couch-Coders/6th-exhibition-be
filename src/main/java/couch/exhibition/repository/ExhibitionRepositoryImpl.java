package couch.exhibition.repository;

import couch.exhibition.entity.Exhibition;

import javax.persistence.EntityManager;


public class ExhibitionRepositoryImpl implements ExhibitionRepository{

    private final EntityManager em;

    public ExhibitionRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Exhibition save(Exhibition exhibition) {
        em.persist(exhibition);
        return exhibition;
    }
}
