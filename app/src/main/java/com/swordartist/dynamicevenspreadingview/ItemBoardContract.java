package com.swordartist.dynamicevenspreadingview;

import android.content.res.Configuration;

import java.util.ArrayList;

/**
 * Created by SwordArtist on 2017-05-15.
 */

public interface ItemBoardContract {
    
    interface View {
        void buildRootViewContent(ArrayList<String> supportItemModelArrayList, Configuration configuration);
    }
    
    interface Action {

        void start();

        void onViewConfigurationChanged(Configuration newConfig);
    }
}
