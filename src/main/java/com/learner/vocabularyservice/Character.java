package com.learner.vocabularyservice;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Value
@Document(collection="vocabulary")
class Character {

    @Id
    String id;

    @Field("汉字")
    String character;

    @Field("序列号")
    int frequencyRank;

    @Field("频率")
    int frequency;

    @Field("频率(%)")
    Double frequencyPercentage;

    @Field("累计频率(%)")
    Double cumulativePercentage;

    @Field("拼音")
    String pinyin;

    @Field("英文翻译")
    String englishTranslation;
}
