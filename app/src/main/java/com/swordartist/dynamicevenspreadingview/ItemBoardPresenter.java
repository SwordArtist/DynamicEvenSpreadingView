package com.swordartist.dynamicevenspreadingview;

import android.content.res.Configuration;

/**
 * Created by SwordArtist on 2017-05-15.
 */

public class ItemBoardPresenter implements ItemBoardContract.Action {

    private ItemBoardRepository mRepository;
    private ItemBoardContract.View mView;

    static public ItemBoardPresenter newInstance(ItemBoardContract.View view, ItemBoardRepository repository) {
        ItemBoardPresenter itemBoardPresenter = new ItemBoardPresenter();
        itemBoardPresenter.setRepository(repository);
        itemBoardPresenter.setView(view);
        return itemBoardPresenter;
    }

    public void setRepository(ItemBoardRepository itemBoardRepository) {
        mRepository = itemBoardRepository;
    }

    public void setView(ItemBoardContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.buildRootViewContent(mRepository.getBoardItemList(), null);
    }

    @Override
    public void onViewConfigurationChanged(Configuration newConfig) {
        mView.buildRootViewContent(mRepository.getBoardItemList(), newConfig);
    }
}
