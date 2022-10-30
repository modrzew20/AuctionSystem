package project.auctionsystem.utils;

import project.auctionsystem.exception.EtagException;

public interface EtagGenerator {

    String generateEtag(Taggable entity);

    void verifyEtag(Taggable entity) throws EtagException;

    void verifyAndUpdateEtag(Taggable entity) throws EtagException;

}
