package au.edu.federation.itech3107.fedunimillionaire30336872;

import java.util.List;

public class OnlineQuestionEntity {

    private String category, type, difficulty, question, correct_answer;
    List<String> incorrect_answers;


    public OnlineQuestionEntity() {

    }

    public OnlineQuestionEntity(String category, String type, String difficulty, String question, String correct_answer) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correct_answer = correct_answer;
    }

    public OnlineQuestionEntity(String category, String type, String difficulty, String question, String correct_answer, List<String> incorrect_answers){
        this(category, type, difficulty, question, correct_answer);
        this.incorrect_answers = incorrect_answers;

    }



    public List<String> getIncorrect_answers() {
        return incorrect_answers;
    }

    public void setIncorrect_answers(List<String> incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }



}
