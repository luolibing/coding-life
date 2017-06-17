package cn.tim.spring4.ioc722;

/**
 * Created by LuoLiBing on 16/5/25.
 */
public class ClientServiceLocator {
    private static ClientService clientService = new ClientService();
    private ClientServiceLocator() {}
    public ClientService createInstance() {
        return clientService;
    }

    public ClientService createAccountInstance() {
        return clientService;
    }
}
