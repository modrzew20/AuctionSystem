package project.auctionsystem.utils.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import project.auctionsystem.exception.EtagException;
import project.auctionsystem.utils.EtagGenerator;
import project.auctionsystem.utils.Taggable;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class EtagGeneratorImpl implements EtagGenerator {

    private final HttpServletRequest httpServletRequest;

    private Mac mac;

    @Value("${SECRET_ETAG_KEY}")
    private String secretEtagKey;

    @PostConstruct
    public void init() throws Exception {
        mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secretEtagKey.getBytes(), "HmacSHA256"));
    }

    @Override
    public String generateEtag(Taggable entity) {
        return Base64.getEncoder().encodeToString(mac.doFinal(entity.generateETagMessage().getBytes()));
    }

    @Override
    public void verifyEtag(Taggable entity) throws EtagException {
        String etag = httpServletRequest.getHeader("If-Match");
        if (!etag.equals(entity.generateETagMessage())) {
            throw new EtagException("Etag is not valid");
        }
    }

    @Override
    public void verifyAndUpdateEtag(Taggable entity) throws EtagException {
        verifyEtag(entity);
        entity.updateTag();
    }
}

