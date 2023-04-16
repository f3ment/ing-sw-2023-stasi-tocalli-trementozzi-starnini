import distributed.Server;
import distributed.local.ClientImpl;
import distributed.local.ServerImpl;
import view.TextualUI;
import utils.*;
import model.*;
import controller.*;

import java.io.IOException;
import java.util.*;

import java.util.ArrayList;


public class App {
    public static void main( String[] args ) {
        Server server = new ServerImpl();
        ClientImpl client = new ClientImpl(server);
        client.run();
    }
}
