package com.swordartist.dynamicevenspreadingview;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link ItemBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemBoardFragment extends Fragment implements ItemBoardContract.View {

    ItemBoardContract.Action presenter;
    LinearLayout rootView;

    final int MAX_COLUMN_IN_ROW_FOR_PORTRAIT = 4;
    final int MAX_COLUMN_IN_ROW_FOR_LANDSCAPE = 6;

    public ItemBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ItemBoardFragment.
     */
    public static ItemBoardFragment newInstance(String param1, String param2) {
        ItemBoardFragment fragment = new ItemBoardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create repository and presenter
        ItemBoardRepository repository = new ItemBoardRepository(getContext());
        presenter = ItemBoardPresenter.newInstance(this, repository);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (LinearLayout) inflater.inflate(R.layout.fragment_item_board, container, false);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        presenter.onViewConfigurationChanged(newConfig);

    }

    @Override
    public void buildRootViewContent(ArrayList<String> boardItemList, Configuration configuration) {
        int maxColumnInRow = MAX_COLUMN_IN_ROW_FOR_PORTRAIT; // default(portrait) max column in a row. 
        int maxColumnInRowForLandscape = MAX_COLUMN_IN_ROW_FOR_LANDSCAPE;
        if (null == configuration) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                maxColumnInRow = maxColumnInRowForLandscape;
            }
        } else {
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                maxColumnInRow = maxColumnInRowForLandscape;
            }
        }

        // before re-create view remove all current views
        rootView.removeAllViews();

        int itemNumberInLastRow = 0;
        float weightOfEmptySpaceForLastRow = 0.0f;
        int itemNumber = boardItemList.size();
        int rowNumber = 0;

        // If there is remainder, that mean that we need to one add additional row
        if ((itemNumber % maxColumnInRow) > 0) {
            rowNumber = (itemNumber / maxColumnInRow) + 1;
            // The number of item in the last raw is the remainder from (itemNumber % maxColumnInRow)
            itemNumberInLastRow = (itemNumber % maxColumnInRow);
            weightOfEmptySpaceForLastRow = ((float) maxColumnInRow - (float) itemNumberInLastRow) / 2.0f;
        } else {
            rowNumber = (itemNumber / maxColumnInRow);
            itemNumberInLastRow = maxColumnInRow;
        }

        int addingItemIndex = 0;

        for (int i = 0; i < rowNumber; i++) {
            // Add one LinearLayout for each row
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams LayoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
            LayoutParams.weight = 1;
            linearLayout.setLayoutParams(LayoutParams);

            int colMax = 0;
            // if it's the last row use itemNumberInLastRow for the loop count
            if ((i + 1) == rowNumber) {
                colMax = itemNumberInLastRow;
                // if empty spaces are required, add 2 more for the leftmost LinearLayout and rightmost LinearLayout
                if (weightOfEmptySpaceForLastRow > 0.0) {
                    colMax = colMax + 2;
                }
            } else {
                colMax = maxColumnInRow;
            }

            for (int j = 0; j < colMax; j++) {
                FrameLayout frameLayout = new FrameLayout(getContext());
                LinearLayout.LayoutParams layoutParamsF = new LinearLayout.LayoutParams(0, MATCH_PARENT);

                // Add empty space if it is the last row of the leftmost and rightmost, if it is required.
                if ((weightOfEmptySpaceForLastRow > 0.0) && ((i + 1) == rowNumber) && ((j == 0) || ((j + 1) == colMax))) {
                    layoutParamsF.weight = weightOfEmptySpaceForLastRow;
                    frameLayout.setLayoutParams(layoutParamsF);
                } else {
                    // set actual content from list
                    layoutParamsF.weight = 1;
                    frameLayout.setLayoutParams(layoutParamsF);
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    ConstraintLayout supportItemLayout = (ConstraintLayout) inflater.inflate(R.layout.item, frameLayout, false);
                    TextView textView = (TextView) supportItemLayout.findViewById(R.id.textView);
                    textView.setText(boardItemList.get(addingItemIndex));
                    addingItemIndex++;
                    frameLayout.addView(supportItemLayout);
                }

                linearLayout.addView(frameLayout);
            }
            rootView.addView(linearLayout);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
