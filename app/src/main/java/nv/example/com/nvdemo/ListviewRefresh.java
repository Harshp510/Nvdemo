package nv.example.com.nvdemo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ListviewRefresh extends Fragment {


    ListView listView;
    String[] arrayS = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p"};
    String[] arrayS2 = new String[]{"a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1", "i1", "j1", "k1", "l1", "m1", "n1", "o1", "p1"};
    String[] arrayS3 = new String[]{"a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1", "i1", "j1", "k1", "l1", "m1", "n1", "o1", "p1"};
    int pageCount = 1;
    View footer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.content_listview_refresh,container,false);

        listView = (ListView)view.findViewById(R.id.load);

        final ArrayList<String> list = new ArrayList<String>();


        for (int i = 0; i < arrayS.length; i++) {
            list.add(arrayS[i]);
        }

        // Add footer view
        footer = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.simple_list_item_, null, false);
        listView.addFooterView(footer);

        final ArrayAdapter<String> ad = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(ad);


        // Implementing scroll refresh
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItemCount, final int totalItems) {
                Log.e("Get position", "--firstItem:" + firstItem + "  visibleItemCount:" + visibleItemCount + "  totalItems:" + totalItems + "  pageCount:" + pageCount);
                int total = firstItem + visibleItemCount;


                // Total array list i have so it
                if (pageCount < 2) {

                    if (total == totalItems) {

                        // Execute some code after 15 seconds have passed
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                for (int i = 0; i < arrayS2.length; i++) {
                                    list.add(arrayS2[i]);
                                }
                                ad.notifyDataSetChanged();
                                listView.setAdapter(ad);
                                listView.setSelection(totalItems);
                                pageCount++;
                            }
                        }, 15000);
                    }
                } else {
                    Log.e("hide footer", "footer hide");
                    listView.removeFooterView(footer);
                }


            }
        });
        return view;

    }


}