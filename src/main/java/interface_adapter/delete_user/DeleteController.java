package interface_adapter.delete_user;
import use_case.delete_user.DeleteInputBoundary;

public class DeleteController {
    final DeleteInputBoundary deleteUseCaseInteractor;

    public DeleteController(DeleteInputBoundary deleteInteractor){
        this.deleteUseCaseInteractor = deleteInteractor;
    }

    public void execute(String id){
        deleteUseCaseInteractor.execute(id);
    }
}
