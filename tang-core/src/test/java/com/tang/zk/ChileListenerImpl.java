package com.tang.zk;

import org.I0Itec.zkclient.IZkChildListener;

import java.util.List;

/**
 * Created by yuma on 2020/2/6.
 */
public class ChileListenerImpl implements IZkChildListener {
    /**
     * Called when the children of the given path changed.
     *
     * @param parentPath    The parent path
     * @param currentChilds The children or null if the root node (parent path) was deleted.
     * @throws Exception
     */
    @Override
    public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
        System.out.println("parentPath: " + parentPath);
        System.out.println("currentChilds: " + currentChilds);
    }
}
