import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class Client implements Comparable<Client> {
    private Integer id;
    private final Socket socket;
    private boolean active = true;

    public Client() throws IOException {
        socket = new Socket("127.0.0.1", 9999);
        registerToServer();
    }

    public int getId() {
        return id;
    }

    public void registerToServer() {
        if (this.id != null) return;
        //register to server
    }

    public void requestResource() {
        //Requisita o recurso
    }

    @Override
    public int compareTo(Client o) {
        return this.getId() - o.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (id == null || o == null || getClass() != o.getClass()) return false;
        return id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                '}';
    }

}
