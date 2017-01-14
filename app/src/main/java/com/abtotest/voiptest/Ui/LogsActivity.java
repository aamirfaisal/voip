package com.abtotest.voiptest.Ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.abtotest.voiptest.adapter.AdapterGridView;
import com.abtotest.voiptest.fragments.DialogLogs;
import com.abtotest.voiptest.R;

/**
 * Created by Car on 6/18/2016.
 */
public class LogsActivity extends Fragment {
    private GridView gridView;
public String a;
    private Button back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logs_layout,container, false);

        gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setAdapter(new
                AdapterGridView(getContext(), R.layout.activity_grid));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DialogLogs dfl = new DialogLogs();
                Bundle b = new Bundle();
                b.putInt("position",position);
                dfl.setArguments(b);
                dfl.show(getActivity().getSupportFragmentManager(), "dfl");
            }
        });
        return view;
    }
}





