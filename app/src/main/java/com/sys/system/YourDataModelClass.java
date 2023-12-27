package com.sys.system;

public class YourDataModelClass {
    private String answer;
    private String question;
    private String selectedOption;

    // Required default constructor for Firebase
    public YourDataModelClass() {
    }

    public YourDataModelClass(String answer, String question, String selectedOption) {
        this.answer = answer;
        this.question = question;
        this.selectedOption = selectedOption;
    }

    // Getters and setters (required for Firebase)
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }
}