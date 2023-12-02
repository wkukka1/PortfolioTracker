package use_case.show;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ShowInputBoundary {
    void execute(ShowInputData showInputData) throws JsonProcessingException;

}
