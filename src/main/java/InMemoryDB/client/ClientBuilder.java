package InMemoryDB.client;

import java.io.IOException;

public class ClientBuilder {

    private String username;


    public ClientBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public Client createClient() throws IOException {
        return new Client(username);
    }
}