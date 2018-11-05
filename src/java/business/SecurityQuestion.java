/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author jdodso227
 */
public enum SecurityQuestion {
    NONE(0, "None"),
    PET(1, "What was the name of you first pet?"),
    CAR(2, "What was the make of your first car?"),
    SCHOOL(3, "What was the name of the first school you went to?");

    private final int questionNo;
    private final String questionText;

    private SecurityQuestion(int questionNo, String questionText) {
        this.questionNo = questionNo;
        this.questionText = questionText;
    }

    public int getQuestionNo() {
        return this.questionNo;
    }

    public String getQuestionText() {
        return this.questionText;
    }
}
