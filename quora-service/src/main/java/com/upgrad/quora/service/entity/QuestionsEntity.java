package com.upgrad.quora.service.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "QUESTION")
@NamedQueries({
        @NamedQuery(name = "findQuestionByUuid" , query = "Select q from QuestionsEntity q where q.uuid = :uuid")
})
public class QuestionsEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Size()
    private Integer id;

    @Column(name = "uuid")
    @NotNull
   // @Size()
    private String uuid;

    @Column(name = "content")
    @NotNull
    //@Size()
    private String content;

    @Column(name = "date")
    @NotNull
    //@Size()
    private ZonedDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id ")
    @NotNull
    //@Size()
    private UserEntity userEntity;

    @OneToMany
    @JoinColumn(name = "user_id")
   // @Size()
    private List<AnswerEntity> answersOfQuestion;
}