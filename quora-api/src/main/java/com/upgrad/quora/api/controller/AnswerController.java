package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.AnswerRequest;
import com.upgrad.quora.api.model.AnswerResponse;
import com.upgrad.quora.service.business.AnswerBusinessService;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
public class AnswerController {

    @Autowired
    private AnswerBusinessService answerBusinessService;

    //This method will be called when the request pattern is of type /question/{questionId}/answer/create and incoming request is of type POST
    //This method receives questionUuid whose answer should be stored in the database, accessToken of the user creating the answer and answer string
    //This method creates the AnswerEntity object and populates it with
    // - answer string from incoming request
    // - generates and sets the UUID
    // - sets the current time
    //Sends the answerentity object to business logic to be persisted in the database
    //On success, returns the answer UUID with message ANSWER CREATED and Http Status code 200
    @RequestMapping(
            method = RequestMethod.POST,
            path = "/question/{questionId}/answer/create",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<AnswerResponse> createAnswer(final AnswerRequest answerRequest , @PathVariable("questionId") final String questionUuid,
                                                      @RequestHeader("authorization") final String accessToken) throws AuthorizationFailedException, InvalidQuestionException{

        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setAns(answerRequest.getAnswer());
        answerEntity.setUuid(UUID.randomUUID().toString());
        answerEntity.setDate(ZonedDateTime.now());

        answerBusinessService.createAnswer(accessToken, questionUuid, answerEntity);

        AnswerResponse answerResponse = new AnswerResponse().id(answerEntity.getUuid()).status("ANSWER CREATED");

        return new ResponseEntity<AnswerResponse>( answerResponse , HttpStatus.OK);
    }
}
