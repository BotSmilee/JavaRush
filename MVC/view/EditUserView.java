package MVC.view;

import com.javarush.task.task36.task3608.controller.Controller;
import com.javarush.task.task36.task3608.model.ModelData;

public class EditUserView implements View {
    private Controller controller;

    public void fireEventShowAllUsers() {
        controller.onShowAllUsers();
    }
    public void fireEventUserDeleted(long id){
        controller.onUserDelete(id);
    }
    public void fireEventShowDeletedUsers() {
        controller.onShowAllDeletedUsers();
    }
    @Override
    public void refresh(ModelData modelData) {
        System.out.println("User to be edited:\n\t"+ modelData.getActiveUser().toString()
                +"\n===================================================");
    }


    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void fireEventUserChanged(String name, long id, int level) {
        controller.onUserChange(name, id, level);
    }
}
