package com.likebookapp.service.impl;

import com.likebookapp.model.entity.Mood;
import com.likebookapp.model.enums.MoodName;
import com.likebookapp.repository.MoodRepository;
import com.likebookapp.service.MoodService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MoodServiceImpl implements MoodService {
    private final MoodRepository moodRepository;

    public MoodServiceImpl(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    @Override
    public void initMoods() {
        long count = moodRepository.count();

        List<Mood> moodList = new ArrayList<>();

        if (count ==  0){
            Arrays.stream(MoodName.values())
                    .forEach(moodName -> {
                        Mood mood = new Mood();
                        mood.setName(moodName);
                        mood.setDescription("...");
                        moodList.add(mood);
                    });
        }

        moodRepository.saveAll(moodList);
    }

    @Override
    public Mood findByMood(MoodName mood) {
        return this.moodRepository.findByName(mood).orElseThrow();
    }
}
