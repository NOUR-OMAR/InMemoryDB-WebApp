package InMemoryDB.common.operation;

import InMemoryDB.common.JSON;
import InMemoryDB.common.JSONBuilder;
import InMemoryDB.common.operation.crud.CreateOperation;
import InMemoryDB.common.operation.crud.DeleteOperation;
import InMemoryDB.common.operation.crud.ReadOperation;
import InMemoryDB.common.operation.crud.UpdateOperation;
import org.jetbrains.annotations.NotNull;

import javax.json.JsonObject;
import java.io.IOException;

import static InMemoryDB.utils.Constant.OperationCommand.*;

public class OperationFactory {

    private final JSONBuilder json = new JSON();

    //TODO try to remove the jsonObject from here
    public AbstractOperation getOperation(String command) throws IOException {

        JsonObject jsonObject = getJson().getJsonObject(command);

        switch (request(jsonObject)) {
            case CREATE -> {

                return CreateOperation.createCreateOperation(getJson().buildEmployee(jsonObject));
            }
            case READ -> {

                return ReadOperation.createReadOperation(getJson().buildID(jsonObject));
            }
            case UPDATE -> {

                return UpdateOperation.createUpdateOperation(getJson().buildEmployee(jsonObject));
            }
            case DELETE -> {

                return DeleteOperation.createDeleteOperation(getJson().buildID(jsonObject));
            }

            case FILTER_NAME -> {


                return NameFilterOperation.createNameFilterOperation(getJson().buildName(jsonObject));
            }
            case FILTER_SALARY -> {

                return SalaryFilterOperation.createSalaryFilterOperation(getJson().buildSalary(jsonObject));
            }
            case CLOSE -> {

                return CloseOperation.createCloseOperation(new String[]{"Close"});
            }
            case SELECT_ALL -> {

                return SelectAllOperation.createSelectAllOperation();
            }
            case HELP -> {

                return HelpOperation.createHelpOperation(new String[]{"Help"});
            }
            default -> {
                return UnrecognizedOperation.createUnrecognizedOperation();
            }
        }
    }

    //TODO
    @NotNull
    private String request(JsonObject jsonObject) {
        return jsonObject.getJsonString("OperationTypes").getString().trim();
    }

    public JSONBuilder getJson() {
        return json;
    }

}
