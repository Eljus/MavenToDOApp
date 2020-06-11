import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

class MainPage {
    private final static Logger log = Logger.getLogger(MainPage.class);


    void typeIntoTodoList(List<String> listOfAddableToDos) {
        for(String toDoToAdd:listOfAddableToDos){
            $("[ng-model='newTodo']").val(toDoToAdd).pressEnter();
        }
    }

    ElementsCollection getAllInsertedToDos() {
        return $$(".view");
    }

    void compareVisibleToDoNumbers(String visibleToDos) {
        $(".todo-count > .ng-binding").shouldHave(Condition.text(visibleToDos));
    }

    void archiveToDoList(List<String> listOfArchievableToDos) {
        for(String toDoToArchive:listOfArchievableToDos){
            $(byText(toDoToArchive)).parent().find(By.cssSelector("input")).click();
        }
    }

    void deleteTodoList(List<String> listOfDeletionNeededToDos) {
        for(String toDoToDelete:listOfDeletionNeededToDos){
            $(byText(toDoToDelete)).hover();
            $(byText(toDoToDelete)).parent().find(By.cssSelector("[class='destroy']")).hover().click();
        }
    }

    void toggleAll() {
        $("[for='toggle-all']").click();
    }

    void validateInsertedNumberWithOneToDo(List<String> toDosToAdd, Integer insertedToDosSize){
        typeIntoTodoList(toDosToAdd);
        getAllInsertedToDos().shouldHave(CollectionCondition.size(insertedToDosSize));
    }

    void validateLeftItemsWithOneToDo(List<String> toDosToAdd, String visibleNumberText){
        typeIntoTodoList(toDosToAdd);
        compareVisibleToDoNumbers(visibleNumberText);
    }

    void typeTodoAndSelectSection(List<String> toDosToAdd, String sectionToSelect){
        typeIntoTodoList(toDosToAdd);
        $(byText(sectionToSelect)).click();
    }

    void typeTodoAndSelectSectionAndValidateToDosCount(List<String> toDosToAdd, String sectionToSelect, Integer insertedTodosSize){
        typeTodoAndSelectSection(toDosToAdd, sectionToSelect);
        getAllInsertedToDos().shouldHaveSize(insertedTodosSize);
    }

    void typeTodoAndSelectSectionAndValidateToDosCountAndVisibleNumbeText(List<String> toDosToAdd, String sectionToSelect, Integer insertedTodosSize, String visibleToDosString){
        typeTodoAndSelectSectionAndValidateToDosCount(toDosToAdd, sectionToSelect, insertedTodosSize);
        compareVisibleToDoNumbers(visibleToDosString);
    }

    void typeToDoSelectSectionAndDelete(List<String> toDosToAdd, String sectionToSelect, List<String> toDoToDelete){
        typeTodoAndSelectSection(toDosToAdd, sectionToSelect);
        deleteTodoList(toDoToDelete);
    }

    void typeToDoArchiveSelectSection(List<String> toDosToAdd, List<String> toDoToArchive, String sectionToSelect){
        typeIntoTodoList(toDosToAdd);
        archiveToDoList(toDoToArchive);
        $(byText(sectionToSelect)).click();
    }

    void typeToDoArchiveSelectSectionAndDelete(List<String> toDosToAdd, List<String> toDoToArchive, String sectionToSelect, List<String> toDoToDelete){
        typeToDoArchiveSelectSection(toDosToAdd, toDoToArchive, sectionToSelect);
        deleteTodoList(toDoToDelete);
    }

    void typeTodoAndDelete(List<String> toDosToAdd, List<String> toDoToDelete){
        typeIntoTodoList(toDosToAdd);
        deleteTodoList(toDoToDelete);
    }

    void typeTodoDeleteAndValidateNumber(List<String> toDosToAdd, List<String> toDoToDelete, Integer sizeToValidate){
        typeTodoAndDelete(toDosToAdd, toDoToDelete);
        getAllInsertedToDos().shouldHaveSize(sizeToValidate);
    }

    void addTodoAndArchive(List<String> toDosToAdd, List<String> toDoToArchive){
        typeIntoTodoList(toDosToAdd);
        archiveToDoList(toDoToArchive);
    }

    void addTodoArchiveAndValidateStringItemsLeft(List<String> toDosToAdd, List<String> toDoToArchive, String itemsLeftString){
        addTodoAndArchive(toDosToAdd, toDoToArchive);
        compareVisibleToDoNumbers(itemsLeftString);
    }

    void validateSelectedModuleAfterAddingToDoAndNavigatingToAnotherSection(List<String> toDosToAdd, String sectionToClick, String sectionToValidateAgainst){
        typeIntoTodoList(toDosToAdd);
        $(byText(sectionToClick)).click();
        $(byText(sectionToValidateAgainst)).shouldHave(Condition.attribute("class", "selected"));
    }
}
