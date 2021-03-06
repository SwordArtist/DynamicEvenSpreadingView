package com.swordartist.dynamicevenspreadingview;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Yusuke Yanai on 2017-05-15.
 */

public class ItemBoardRepository {
    private Context context;
    private ArrayList<String> mBoardItemList = new ArrayList<>();

    public ItemBoardRepository(Context context) {
        this.context = context;
    }

    public void populateBoardItemList() {
        mBoardItemList.add("Item1");
        mBoardItemList.add("Item2");
        mBoardItemList.add("Item3");
        mBoardItemList.add("Item4");
        mBoardItemList.add("Item5");
        mBoardItemList.add("Item6");
        mBoardItemList.add("Item7");
        mBoardItemList.add("Item8");
        mBoardItemList.add("Item9");
        mBoardItemList.add("Item10");
        mBoardItemList.add("Item11");
        mBoardItemList.add("Item12");
        mBoardItemList.add("Item13");
        mBoardItemList.add("Item14");
        mBoardItemList.add("Item15");
    }

    public ArrayList<String> getBoardItemList() {
        if (mBoardItemList.isEmpty()) {
            populateBoardItemList();
        }
        return mBoardItemList;
    }
}
