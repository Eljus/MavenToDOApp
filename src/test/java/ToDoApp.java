import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@RunWith(JUnit4.class)
public class ToDoApp {

    @Before
    public void configureBrowser() {
        Configuration.baseUrl = "http://todomvc.com/examples/angularjs/#/"; //http://localhost:8080/
        open("/");
    }

    @After
    public void clearData() {
        clearBrowserLocalStorage();
        clearBrowserCookies();
    }

    @Test
    public void validateNumberInsertedToDosWithOne() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Algorithms");
        mainPage.getAllInsertedToDos().shouldHave(CollectionCondition.size(1));
    }

    @Test
    public void validateNumberInsertedToDosWithTwo() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.getAllInsertedToDos().shouldHave(CollectionCondition.size(1));
    }

    @Test
    public void validateLeftItemsWIthOne() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.compareVisibleToDoNumbers("1");
    }

    @Test
    public void removeOneToDo() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.deleteTodo("Math");
        mainPage.getAllInsertedToDos().shouldBe(empty);
    }

    @Test
    public void removeOneToDoFromTwo() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.typeIntoTodoList("History");
        mainPage.deleteTodo("Math");
        mainPage.getAllInsertedToDos().shouldHaveSize(1);
    }

    @Test
    public void archiveOneTodo() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.archiveToDoList("Math");
        mainPage.compareVisibleToDoNumbers("0");
    }

    @Test
    public void activeOneToDo() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        $(byText("Active")).click();
        mainPage.getAllInsertedToDos().shouldHaveSize(1);
        mainPage.getAllInsertedToDos().shouldHave(texts("Math"));
    }

    @Test
    public void validateActiveToDosIfArchived() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        $(byText("Active")).click();
        mainPage.getAllInsertedToDos().shouldHaveSize(1);
        mainPage.compareVisibleToDoNumbers("1");
    }

    @Test
    public void validateCompletedIfNotArchived() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        $(byText("Completed")).click();
        mainPage.getAllInsertedToDos().shouldHaveSize(0);
        mainPage.compareVisibleToDoNumbers("1");
    }

    @Test
    public void validateArchivedToDosInCompleted() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.archiveToDoList("Math");
        $(byText("Completed")).click();
        mainPage.getAllInsertedToDos().shouldHaveSize(1);
        mainPage.compareVisibleToDoNumbers("0");
    }

    @Test
    public void deleteTodoAfterCreating() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.deleteTodo("Math");
        mainPage.getAllInsertedToDos().shouldHaveSize(0);
    }

    @Test
    public void deleteOneTodoFromTwo() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.typeIntoTodoList("Math2");
        mainPage.deleteTodo("Math");
        mainPage.getAllInsertedToDos().shouldHaveSize(1);
        mainPage.compareVisibleToDoNumbers("1");
        mainPage.getAllInsertedToDos().shouldHave(texts("Math"));
    }

    @Test
    public void deleteOneInActive() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.typeIntoTodoList("Math2");
        $(byText("Active")).click();
        mainPage.deleteTodo("Math");
        mainPage.getAllInsertedToDos().shouldHaveSize(1);
        mainPage.compareVisibleToDoNumbers("1");
        mainPage.getAllInsertedToDos().shouldHave(texts("Math"));
    }

    @Test
    public void deletedOneInActiveOneInCompleted() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.typeIntoTodoList("Math2");
        mainPage.archiveToDoList("Math2");
        $(byText("Active")).click();
        mainPage.deleteTodo("Math");
        $(byText("Completed")).click();
        mainPage.deleteTodo("Math2");
        mainPage.getAllInsertedToDos().shouldHaveSize(0);
    }

    @Test
    public void changeFromCompletedToActive() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.archiveToDoList("Math");
        $(byText("Completed")).click();
        mainPage.archiveToDoList("Math");
        mainPage.getAllInsertedToDos().shouldHaveSize(0);
        mainPage.compareVisibleToDoNumbers("1");
        $(byText("All")).click();
        mainPage.getAllInsertedToDos().shouldHaveSize(1);
    }

    @Test
    public void clearCompleted() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.archiveToDoList("Math");
        $(byText("Completed")).click();
        mainPage.archiveToDoList("Math");
        mainPage.getAllInsertedToDos().shouldHaveSize(0);
    }

    @Test
    public void clearThreeCompletedIfNoToDosInAll() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.typeIntoTodoList("Math2");
        mainPage.typeIntoTodoList("Math3");
        mainPage.archiveToDoList("Math");
        mainPage.archiveToDoList("Math2");
        mainPage.archiveToDoList("Math3");
        $(byText("Completed")).click();
        mainPage.archiveToDoList("Math");
        mainPage.archiveToDoList("Math2");
        mainPage.archiveToDoList("Math3");
        mainPage.getAllInsertedToDos().shouldHaveSize(0);
    }

    @Test
    public void clearCompletedMissingIfNoToDosInActive() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.typeIntoTodoList("Math2");
        mainPage.typeIntoTodoList("Math3");
        $(byText("Active"));
        mainPage.archiveToDoList("Math");
        mainPage.archiveToDoList("Math2");
        mainPage.archiveToDoList("Math3");
        $(byText("Completed")).click();
        mainPage.archiveToDoList("Math");
        mainPage.archiveToDoList("Math2");
        mainPage.archiveToDoList("Math3");
        mainPage.getAllInsertedToDos().shouldHaveSize(0);
    }

    @Test
    public void validateDeArchivedInActiveAfterClearedInActive() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.typeIntoTodoList("Math2");
        mainPage.typeIntoTodoList("Math3");
        $(byText("Active"));
        mainPage.archiveToDoList("Math");
        mainPage.archiveToDoList("Math2");
        mainPage.archiveToDoList("Math3");
        $(byText("Completed")).click();
        mainPage.archiveToDoList("Math");
        mainPage.archiveToDoList("Math2");
        mainPage.archiveToDoList("Math3");
        $(byText("Active")).click();
        mainPage.getAllInsertedToDos().shouldHaveSize(3);
    }

    @Test
    public void makeActiveAndDelete() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.typeIntoTodoList("Math2");
        mainPage.archiveToDoList("Math");
        mainPage.archiveToDoList("Math2");
        $(byText("Completed")).click();
        mainPage.archiveToDoList("Math");
        mainPage.archiveToDoList("Math2");
        $(byText("Active")).click();
        mainPage.deleteTodo("Math");
        mainPage.deleteTodo("Math2");
        mainPage.getAllInsertedToDos().shouldHaveSize(0);
    }

    @Test
    public void validateArchivedToDosTextColor() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.archiveToDoList("Math");
        $(byText("Math")).shouldHave(Condition.cssValue("color", "rgba(217, 217, 217, 1)"));
    }

    @Test
    public void placeholderTextInAll() {
        $("[ng-model='newTodo']").shouldHave(Condition.attribute("placeholder", "What needs to be done?"));
    }

    @Test
    public void placeholderTextInActive() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        $(byText("Active")).click();
        $("[ng-model='newTodo']").shouldHave(Condition.attribute("placeholder", "What needs to be done?"));
    }

    @Test
    public void placeholderTextInCompleted() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        $(byText("Active")).click();
        $("[ng-model='newTodo']").shouldHave(Condition.attribute("placeholder", "What needs to be done?"));
    }

    @Test
    public void validateDefaultSelectedModule() {
        $(byText("All")).shouldHave(Condition.attribute("class", "selected"));
    }

    @Test
    public void validateActiveModuleIsSelectedAfterMovingThere() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        $(byText("Active")).click();
        $(byText("Active")).shouldHave(Condition.attribute("class", "selected"));
    }

    @Test
    public void validateCompletedModuleIsSelectedAfterMovingThere() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        $(byText("Completed")).click();
        $(byText("Completed")).shouldHave(Condition.attribute("class", "selected"));
    }

    @Test
    public void abilityToArchiveAllAtOnceInAll() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.typeIntoTodoList("Math2");
        mainPage.typeIntoTodoList("Math3");
        mainPage.toggleAll();
        mainPage.getAllInsertedToDos().shouldHaveSize(3);
    }

    @Test
    public void abilityToArchiveAllAtOnceInActive() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.typeIntoTodoList("Math2");
        mainPage.typeIntoTodoList("Math3");
        $(byText("Active")).click();
        mainPage.toggleAll();
        mainPage.getAllInsertedToDos().shouldHaveSize(0);
    }

    @Test
    public void abilityToDeArchiveAllAtOnceInCompleted() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.typeIntoTodoList("Math2");
        mainPage.typeIntoTodoList("Math3");
        mainPage.toggleAll();
        $(byText("Completed")).click();
        mainPage.toggleAll();
        mainPage.getAllInsertedToDos().shouldHaveSize(0);
    }

    @Test
    public void toggledAllAtOnceShouldAppearInCompleted() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.typeIntoTodoList("Math2");
        mainPage.typeIntoTodoList("Math3");
        mainPage.toggleAll();
        $(byText("Completed")).click();
        mainPage.getAllInsertedToDos().shouldHave(texts("Math", "Math2", "Math3"));
    }

    @Test
    public void toggledAllAtOnceInActiveShouldAppearInCompleted() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.typeIntoTodoList("Math2");
        mainPage.typeIntoTodoList("Math3");
        $(byText("Active")).click();
        mainPage.toggleAll();
        $(byText("Completed")).click();
        mainPage.getAllInsertedToDos().shouldHave(texts("Math", "Math2", "Math3"));
    }

    @Test
    public void toggledAllAtOnceInCompletedShouldAppearInAll() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.typeIntoTodoList("Math2");
        mainPage.typeIntoTodoList("Math3");
        $(byText("Completed")).click();
        mainPage.toggleAll();
        $(byText("All")).click();
        mainPage.getAllInsertedToDos().shouldHave(texts("Math", "Math2", "Math3"));
    }

    @Test
    public void toggledAllAtOnceInCompletedShouldAppearInActive() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList("Math");
        mainPage.typeIntoTodoList("Math2");
        mainPage.typeIntoTodoList("Math3");
        mainPage.toggleAll();
        $(byText("Completed")).click();
        mainPage.toggleAll();
        $(byText("Active")).click();
        mainPage.getAllInsertedToDos().shouldHave(texts("Math", "Math2", "Math3"));
    }
}
