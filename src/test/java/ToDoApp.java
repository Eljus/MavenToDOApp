import com.codeborne.selenide.CollectionCondition;
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
    public void clearData(){
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
}
