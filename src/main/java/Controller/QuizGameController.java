package Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import Model.Question;
import Model.Answer;
import Service.QuestionService;
import Service.AnswerService;
import java.util.List;

public class QuizGameController {
    QuestionService questionService;
    AnswerService answerService;
    public QuizGameController(){
        this.questionService = new QuestionService();
        this.answerService = new AnswerService();
    }

    public Javalin startAPI(){
        Javalin app = Javalin.create();
        app.get("/questions", this::getAllQuestionsHandler);
        app.get("/answers", this::getAllAnswersHandler);
        app.get("questions/{question_id}", this::getQuestionByIDHandler);
        app.get("answers/{answer_id}", this::getAnswersByIDHandler);
        return app;
    }

    public void getAllQuestionsHandler(Context context) {
        List<Question> questions = questionService.getAllQuestions();
        context.json(questions);
        context.status(200);
    }

    public void getAllAnswersHandler(Context context){
        List<Answer> answers = answerService.getAllAnswers();
        context.json(answers);
        context.status(200);
    }
    public void getQuestionByIDHandler(Context context){
        int questionID = Integer.parseInt(context.pathParam("question_id"));
        Question getQuestion = questionService.getQuestionByID(questionID);

        if(getQuestion != null){
            context.json(getQuestion);
            context.status(200);
        }
    }
    public List<Answer> getAnswersByIDHandler(Context context){
        int answerID = Integer.parseInt(context.pathParam("answer_id"));

        List<Answer> getAnswers = answerService.getAnswersByID(answerID);

        if(getAnswers != null){
            context.json(getAnswers);
            context.status(200);
        }
        return getAnswers;
    }
}

// pk is unique. answers belong to a question.
//something in question