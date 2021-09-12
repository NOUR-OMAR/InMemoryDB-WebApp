package InMemoryDB.database.record;

import InMemoryDB.model.User;

import static InMemoryDB.utils.Display.display;


public class UsersRecord implements RecordHandler {

    private static void tryParsingRecord(String record, User user) {
        try {
            setUserInfo(record, user);
        } catch (IllegalArgumentException illegalArgumentException) {
          //  display(illegalArgumentException.getMessage() + "\n Row \"" + record + "\" was not parsed.");
            illegalArgumentException.printStackTrace();
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
          //  display("\n Row \"" + record + "\" has wrong structure and was not parsed.");
            indexOutOfBoundsException.printStackTrace();
        }
    }

    private static void setUserInfo(String record, User user) {
        String[] userRecord = record.split(";");//
        display("length"+userRecord.length);
        if (userRecord.length > RecordLength.USERS_RECORD_LENGTH.getRecordLength()) throw new IndexOutOfBoundsException();
        user.setId(Integer.parseInt(userRecord[0]));
        user.setUsername(userRecord[1]);
        user.setPassword(userRecord[2]);
        user.setRole(userRecord[3]);

    }

    @Override
    public User parseRecord(String record) throws IllegalArgumentException {
        User user = new User();
        tryParsingRecord(record, user);
        return user;
    }

}
