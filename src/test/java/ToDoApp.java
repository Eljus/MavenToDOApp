import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

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
    public void validateNumberInsertedToDosWithAlgorithms() {
        MainPage mainPage = new MainPage();
        mainPage.validateInsertedNumberWithOneToDo(Collections.singletonList("Algorithms"), 1);
    }

    @Test
    public void validateLeftItemsWihOne() {
        MainPage mainPage = new MainPage();
        mainPage.validateLeftItemsWithOneToDo(Collections.singletonList("Math"), "1");
    }

    @Test
    public void removeOneToDo() {
        MainPage mainPage = new MainPage();
        mainPage.typeTodoAndDelete(Collections.singletonList("Math"), Collections.singletonList("Math"));
        mainPage.getAllInsertedToDos().shouldBe(empty);
    }

    @Test
    public void removeOneToDoFromTwo() {
        MainPage mainPage = new MainPage();
        mainPage.typeTodoDeleteAndValidateNumber(Arrays.asList("Math", "History"), Collections.singletonList("Math"), 1);
    }

    @Test
    public void archiveOneTodo() {
        MainPage mainPage = new MainPage();
        mainPage.addTodoArchiveAndValidateStringItemsLeft(Collections.singletonList("Math"), Collections.singletonList("Math"), "0");
    }

    @Test
    public void activeOneToDo() {
        MainPage mainPage = new MainPage();
        mainPage.typeTodoAndSelectSectionAndValidateToDosCount(Collections.singletonList("Math"), "Active", 1);
        mainPage.getAllInsertedToDos().shouldHave(texts("Math"));
    }

    @Test
    public void validateActiveToDosIfArchived() {
        MainPage mainPage = new MainPage();
        mainPage.typeTodoAndSelectSectionAndValidateToDosCountAndVisibleNumbeText(Collections.singletonList("Math"), "Active", 1, "1");
    }

    @Test
    public void validateCompletedIfNotArchived() {
        MainPage mainPage = new MainPage();
        mainPage.typeTodoAndSelectSectionAndValidateToDosCountAndVisibleNumbeText(Collections.singletonList("Math"), "Completed", 0, "1");
    }

    @Test
    public void validateArchivedToDosInCompleted() {
        MainPage mainPage = new MainPage();
        mainPage.typeToDoArchiveSelectSection(Collections.singletonList("Math"),Collections.singletonList("Math"), "Completed");
        mainPage.getAllInsertedToDos().shouldHaveSize(1);
        mainPage.compareVisibleToDoNumbers("0");
    }

    @Test
    public void deleteTodoAfterCreating() {
        MainPage mainPage = new MainPage();
        mainPage.typeTodoDeleteAndValidateNumber(Collections.singletonList("Math"), Collections.singletonList("Math"), 0);
    }

    @Test
    public void deleteOneTodoFromTwo() {
        MainPage mainPage = new MainPage();
        mainPage.typeTodoDeleteAndValidateNumber(Arrays.asList("Math", "Math2"), Collections.singletonList("Math"), 1);
        mainPage.compareVisibleToDoNumbers("1");
        mainPage.getAllInsertedToDos().shouldHave(texts("Math"));
    }

    @Test
    public void deleteOneInActive() {
        MainPage mainPage = new MainPage();
        mainPage.typeToDoSelectSectionAndDelete(Arrays.asList("Math", "Math2"), "Active", Collections.singletonList("Math"));
        mainPage.getAllInsertedToDos().shouldHaveSize(1);
        mainPage.compareVisibleToDoNumbers("1");
        mainPage.getAllInsertedToDos().shouldHave(texts("Math"));
    }

    @Test
    public void deletedOneInActiveOneInCompleted() {
        MainPage mainPage = new MainPage();
        mainPage.typeToDoArchiveSelectSectionAndDelete(Arrays.asList("Math", "Math2"), Collections.singletonList("Math2"), "Active", Collections.singletonList("Math"));
        $(byText("Completed")).click();
        mainPage.deleteTodoList(Collections.singletonList("Math2"));
        mainPage.getAllInsertedToDos().shouldHaveSize(0);
    }

    @Test
    public void changeFromCompletedToActive() {
        MainPage mainPage = new MainPage();
        mainPage.typeToDoArchiveSelectSection(Collections.singletonList("Math"), Collections.singletonList("Math"), "Completed");
        mainPage.archiveToDoList(Collections.singletonList("Math"));
        mainPage.getAllInsertedToDos().shouldHaveSize(0);
        mainPage.compareVisibleToDoNumbers("1");
        $(byText("All")).click();
        mainPage.getAllInsertedToDos().shouldHaveSize(1);
    }

    @Test
    public void clearCompleted() {
        MainPage mainPage = new MainPage();
        mainPage.typeToDoArchiveSelectSection(Collections.singletonList("Math"), Collections.singletonList("Math"), "Completed");
        mainPage.archiveToDoList(Collections.singletonList("Math"));
        mainPage.getAllInsertedToDos().shouldHaveSize(0);
    }

    @Test
    public void clearThreeCompletedIfNoToDosInAll() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList(Arrays.asList("Math", "Math2", "Math3"));
        mainPage.archiveToDoList(Arrays.asList("Math", "Math2", "Math3"));
        $(byText("Completed")).click();
        mainPage.archiveToDoList(Arrays.asList("Math", "Math2", "Math3"));
        mainPage.getAllInsertedToDos().shouldHaveSize(0);
    }

    @Test
    public void clearCompletedMissingIfNoToDosInActive() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList(Arrays.asList("Math", "Math2", "Math3"));
        $(byText("Active"));
        mainPage.archiveToDoList(Arrays.asList("Math", "Math2", "Math3"));
        $(byText("Completed")).click();
        mainPage.archiveToDoList(Arrays.asList("Math", "Math2", "Math3"));
        mainPage.getAllInsertedToDos().shouldHaveSize(0);
    }

    @Test
    public void validateDeArchivedInActiveAfterClearedInActive() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList(Arrays.asList("Math", "Math2", "Math3"));
        $(byText("Active"));
        mainPage.archiveToDoList(Arrays.asList("Math", "Math2", "Math3"));
        $(byText("Completed")).click();
        mainPage.archiveToDoList(Arrays.asList("Math", "Math2", "Math3"));
        $(byText("Active")).click();
        mainPage.getAllInsertedToDos().shouldHaveSize(3);
    }

    @Test
    public void makeActiveAndDelete() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList(Arrays.asList("Math", "Math2"));
        mainPage.archiveToDoList(Arrays.asList("Math", "Math2"));
        $(byText("Completed")).click();
        mainPage.archiveToDoList(Arrays.asList("Math", "Math2"));
        $(byText("Active")).click();
        mainPage.deleteTodoList(Arrays.asList("Math", "Math2"));
        mainPage.getAllInsertedToDos().shouldHaveSize(0);
    }

    @Test
    public void validateArchivedToDosTextColor() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList(Collections.singletonList("Math"));
        mainPage.archiveToDoList(Collections.singletonList("Math"));
        $(byText("Math")).shouldHave(Condition.cssValue("color", "rgba(217, 217, 217, 1)"));
    }

    @Test
    public void placeholderTextInAll() {
        $("[ng-model='newTodo']").shouldHave(Condition.attribute("placeholder", "What needs to be done?"));
    }

    @Test
    public void placeholderTextInActive() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList(Collections.singletonList("Math"));
        $(byText("Active")).click();
        $("[ng-model='newTodo']").shouldHave(Condition.attribute("placeholder", "What needs to be done?"));
    }

    @Test
    public void placeholderTextInCompleted() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList(Collections.singletonList("Math"));
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
        mainPage.validateSelectedModuleAfterAddingToDoAndNavigatingToAnotherSection(Collections.singletonList("Math"), "Active", "Active");
    }

    @Test
    public void validateCompletedModuleIsSelectedAfterMovingThere() {
        MainPage mainPage = new MainPage();
        mainPage.validateSelectedModuleAfterAddingToDoAndNavigatingToAnotherSection(Collections.singletonList("Math"), "Completed", "Completed");
    }

    @Test
    public void abilityToArchiveAllAtOnceInAll() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList(Arrays.asList("Math", "Math2", "Math3"));
        mainPage.toggleAll();
        mainPage.getAllInsertedToDos().shouldHaveSize(3);
    }

    @Test
    public void abilityToArchiveAllAtOnceInActive() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList(Arrays.asList("Math", "Math2", "Math3"));
        $(byText("Active")).click();
        mainPage.toggleAll();
        mainPage.getAllInsertedToDos().shouldHaveSize(0);
    }

    @Test
    public void abilityToDeArchiveAllAtOnceInCompleted() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList(Arrays.asList("Math", "Math2", "Math3"));
        mainPage.toggleAll();
        $(byText("Completed")).click();
        mainPage.toggleAll();
        mainPage.getAllInsertedToDos().shouldHaveSize(0);
    }

    @Test
    public void toggledAllAtOnceShouldAppearInCompleted() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList(Arrays.asList("Math", "Math2", "Math3"));
        mainPage.toggleAll();
        $(byText("Completed")).click();
        mainPage.getAllInsertedToDos().shouldHave(texts("Math", "Math2", "Math3"));
    }

    @Test
    public void toggledAllAtOnceInActiveShouldAppearInCompleted() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList(Arrays.asList("Math", "Math2", "Math3"));
        $(byText("Active")).click();
        mainPage.toggleAll();
        $(byText("Completed")).click();
        mainPage.getAllInsertedToDos().shouldHave(texts("Math", "Math2", "Math3"));
    }

    @Test
    public void toggledAllAtOnceInCompletedShouldAppearInAll() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList(Arrays.asList("Math", "Math2", "Math3"));
        $(byText("Completed")).click();
        mainPage.toggleAll();
        $(byText("All")).click();
        mainPage.getAllInsertedToDos().shouldHave(texts("Math", "Math2", "Math3"));
    }

    @Test
    public void toggledAllAtOnceInCompletedShouldAppearInActive() {
        MainPage mainPage = new MainPage();
        mainPage.typeIntoTodoList(Arrays.asList("Math", "Math2", "Math3"));
        mainPage.toggleAll();
        $(byText("Completed")).click();
        mainPage.toggleAll();
        $(byText("Active")).click();
        mainPage.getAllInsertedToDos().shouldHave(texts("Math", "Math2", "Math3"));
    }
}
