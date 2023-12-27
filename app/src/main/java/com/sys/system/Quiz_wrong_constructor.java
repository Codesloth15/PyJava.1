package com.sys.system;

public class Quiz_wrong_constructor {
    String answer ;
    String correct_answer;
    String question;


    public Quiz_wrong_constructor(){

    }
    public Quiz_wrong_constructor(String answer, String correct_answer, String question) {
        this.answer = answer;
        this.correct_answer = correct_answer;
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
