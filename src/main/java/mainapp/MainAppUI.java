package mainapp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tableobjects.Project;
import tableobjects.Task;
import tableobjects.User;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import userinterface.SynkApp;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MainAppUI {
    @FXML private ListView<Project> listViewProjects;
    @FXML private ListView<Task> listViewTasks;
    @FXML private ListView<User> listViewUsers;

    //TODO move some functionality to a separate class, maybe a UI controller of some sorts
    //TODO add functionality to only display projects that the user is on, maybe through saved session user_id

    @FXML
    public void displayForm(ActionEvent event) {
        //Use elements embedded in the fxml file for the button in order to display the correct form.
        //also uses the name on the button in order to set the title of the form
        SynkApp.getInstance().showForm(((Button) event.getSource()).getText(),((Button) event.getSource()).getId());
    }
    public void initialize(){
        listViewProjects.setItems(AppData.getInstance().getProjItems());
        listViewProjects.setCellFactory(lv -> Project.getCell());
        listViewTasks.setCellFactory(lv -> Task.getCell());
    }
    @FXML
    public void updateProjectName(Event event){
        int projId = listViewProjects.getItems().get(listViewProjects.getEditingIndex()).getProjId();
        String projName = listViewProjects.getItems().get(listViewProjects.getEditingIndex()).getProjName();
        updateDatabase("projects",projId,projName);
    }
    @FXML
    public void updateTaskName(Event event){
        int taskId = listViewTasks.getItems().get(listViewTasks.getEditingIndex()).getTaskID();
        String taskName = listViewTasks.getItems().get(listViewTasks.getEditingIndex()).getTaskName();
        updateDatabase("tasks",taskId,taskName);
    }
    public void updateDatabase(String type, int id, String newValue){
        AppData.getInstance().updateDatabase(type,id,newValue);
    }
    @FXML
    public void narrowUsers(){
        if(listViewTasks.getSelectionModel().getSelectedIndex() ==-1){
            return;
        }
        int taskID = listViewTasks.getItems().get(listViewTasks.getSelectionModel().getSelectedIndex()).getTaskID();
        listViewUsers.setItems(MainAppUIController.getFilteredUsersToDisplay(taskID));

    }
    @FXML
    public void showProjectTasksAndUsers(){
        if(listViewProjects.getSelectionModel().getSelectedIndex() == -1){
            return;
        }
        int projId = listViewProjects.getItems().get(listViewProjects.getSelectionModel().getSelectedIndex()).getProjId();
        listViewTasks.setItems(new FilteredList<>(AppData.getInstance().getTaskItems()).filtered(s->s.getProjID() == projId));
        listViewUsers.setItems(MainAppUIController.getUsersToDisplay(projId));


    }

}
