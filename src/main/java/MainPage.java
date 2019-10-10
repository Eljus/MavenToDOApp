import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

class MainPage {
    private final static Logger log = Logger.getLogger(MainPage.class);


    void typeIntoTodoList(String toDo) {
        $("[ng-model='newTodo']").val(toDo).pressEnter();
    }

    ElementsCollection getAllInsertedToDos() {
        return $$(".view");
    }

    void compareVisibleToDoNumbers(String visibleToDos) {
        $(".todo-count > .ng-binding").shouldHave(Condition.text(visibleToDos));
    }

    void archiveToDoList(String toDoToAchieve) {
        $(byText(toDoToAchieve)).parent().find(By.cssSelector("input")).click();
    }

    void deleteTodo(String toDoToDelete) {
        $(byText(toDoToDelete)).hover();
        $(byText(toDoToDelete)).parent().find(By.cssSelector("[class='destroy']")).hover().click();
    }
}
