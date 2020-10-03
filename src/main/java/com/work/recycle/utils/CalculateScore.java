package com.work.recycle.utils;

import com.work.recycle.entity.GarbageChoose;

import java.util.Iterator;
import java.util.List;

public class CalculateScore {
    public static int getScore(List<GarbageChoose> garbageChooses) {
        Iterator<GarbageChoose> iterator = garbageChooses.iterator();
        int score = 0;
        while (iterator.hasNext()) {
            GarbageChoose choose = iterator.next();
            score += choose.getAmount() * choose.getGarbage().getScore();
        }
        return score;
    }

}
