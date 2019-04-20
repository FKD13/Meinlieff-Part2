package Meinlieff;

import Meinlieff.ServerClient.ServerTasks.AwaitResponseTaskListener;

public interface ResponseWaiter {

    void onResponse(AwaitResponseTaskListener awaitResponseTaskListener);
}
