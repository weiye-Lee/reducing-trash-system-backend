package com.work.recycle.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.entity.Garbage;
import com.work.recycle.entity.GarbageChoose;
import com.work.recycle.exception.Asserts;
import com.work.recycle.repository.GarbageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class CalculateScore {

    @Autowired
    private GarbageRepository garbageRepository;
    @Autowired
    private ObjectMapper mapper;

    public int getScore(List<GarbageChoose> garbageChooses) {
        Iterator<GarbageChoose> iterator = garbageChooses.iterator();
        int score = 0;
        while (iterator.hasNext()) {
            GarbageChoose choose = iterator.next();
            int id = choose.getGarbage().getId();
            Garbage garbage = garbageRepository.getGarbageById(id);
            try {
                log.warn(mapper.writeValueAsString(garbage));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            score += choose.getAmount() * garbage.getScore();

        }
        return score;
    }

}
