package InMemoryDB.server.database.record;

import InMemoryDB.client.model.User;

import static InMemoryDB.utils.Constant.Display.display;
import static InMemoryDB.utils.Constant.ROW_LENGTH;


public class UsersRecord implements RecordHandler {

    private static void tryParsingRecord(String record, User user) {
        try {
            setUserInfo(record, user);
        } catch (IllegalArgumentException illegalArgumentException) {
            display(illegalArgumentException.getMessage() + "\n Row \"" + record + "\" was not parsed.");
            illegalArgumentException.printStackTrace();
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            display("\n Row \"" + record + "\" has wrong structure and was not parsed.");
            indexOutOfBoundsException.printStackTrace();
        }
    }

    private static void setUserInfo(String record, User user) {
        String[] userRecord = record.split(";");//
        if (userRecord.length > ROW_LENGTH) throw new IndexOutOfBoundsException();
        user.setUsername(userRecord[0].strip());
        user.setPassword(userRecord[1]);
    }

    @Override
    public User parseRecord(String record) throws IllegalArgumentException {
        User user = new User();
        tryParsingRecord(record, user);
        return user;
    }

}
