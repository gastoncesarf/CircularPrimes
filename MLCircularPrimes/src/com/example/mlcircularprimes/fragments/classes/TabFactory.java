package com.example.mlcircularprimes.fragments.classes;

/* 
 * 2015-02-05 
 * FLORES GASTON - PRIMOS CIRCULARES
 * 
 * TabFactory.java
 *  
 */

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

public class TabFactory implements TabContentFactory {

    private final Context mContext;

    public TabFactory(Context context) {
        mContext = context;
    }

    public View createTabContent(String tag) {
        View v = new View(mContext);
        v.setMinimumWidth(0);
        v.setMinimumHeight(0);
        return v;
    }
}
