package com.quiz.app.chapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateChapterDTO {
    private String subjectId;
    private List<ChapterDTO> chapters;
}
